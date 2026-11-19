# InnoDB 快照读与 `SELECT ... FOR UPDATE`

## 一句话区分

- **普通 `SELECT`** 通常是快照读：用 MVCC 判断哪个行版本对当前事务可见，不为读到的行加行锁。适合展示和一般查询。
- **`SELECT ... FOR UPDATE`** 是锁定读（当前读）：读取当前可用的记录，并给扫描到的索引记录加排他锁；必要时还会锁索引间隙。适合在同一事务中先检查、再依据结果修改。

这里讨论 MySQL InnoDB。`FOR UPDATE` 不会把数据复制一份或永久锁住；锁通常在事务 `COMMIT` 或 `ROLLBACK` 后释放。

## 用两个事务看区别

假设 `product` 表中 `id = 1` 的库存起初是 `1`。

### 普通 `SELECT`

```sql
-- 事务 A
START TRANSACTION;
SELECT stock FROM product WHERE id = 1;  -- 读到 1

-- 事务 B 此时可以修改该行并提交
UPDATE product SET stock = 0 WHERE id = 1;

-- 事务 A 不能只凭刚才读到的 1，断定现在仍有库存
```

普通查询没有锁住该商品。事务 A 再次普通查询会看到什么，取决于隔离级别和读视图：在 `READ COMMITTED` 下通常能看到 B 已提交的新值；在 `REPEATABLE READ` 下，若沿用之前的读视图，通常仍看到旧值 `1`。**看到旧值不代表可以安全地按旧值扣库存。**

### `FOR UPDATE`

```sql
-- 事务 A
START TRANSACTION;
SELECT stock FROM product WHERE id = 1 FOR UPDATE;  -- 读到 1，锁住该行
-- 根据业务条件继续修改
UPDATE product SET stock = stock - 1 WHERE id = 1;
COMMIT;  -- 释放锁
```

在 A 提交前，事务 B 若要更新同一行，通常必须等待；B 的普通 `SELECT` 通常仍可进行快照读。这样 A 在“读库存、判断、修改”的事务内，不会被 B 对该行的冲突修改插入。

如果 B 已经先锁住该行，A 的 `FOR UPDATE` 通常会等待 B 完成，然后读取可用的最新版本；它不是沿用 A 之前普通 `SELECT` 的旧快照。

## 什么时候选哪个

| 需求 | 常用写法 | 原因 |
|---|---|---|
| 展示商品、查询订单、一般报表 | 普通 `SELECT` | 不需要阻止别人修改，减少锁竞争 |
| 先读多项数据、做业务判断，再在同一事务中修改 | `START TRANSACTION` + `SELECT ... FOR UPDATE` | 在判断到修改期间保护相关记录 |
| 只需按一个条件原子修改一行 | 单条条件 `UPDATE` | 数据库可以在更新时同时判断条件，通常更简单 |

库存只扣 1 的例子通常直接写：

```sql
UPDATE product
SET stock = stock - 1
WHERE id = 1 AND stock > 0;
```

检查受影响行数：`1` 表示扣减成功，`0` 表示没有满足条件的行。这条 `UPDATE` 本身会按需加锁并在更新时检查条件，不需要额外先查一次 `FOR UPDATE`。

## 使用 `FOR UPDATE` 的注意点

1. **要有事务边界。** 在默认自动提交模式下，单独执行 `SELECT ... FOR UPDATE` 后语句就结束，锁无法保护随后另一条语句。把读取、判断和修改放进同一个事务。
2. **事务尽量短。** 持锁期间不要等待用户输入、长时间调用外部接口；其他事务可能因此排队。
3. **索引影响锁范围。** 按唯一索引精确命中一行通常只锁该记录；范围扫描或非唯一索引在 `REPEATABLE READ` 下可能涉及 next-key lock / 间隙锁。没有合适索引可能扫描并锁住更多记录。实际范围以执行计划和隔离级别为准。
4. **普通 `SELECT` 与锁定读可能看到不同版本。** `REPEATABLE READ` 下普通 `SELECT` 使用读视图；`FOR UPDATE` 要处理当前可用记录。因此不要把旧快照里的值当作当前状态。

记忆：**只是看，用普通 `SELECT`；读完要依据结果做多步修改，在事务中考虑 `FOR UPDATE`；能用一条条件 `UPDATE` 完成，就优先让数据库一次做完。**
