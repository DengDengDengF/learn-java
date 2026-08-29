# 懒连接和连接池真正结合的 Demo

## 一句话说明

真正结合后的流程应该是：

```text
DataSource.getConnection()
    -> 只创建代理，不取真实连接

第一次执行 SQL
    -> getRealConnection()
    -> target = supplier.get()
    -> 先从连接池取真实连接
    -> 池中没有才调用 DriverManager.getConnection(...)

代理 close()
    -> 不关闭真实连接
    -> 把 target 归还连接池
```

因此，这一版同时实现了：

- **懒连接**：第一次执行 SQL 时才取得真实连接。
- **连接池**：用完以后不真正关闭，归还队列供下次复用。

---

## 一、公共代理父类

`Connection` 接口的方法很多。代理父类负责把这些方法转交给真实连接：

```java
public abstract class AbstractConnectionProxy implements Connection {

    protected abstract Connection getRealConnection()
            throws SQLException;

    @Override
    public Statement createStatement() throws SQLException {
        return getRealConnection().createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql)
            throws SQLException {
        return getRealConnection().prepareStatement(sql);
    }

    @Override
    public void commit() throws SQLException {
        getRealConnection().commit();
    }

    @Override
    public void rollback() throws SQLException {
        getRealConnection().rollback();
    }

    @Override
    public void close() throws SQLException {
        getRealConnection().close();
    }

    // Connection 的其他方法采用相同方式转发，此处省略。
}
```

例如调用：

```java
connection.prepareStatement(sql);
```

实际进入：

```java
getRealConnection().prepareStatement(sql);
```

所以真正执行 SQL 的是 `getRealConnection()` 返回的对象。

---

## 二、定义取得真实连接的回调

创建或借出连接可能抛出 `SQLException`，所以定义一个简单接口：

```java
@FunctionalInterface
public interface ConnectionSupplier {
    Connection get() throws SQLException;
}
```

它表示：

> 当代理真正需要连接时，应该去哪里取得连接。

注意，它不一定每次都创建新连接，也可能从连接池中取出旧连接。

---

## 三、懒加载的池化代理

```java
public class LazyPooledConnectionProxy
        extends AbstractConnectionProxy {

    // 知道如何从连接池取得真实连接：
    private final ConnectionSupplier supplier;

    // 连接使用完后要归还到这里：
    private final Queue<Connection> idleQueue;

    // 当前代理借到的真实连接，开始时是 null：
    private Connection target;

    public LazyPooledConnectionProxy(
            ConnectionSupplier supplier,
            Queue<Connection> idleQueue) {
        this.supplier = supplier;
        this.idleQueue = idleQueue;
    }

    @Override
    protected Connection getRealConnection() throws SQLException {
        if (target == null) {
            // 第一次执行 SQL 时才执行这个回调：
            target = supplier.get();
        }
        return target;
    }

    @Override
    public void close() throws SQLException {
        if (target == null) {
            // 从未执行 SQL，也就没有借真实连接。
            return;
        }

        System.out.println("Return connection to pool: " + target);

        // 假关闭：不调用 target.close()，只归还连接池。
        boolean returned = idleQueue.offer(target);

        // 队列满了，无法归还时才真正关闭。
        if (!returned) {
            target.close();
        }

        // 当前代理不再持有这个真实连接。
        target = null;
    }
}
```

最关键的代码仍然是：

```java
target = supplier.get();
```

但这里的 `supplier.get()` 不一定直接新建连接，而是：

```text
先从 idleQueue 取
    -> 取到了：复用旧连接
    -> 没取到：创建新连接
```

对象刚创建时：

```text
LazyPooledConnectionProxy
    |
    +-- supplier -> 保存着“怎样从池中取得连接”的回调
    +-- idleQueue -> 空闲真实连接队列
    +-- target   -> null
```

第一次执行 SQL 后：

```text
LazyPooledConnectionProxy
    |
    +-- target -> 从池中借到或新创建的真实 Connection
```

---

## 四、真正结合后的 PooledDataSource

```java
public class PooledDataSource implements DataSource {

    private final String url;
    private final String defaultUsername;
    private final String defaultPassword;

    // 队列保存空闲的真实连接：
    private final Queue<Connection> idleQueue =
            new ArrayBlockingQueue<>(100);

    public PooledDataSource(
            String url,
            String defaultUsername,
            String defaultPassword) {
        this.url = url;
        this.defaultUsername = defaultUsername;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(defaultUsername, defaultPassword);
    }

    @Override
    public Connection getConnection(
            String username,
            String password) throws SQLException {

        ConnectionSupplier supplier = () ->
                borrowConnection(username, password);

        // 注意：这里只创建代理，没有执行 supplier.get()。
        return new LazyPooledConnectionProxy(
                supplier,
                idleQueue
        );
    }

    private Connection borrowConnection(
            String username,
            String password) throws SQLException {

        // 第一次执行 SQL 时，才会进入这个方法。
        Connection connection = idleQueue.poll();

        if (connection != null) {
            System.out.println(
                    "Reuse connection from pool: " + connection
            );
            return connection;
        }

        // 池中没有空闲连接，才创建新的真实连接。
        connection = DriverManager.getConnection(
                url,
                username,
                password
        );

        System.out.println("Open new connection: " + connection);
        return connection;
    }

    // DataSource 的其他管理方法省略。
}
```

这里与之前单独的连接池 Demo 最大的区别是：

之前的写法在 `getConnection()` 中立即执行：

```java
Connection connection = idleQueue.poll();
```

结合后的写法在 `getConnection()` 中只保存回调：

```java
ConnectionSupplier supplier = () ->
        borrowConnection(username, password);

return new LazyPooledConnectionProxy(supplier, idleQueue);
```

此时 `borrowConnection()` 没有执行，所以既没有从池中借连接，也没有创建新连接。

---

## 五、完整使用 Demo

```java
public class Main {

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new PooledDataSource(
                "jdbc:mysql://localhost:3306/test",
                "root",
                "123456"
        );

        System.out.println("只获取代理，不执行 SQL");
        try (Connection connection = dataSource.getConnection()) {
            // 没有执行 SQL，不会取得真实连接。
        }

        System.out.println("第一次执行 SQL");
        try (Connection connection1 = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection1.prepareStatement(
                                 "SELECT * FROM students")) {
                statement.executeQuery();
            }
        }

        System.out.println("第二次执行 SQL");
        try (Connection connection2 = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection2.prepareStatement(
                                 "SELECT * FROM students")) {
                statement.executeQuery();
            }
        }
    }
}
```

---

## 六、只获取代理，不执行 SQL

调用：

```java
Connection connection = dataSource.getConnection();
```

执行过程：

```text
PooledDataSource.getConnection()
    -> 创建 supplier 回调
    -> new LazyPooledConnectionProxy(supplier, idleQueue)
    -> 返回代理
```

此时：

```text
target == null
```

没有执行：

```java
supplier.get();
```

也没有执行：

```java
idleQueue.poll();
DriverManager.getConnection(...);
```

随后调用 `close()`，因为：

```java
target == null
```

所以直接结束，什么也不用归还。

---

## 七、第一次执行 SQL

调用：

```java
connection1.prepareStatement(sql);
```

完整过程：

```text
AbstractConnectionProxy.prepareStatement(sql)
    -> getRealConnection()
    -> target == null
    -> target = supplier.get()
    -> borrowConnection(username, password)
    -> idleQueue.poll()
    -> 池中没有空闲连接
    -> DriverManager.getConnection(...)
    -> 创建真实 Connection #1
    -> 保存到 target
    -> target.prepareStatement(sql)
```

所以在第一次执行 SQL 时：

```java
target = supplier.get();
```

确实是取得真实连接的入口。

使用结束时：

```java
connection1.close();
```

执行：

```text
idleQueue.offer(target)
    -> Connection #1 被放回连接池
    -> target = null
```

注意：这里没有调用真实连接的 `close()`。

---

## 八、第二次执行 SQL

第二次调用：

```java
Connection connection2 = dataSource.getConnection();
```

仍然只创建一个新代理：

```text
connection2 -> 新的 LazyPooledConnectionProxy
               target == null
```

然后调用：

```java
connection2.prepareStatement(sql);
```

执行过程：

```text
getRealConnection()
    -> target == null
    -> target = supplier.get()
    -> borrowConnection(...)
    -> idleQueue.poll()
    -> 取出 Connection #1
    -> 不执行 DriverManager.getConnection(...)
    -> target = Connection #1
    -> target.prepareStatement(sql)
```

因此：

```text
代理 connection1 和 connection2 是两个不同对象
它们先后使用的 target 是同一个 Connection #1
```

这就同时实现了“懒取得”和“复用”。

---

## 九、`supplier.get()` 到底做了什么

现在不能简单理解为：

```text
supplier.get() = 一定创建新连接
```

正确理解是：

```text
supplier.get() = 向连接池申请一个真实连接
```

连接池内部再决定：

```text
idleQueue 有连接
    -> 返回旧连接

idleQueue 没有连接
    -> DriverManager.getConnection(...)
    -> 创建新连接
```

所以真正建立新的数据库物理连接，只发生在：

```java
DriverManager.getConnection(url, username, password);
```

而执行到它必须同时满足：

1. 第一次执行 SQL，触发 `target = supplier.get()`。
2. 空闲队列中没有可复用的真实连接。

---

## 十、完整调用链总结

```text
dataSource.getConnection()
    -> 返回懒代理
    -> target == null

第一次执行 SQL
    -> getRealConnection()
    -> supplier.get()
    -> 从 idleQueue 借连接
       ├── 有：复用旧连接
       └── 无：DriverManager.getConnection() 创建新连接
    -> 保存到 target
    -> 使用 target 执行 SQL

代理 close()
    -> idleQueue.offer(target)
    -> 归还真实连接
    -> target = null
```

一句话记住：

> `target = supplier.get()` 负责在第一次执行 SQL 时向连接池借连接；池中没有连接时，才真正新建数据库连接。

---

## 十一、这是简化 Demo，仍有瑕疵

为了突出核心流程，这一版没有实现生产连接池的全部功能，例如：

- 没有严格限制真实连接总数。
- 没有等待可用连接的超时机制。
- 没有借出前的连接有效性检查。
- 没有实现关闭整个连接池。
- 不同用户名创建的连接共用一个队列，可能发生账号混用。
- 没有重置事务状态、自动提交、隔离级别等连接属性。

生产项目应使用 HikariCP 等成熟连接池。这份 Demo 只用于理解懒代理和连接复用怎样组合。
