[TOC]

### 1.修饰符

```java
public:(最宽松)
类：可以修饰类。被 public 修饰的类可以在整个项目中被访问。
变量：可以修饰类成员变量。被 public 修饰的变量可以在任何地方访问。
方法：可以修饰类的方法。被 public 修饰的方法可以在任何地方调用。
构造函数：可以修饰构造函数。被 public 修饰的构造函数可以在任何地方调用来创建类的实例。
     
protected(很宽松，允许跨包子类访问):
类：不能修饰类。顶级类不能是 protected。
变量：可以修饰类成员变量。被 protected 修饰的变量可以在同一个包中的其他类中访问，也可以在不同包中的子类中访问。
方法：可以修饰类的方法。被 protected 修饰的方法可以在同一个包中的其他类中调用，也可以在不同包中的子类中调用。
构造函数：可以修饰构造函数。被 protected 修饰的构造函数可以在同一个包中，或在不同包中的子类中被调用。
protected作用于继承关系。定义为protected的字段和方法可以被子类访问，以及子类的子类。
    
package:默认（稍微宽松，限制在包内访问）
类：如果一个类没有明确指定为 public 或 private，那么它默认是包级别的（包私有的）。这样的类只能在同一个包中被访问，不能被其他包中的类访问。
变量：如果一个变量没有明确指定为 public、private 或 protected，那么它也是包级别的。这样的变量只能在同一个包中访问。
方法：如果一个方法没有明确指定为 public、private 或 protected，那么它也是包级别的。这样的方法只能在同一个包中调用。
构造函数：同样地，如果构造函数没有明确指定访问修饰符，它也是包级别的。这样的构造函数只能在同一个包中被调用。
    
private:(最严格，限制在类内部)
类：不能修饰类。顶级类不能是 private。
内部类：可以修饰内部类，表示该内部类只能在定义它的外部类中使用。
变量：可以修饰类成员变量。被 private 修饰的变量只能在本类中访问。
方法：可以修饰类的方法。被 private 修饰的方法只能在本类中调用。
构造函数：可以修饰构造函数。被 private 修饰的构造函数只能在本类中使用，通常用于实现单例模式。
```

### 2.非访问修饰符

```java
1.static：
方法：调用静态方法不需要实例，无法访问this，但可以访问静态字段和其他静态方法；静态方法常用于工具类和辅助方法。
变量：静态字段属于所有实例“共享”的字段，实际上是属于class的字段；
    
2.final：
类：该类不能被继承。
方法：该方法不能被覆写(Override)。
变量：该变量只能被赋值一次（常量）。
    
3*.abstract：
类：抽象类，不能实例化，必须被子类继承。
方法：抽象方法，没有方法体，必须在子类中实现。
抽象类本身被设计成"只能被继承"，因此抽象类可以"强迫子类"实现其定义的抽象方法，相当于定义了一个"规范"。
    
4.interface
类:Java的接口（interface）定义了纯抽象规范，一个类可以实现(implements)多个接口；
接口也是数据类型，适用于向上转型和向下转型；
接口的所有方法都是抽象方法(public abstract)，接口"不能定义实例字段";如果定义了字段，编译器会自动加上public static final 字段。
接口可以定义default方法（JDK>=1.8）。

5.default 关键字用于接口方法，允许提供方法的默认实现。

6.enum
Java使用enum定义枚举类型，它被编译器编译为final class Xxx extends Enum { … }；
通过name()获取常量定义的字符串，注意不要使用toString()；
通过ordinal()返回常量定义的顺序（无实质意义）；
可以为enum编写构造方法、字段和方法
enum的构造方法要声明为private，字段强烈建议声明为final；
enum适合用在switch语句中。

7.record
从Java 14开始，提供新的record关键字，可以非常方便地定义Data Class：
使用record定义的是不变类;("final class Point extends Record")
可以编写Compact Constructor对参数进行验证；
可以定义静态方法。(record提供的of方法返回Record类型)
我的理解，这就是为了简化逻辑，java官方给封装的class.也没发现多简化。

5.synchronized：
方法：同步方法，确保同一时间只有一个线程可以执行该方法，通常用于线程安全。
代码块：同步代码块，同步一部分代码，而不是整个方法。
    可以锁住方法
    public class Example {
             public synchronized void synchronizedMethod() {
                 // 一个线程可以同时执行该实例的所有 synchronized 实例方法。
             }
     }

    可以锁住类
     public class Example {
          public static synchronized void synchronizedStaticMethod() {
                 // 在整个类的所有实例之间共享一个锁。
          }
     }
    可以锁住变量
      public class Example {
            private final Object lock = new Object();
            public void method() {
                   synchronized (lock) {
                      // 只有持有 lock 锁的线程可以执行这段代码
                   }
            }
       }
  
6.volatile：
变量：易变变量，确保所有线程看到的变量值是一致的，通常用于多线程环境。
    
7.transient：
变量：瞬态变量，不会被序列化。
    
8.native：
方法：本地方法，用于调用非 Java 代码（例如 C/C++ 代码）。
    
9.strictfp：
类、方法：确保浮点数运算的精确性和可移植性
```

### 4."==" 和  "equals()"

```java
"==",基本数据类型判断值，引用数据类型判断地址。
"equals()",引用类型的变量内容是否相等。
```

### 6.java网络编程

```java
服务端的"输入 " 接收的是  客户端的"输出"
客户端的"输入"  接受的是  服务端的"输出"
Socket 实现多线程服务器程序：
    
Client:
import java.io.*;
import java.net.*;
public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;
        //连接服务
        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to " + serverAddress);

            OutputStream output = socket.getOutputStream();//输出流
            PrintWriter writer = new PrintWriter(output, true);//每次调用println方法后自动刷新输出流

            InputStream input = socket.getInputStream();//输入流

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));//读取输入流
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));//控制台手动打印的数据

            String userInput;
            //读取控制台手动打印的数据
            while ((userInput = consoleReader.readLine()) != null) {
                //刷新输出流
                writer.println(userInput);
                //读取输入
                String serverResponse = reader.readLine();
                System.out.println("server response: " + serverResponse);
            }
        } catch (UnknownHostException e) {
            System.err.println("不明主机: " + serverAddress);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("无法获取 I/O 连接到: " + serverAddress);
            e.printStackTrace();
        }
    }
}

server:
import java.io.*;
import java.net.*;

class ClientHandler implements Runnable {
    public Socket clientSocket;

    //构造函数，
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (InputStream input = clientSocket.getInputStream(); //输入流
             OutputStream output = clientSocket.getOutputStream();//输出流
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));//读取输入流
             PrintWriter writer = new PrintWriter(output, true);//调用println方法自动刷新输出流
        ) {
            String clientMessage;
            //读取的输入流不为null
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("received client message :" + clientMessage);
                //刷新输出流
                writer.println("server response:" + clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


public class MultiThreadedServer {
    public static void main(String[] args) {
        int port = 12345;
        //启动服务
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server have started!Waiting for connection");
            while (true) {
                //客户端socket
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected :" + clientSocket.getInetAddress().getHostAddress());
                //为客户端socket建立一个新的线程处理通信
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

<img src="https://s2.loli.net/2024/08/26/fJvA7xRe2pLgbIS.png" alt="image.png" style="zoom:50%;" />





### 10.死锁

```java
1.产生原因 互相占着，都等着对面释放资源。
2.解决方法： 信号量 + 错峰
           public final Semaphore a1 = new Semaphore(1); //信号量
           if(a1.tryAcquire(1, TimeUnit.SECONDS)){}//1秒钟后尝试获取，信号量会--；
           a1.relase()//释放
           Thread.sleep(1000)//模拟真实 do something    
```

### 11.ThreadLocal

```java
它允许每个线程有自己的独立副本的变量，这样多个线程之间不会互相干扰。
https://www.runoob.com/java/thread-id.html
这个示例中,通过 initalValue 这个方法是ThreadLocal类中的一个保护方法，用于提供每个线程第一次访问ThreadLocal变量时的初始值   "设置"独立副本变量的初始值
protected Object initalValue(){
    print("in initialValue");
    return getNewID();
}

通过getThreadID  "获取"独立副本变量的初始值
public init getThreadID(){
    Integer id=(Integer) get();
    return id.intValue();
}
    
```

### 12.join 线程挂起

```java
 new SleepingThread().join(); //等待线程完成

```

### 13.生产者消费者问题

```java
https://www.runoob.com/java/thread-procon.html
需要解决线程争抢
synchronized 关键字：确保同一时间只有一个线程可以访问 get() 和 put() 方法，防止数据竞争。
wait() 和 notifyAll() 方法：用于线程间通信。wait() 使当前线程等待，直到其他线程调用 notifyAll() 来唤醒它们。
    
shared buffer{
     private int contents;//共享数据
     private boolean available=false //
     //消费者调用
     public synchronize int get(){
           while(available == false){ wait()};
           available=false;
           notifyAll();
           return contents;
     }
      //生产者调用
     public synchronized void put(int value){
           while(available == true){ wait()};
           available=true;
           contents=value;
           notifyAll();
     }
}
```

 ![png](https://www.runoob.com/wp-content/uploads/2015/05/2011091018554595.gif)

### 

### 14.Override(覆写)和Overload(重载)

```java
Override和Overload不同的是，如果方法签名不同，就是Overload，Overload方法是一个新方法；如果方法签名相同，并且返回值也相同，就是Override。
class Person {
    public void run() {
        System.out.println("Person run");
    }
}

class Student extends Person {
    @Override // Compile error!
    public void run() {
        System.out.println("Student run");
    }
}
public class Main {
    public static void main(String[] args) {
           Person p = new Student();
           p.run();//"Student run"  Java的实例方法调用是基于"运行时的实际类型"的动态调用，而非变量的声明类型。
    }
}

```

### 15.向上转型和向下转型

```java
同样，Student继承自Person
Student s = new Student();
Person p = s; // upcasting, ok
Object o1 = p; // upcasting, ok
Object o2 = s; // upcasting, ok    
这种把一个子类类型安全地变为父类类型的赋值，被称为向上转型（upcasting）。
    
Person p1 = new Student(); // upcasting, ok
Person p2 = new Person();
Student s1 = (Student) p1; // ok 在向下转型的时候，把p1转型为Student会成功，因为p1确实指向Student实例
Student s2 = (Student) p2; // runtime error! ClassCastException! 把p2转型为Student会失败，因为p2的实际类型是Person，不能把父类变为子类，因为子类功能比父类多，多的功能无法凭空变出来。   
和向上转型相反，如果把一个父类类型强制转型为子类类型，就是向下转型（downcasting）。
    
Person s1 = new Student();
Person p1 = (Student) s1; //ok
Student p2 = (Person) s1;//runtime error!
子类赋值给父类可以，父类赋值给子类不行！
    
向下转型，限制还是多！
```

### 16.abstract class 和 interface区别

|            | abstract class       | interface                   |
| :--------- | :------------------- | --------------------------- |
| 继承       | 只能extends一个class | 可以implements多个interface |
| 字段       | 可以定义实例字段     | 不能定义实例字段            |
| 抽象方法   | 可以定义抽象方法     | 可以定义抽象方法            |
| 非抽象方法 | 可以定义非抽象方法   | 可以定义default方法         |

```java
所谓interface，就是比抽象类还要抽象的"纯抽象"接口，因为它连字段都不能有。因为接口定义的所有方法默认都是public abstract的，所以这两个修饰符不需要写出来（写不写效果都一样）.
```

### 17.内部类

**1.Inner Class**

```java
class A {
    private int x = 1;//外部内A的实例变量x

    class B {
        private int x=100;//内部类B的实例变量x
        B() {
            System.out.println(A.this.x);//访问外部类 A 的实例变量 x
            System.out.println(this.x);//访问内部类B的实例变量x
        }
    }
}

public class Main {
    public static void main(String[] args) {
        A a = new A();//创建外部类A实例
        A.B ab = a.new B();//通过外部类实例 a 创建内部类 B 的实例
    }
}

tips:
内部类，除了有一个this指向自己，还隐含的有一个外部类实例。
```

**2.Anonymous Class**

```java
class A {
    void display() {
        System.out.println("A's display");
    }
}

public class Main {
    public static void main(String[] args) {
        A a = new A() {
            //匿名类的构造函数
            {
                System.out.println("Anonymous constructor");
            }
            @Override
            void display() { // 覆写父类方法
                System.out.println("Anonymous class display");
            }

            void test() { // 新定义的方法
                System.out.println("test method");
            }
        };

        a.display(); // 调用的是匿名类覆写的 display 方法，就是用的java的多态特性
        // a.test(); // 这是不允许的，因为 A 中没有 test 方法。可以new A(){....}.test()，去执行。
    }
}

```

### 18.Static Nested Class

```java
class A {
    private int x = 1; // A 的实例变量（静态内部类 B 不能直接访问）

    private static int y = 2; // A 的静态变量（静态内部类 B 可以直接访问）

    static class B {
        private int x = 100; // B 的实例变量

        B() {
            System.out.println(this.x); // 访问 B 的实例变量 x
            System.out.println(A.y); // 访问 A 的静态变量 y
        }
    }
}

public class Main {
    public static void main(String[] args) {
        A.B ab = new A.B(); // 直接创建静态内部类 B 的实例
    }
}
tips：
静态内部类：静态内部类 B 可以直接访问外部类 A 的静态成员（如 y），但不能访问外部类的实例成员（如 x）。
实例字段与静态字段：this.x 指的是 B 的实例字段，而 A.y 指的是 A 的静态字段。
独立性：静态内部类 B 的实例可以"独立于外部类 A 的实例"存在。
```

### 19.JDK JRE JVM区别

```
JDK > JRE > JVM。JDK是最大的，它包含JRE，而JRE又包含JVM。

JDK是开发工具，它让程序员编写Java程序。
JRE是运行环境，它让编写好的Java程序可以被运行。
JVM则是JRE的一部分，位于程序执行的最前沿，将字节码转化为机器代码。
```

### 20.String

```java
Java字符串的一个重要特点就是字符串不可变。这种不可变性是通过内部的private final char[]字段，以及没有任何修改char[]的方法实现的。前者确保私有性以及不能修改引用，后者确保不能修改内容。确保安全性。

String s = new String(new char[] {'H', 'e', 'l', 'l', 'o', '!'});
s=s.toUpperCase();
System.out.println(s);

这里的 转大写 实际上是重新生成的引用。
```

```java
char[] cs = "Hello".toCharArray();
String s = new String(cs);
System.out.println(s);
cs[0] = 'X';
System.out.println(s);
从String的不变性设计可以看出，如果传入的对象有可能改变，我们需要复制而不是直接引用。
```

### 21.4bit有符号表示[-8,7]

```
   符号位1代表负数，0代表正数   
   1001               0000                     0111
    ---------------------------------------------
    -7                 0                        7
    
   0111   取反得  1000  加1得  1001
   
   4bit   最大值是 0111，也就是十进制的7
   但是还缺一位啊！1000表示啥？表示-0?没意义啊，已经有一个表示0的了
   
   如果我要表示8呢? 4bit一定表示不了了，可以用5bit表示。
   5bit的8表示为0 1000。
   如果我要表示5bit的-8呢?
   0 1000 取反得  1 0111 加1得 1 1000 
   如果要表示4bit的-8呢？1 1000保留4bit,得到1000
   
   因此可以用1000表示4bit的-8.
```

<img src="https://neth-lab.netlify.app/publication/21-4-18-nubmer_system/pic/twos_comp.png" alt="png" style="zoom:50%;" />

### 22.Integer

```java
伪代码
class Integer {
    private int x;

    public Integer(int i) {
        this.x = i;
    }
    //静态工厂方法
    public static Integer valueOf(int i) {
        //缓存实例
        return new Integer(i);
    }
     /**
       @mind  String  s="4"; System.out.println(s.charAt(0)- '0');
     * @return {int}*/
    public static int parseInt(String s) {
        int length = s.length();
        int result = 0;
        int i = 0;
        // Process each character
        while (i < length) {
            char c = s.charAt(i++);
            // Convert character to digit
            int digit = c - '0';
            result = result * 10 + digit;
        }

        return result;
    }
    public int intValue() {
        return this.x;
    }
}
//转二进制代码
class Binary {
    private int data;

    public Binary(int data) {
        this.data = data;
    }

    public String valueOf() {
        if (data == 0) return "0";
        StringBuilder sb = new StringBuilder();
        int num = data;  // 使用临时变量来保存 data 的原始值

        while (num > 0) {
            sb.append(num % 2);
            num /= 2;
        }

        return sb.reverse().toString();  // 反转字符串并返回
    }
}


public class Main {
    public static void main(String[] args) {
        Integer i1 = new Integer(1);
        Integer i2 = Integer.valueOf(2);
        Integer i23 = Integer.valueOf(23);
        System.out.println(i1.intValue());
        System.out.println(i2.intValue());
        System.out.println(i23.intValue());

    }
}
```

### 23.enum

```java
按照 == 和 equal的定义，引用类型的值用equal。但是，enum类型的每个常量在JVM中只有一个唯一实例，所以可以直接用==比较：
enum Color {
    RED(0,"red"), GREEN(1,"green"), BLUE(2,"blue");
    public final String col;
    public final int order;
    private Color(int order,String col){
        this.col=col;
        this.order=order;
    }
}

public final class Color extends java.lang.Enum { // 继承自Enum，标记为final class,确保无法被继承
    // 每个实例均为全局唯一:
    public static final Color RED = new Color();
    public static final Color GREEN = new Color();
    public static final Color BLUE = new Color();
    // private构造方法，确保外部无法调用new操作符:
    private Color() {}
}
```

### 24.record

**1.假设我们希望定义一个Point类，有x、y两个变量，同时它是一个不变类，可以这么写:**

```java
public final class Point {
    private final int x;
    private final int y;
    public Point(int x, int y) { this.x = x;this.y = y;}
    public int x() {return this.x;}
    public int y() { return this.y;}
}
为了保证不变类的比较，还需要正确覆写equals()和hashCode()方法，这样才能在集合类中正常使用。
```

**2.java14引入了Record类，用法如下：**

```java
record Point(int x, int y) {}
public class Main {
    public static void main(String[] args) {
        Point p = new Point(123, 456);
        System.out.println(p.x());java
        System.out.println(p.y());
        System.out.println(p);
    }
}
```

**3.Record底层定义**

```java
final class Point extends Record {
    private final int x;
    private final int y;
    public Point(int x, int y) {this.x = x;this.y = y;}
    public int x() { return this.x;}
    public int y() {return this.y;}
    public String toString() {return String.format("Point[x=%s, y=%s]", x, y);}
    public boolean equals(Object o) { ...}
    public int hashCode() {...}
}

"record Point(int x, int y) {},"语法糖给创建了构造方法、和字段名同名方法，以及覆写toString()、equals()、hashCode()方法。
```

**4.Recorde构造方法**

```java
public record Point(int x, int y) {
    public Point {//Compact Constructor，它的目的是让我们编写检查逻辑
        if (x < 0 || y < 0) {//有一方负数就抛出IllegalArgumentException
            throw new IllegalArgumentException();
        }
    }
    //用于添加的静态方法of();
    public static Point of() { return new Point(0, 0);}
    public static Point of(int x, int y) {return new Point(x, y); }
}
public class Main {
    public static void main(String[] args) throws Exception {
        Point x=new Point(100,200);
        Point z=Point.of();
        Point z1=Point.of(1,2);
        Point z2=Point.of(1,-2);// err IllegalArgumentException! 出现负数了
    }
}
```

### 25.BIgDecimal

```java
对BigDecimal做加、减、乘时，精度不会丢失，但是做除法时，存在无法除尽的情况，这时，就必须指定精度以及如何进行截断：
BigDecimal d1 = new BigDecimal("123.456");
BigDecimal d2 = new BigDecimal("23.456789");
BigDecimal d3 = d1.divide(d2, 10, RoundingMode.HALF_UP); // 保留10位小数并四舍五入
BigDecimal d4 = d1.divide(d2); // 报错：ArithmeticException，因为除不尽   

BigDecimal用于表示精确的小数，常用于财务计算；
比较BigDecimal的值是否相等，必须使用compareTo()而不能使用equals()。
其他细节查看 https://liaoxuefeng.com/books/java/oop/core/bigdecimal/index.html
```

### 26.异常体系

![image.png](https://s2.loli.net/2024/08/26/bUQL7xP8DoEvasI.png)

- 必须捕获的异常，包括`Exception`及其子类，但不包括`RuntimeException`及其子类，这种类型的异常称为Checked Exception。通常表示可以预见的、正常情况下可能发生的异常情况，处理这些异常可以让程序更加健壮。

- 不需要捕获的异常，包括`Error`及其子类，`RuntimeException`及其子类，这种类型叫UncheckedException。通常表示编程错误或系统级错误，开发者应该通过调试和测试来修复这些问题，而不是在运行时处理它们。

- 异常的抛出用的是栈。从底层调用到上层调用。

- Java标准库定义的常用异常包括：

  ```java
  Exception
  ├─ RuntimeException
  │  ├─ NullPointerException
  │  ├─ IndexOutOfBoundsException
  │  ├─ SecurityException
  │  └─ IllegalArgumentException
  │     └─ NumberFormatException
  ├─ IOException
  │  ├─ UnsupportedCharsetException
  │  ├─ FileNotFoundException
  │  └─ SocketException
  ├─ ParseException
  ├─ GeneralSecurityException
  ├─ SQLException
  └─ TimeoutException
  ```

  

#### 26.1.捕获异常

```java
通俗来讲，在当前层的上层如果抛出了异常(CheckedException),当前层以及当前层下层抛出异常/捕获异常。
```

```java
import java.io.IOException;
//顶层
class TopError {
    public static void test() throws IOException {
        System.out.println("I'm going to test IOException!");
    }
}

//当前层捕获
class TestError {public static void fuck() {try{TopError.test();}catch(IOException e){}}}public class Main {
public static void main(String[] args) {TestError.fuck();}}

//当前层抛异常
class TestError { public static void fuck() throws IOException { TopError.test();}}
//当前层下层去捕获
public class Main {public static void main(String[] args) { try{TestError.fuck();}catch(IOException e){}}}
```

#### 26.2自定义异常

**RuntimeException源码**

```java
public class RuntimeException extends Exception {
    @java.io.Serial
    static final long serialVersionUID = -7034897190745766939L;
    public RuntimeException() {super();}
    public RuntimeException(String message) { super(message);}
    public RuntimeException(String message, Throwable cause) {super(message, cause);}
    public RuntimeException(Throwable cause) { super(cause);}
    protected RuntimeException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace); }
}
```

**模仿RuntimeException继承的自定义异常**

```java
public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {super();}
    public LoginFailedException(String message, Throwable cause{super(message, cause);}
    public LoginFailedException(String message) { super(message); }
    public LoginFailedException(Throwable cause) { super(cause);}
}
```

### 27.反射

#### 27.1反射class类

 每加载一种`class`，`JVM`就为其创建一个`Class`类型的实例，并关联起来。注意：这里的`Class`类型是一个名叫`Class`的`class`。它长这样：

```java
public final class Class {
    //只有JVM能创建Class实例,我们自己的Java程序是无法创建Class实例的。
    private Class() {}
}
```

以`String`类为例，当JVM加载`String`类时，它首先读取`String.class`文件到内存，然后，为`String`类创建一个`Class`实例并关联起来：`以下只是普通java代码,用于案例说明`

```java
Person p = new Person();
Class c = p.getClass();//getClass() 方法会访问这个对象头，从中提取 Class 对象的引用。getClass() 方法返回的 Class 对象是一个 java.lang.Class 实例，它包含了关于对象 p 的类的所有信息。
```

所以，JVM持有的每个`Class`实例都指向一个数据类型（`class`或`interface`）：

```
┌───────────────────────────┐
│      Class Instance       │────▶ String
├───────────────────────────┤
│name = "java.lang.String"  │
└───────────────────────────┘
        ......
```

一个`Class`实例包含了该`class`的所有完整信息：

```
┌───────────────────────────┐
│      Class Instance       │────▶ String
├───────────────────────────┤
│name = "java.lang.String"  │
├───────────────────────────┤
│package = "java.lang"      │
├───────────────────────────┤
│super = "java.lang.Object" │
├───────────────────────────┤
│interface = CharSequence...│
├───────────────────────────┤
│field = value[],hash,...   │
├───────────────────────────┤
│method = indexOf()...      │
└───────────────────────────┘
```

这种通过`Class`实例获取`class`信息的方法称为反射（Reflection）。

jvm`动态加载`类，用到的时候才加载。

对普通开发者来说，`Class` 类提供了用于`访问`类的结构和信息的权限，但`不允许直接修改`这些信息。这种设计确保了类的内部实现细节的`封装和安全性`。普通开发者通常只能使用 `Class` 类的方法来读取和操作类的信息，而不能直接创建或修改 `Class` 实例。

#### 27.2动态代理

代理类调用目标类，之前和之后，添加一些`预处理`和`后处理`的操作，扩展一些不属于目标类的功能。可以在一个地方（`InvocationHandler` 中）统一处理多个接口的不同方法调用，避免为每个接口单独实现不同的逻辑，从而提高代码的灵活性和可维护性。

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
interface Hello {void morning(String name);void afternoon(String name);}
interface Eat {void eatPig();void eatChicken();}
public class Main {
    public static void main(String[] args) {
        InvocationHandler handler = new InvocationHandler() {
            //方法拦截处理，统一处理逻辑
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("------------" + method);
                switch (method.getName()) {
                    case "morning":
                        System.out.println("Good morning, " + args[0]);
                        break;
                    case "afternoon":
                        System.out.println("Good afternoon, " + args[0]);
                        break;
                    case "eatPig":
                        System.out.println("Eat pig,");
                    case "eatChicken":
                        System.out.println("Eat chicken,");
                        break;
                    default:
                        System.out.println("Unknown method: " + method);
                }
                return null;
            }
        };
        //动态创建代理对象 
        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                new Class[]{Hello.class}, // 传入要实现的接口
                handler); // 传入处理调用方法的InvocationHandler
        hello.morning("早上好");
        hello.afternoon("下午好");
        //动态创建代理对象 
        Eat eats = (Eat) Proxy.newProxyInstance(
                Eat.class.getClassLoader(),
                new Class[]{Eat.class},
                handler
        );
        eats.eatPig();
        eats.eatChicken();
    }
}

```

### 28.泛型

#### 28.1为啥需要泛型

假如说没有泛型的概念，如果我要求list`每个元素`都是String,

```java
public class StringArrayList { 
    private String[] array;
    private int size;
    public void add(String e){...};
    public void remove(int index) {...};
    public String get(int index) {...};
}
StringArrayList list = new StringArrayList();
list.add("Hello");
String first = list.get(0);
```

接着上面的概念，如果我要求list`每个元素`都是Integer

```java
public class IntegerArrayList { 
    private Integer[] array;
    private int size;
    public void add(Integer e) {...}; 
    public void remove(int index) {...};
    public Integer get(int index) {...};
}
IntegerArrayList list =new IntegerArrayList();
list.add(new Integer(123));
Integer num=list.get(0);
```

 接着上面的概念，如果我要求list`每个元素`都是Long

接着上面的概念，如果我要求list`每个元素`都是Double

接着上面的概念，如果我要求list`每个元素`都是Float

......

无穷无尽了。

为了解决问题，需要把ArrayList变成一种`模板`,如下

```java
public class ArrayList<T> { 
    private T[] array;
    private int size;
    public void add(T e) {...};
    public void remove(int index) {...};
    public T get(int index) {...}
}
ArrayList<String> strList = new ArrayList<String>();
ArrayList<Float> floatList = new ArrayList<Float>();
ArrayList<Double> personList = new ArrayList<Double>();
........
```

#### 28.2泛型之向上转型

```java
public class ArrayList<T> implements List<T> {
    ...
}
List<String> list = new ArrayList<String>();
ArrayList继承自List
```

 不能把`ArrayList<Integer>`向上转型为`ArrayList<Number>`或`List<Number>`。举个反例：

```java
// 创建ArrayList<Integer>类型：
ArrayList<Integer> integerList = new ArrayList<Integer>();
// 添加一个Integer：
integerList.add(new Integer(123));
// “向上转型”为ArrayList<Number>：
ArrayList<Number> numberList = integerList;
// 添加一个Float，因为Float也是Number：
numberList.add(new Float(12.34));
// 从ArrayList<Integer>获取索引为1的元素（即添加的Float）：
Integer n = integerList.get(1); // ClassCastException!
Float转Integer产生ClassCastException!!!!
```

 总结：

泛型就是编写模板代码来适应任意类型；

泛型的好处是使用时不必对类型进行强制转换，它通过编译器对类型进行检查；

注意泛型的继承关系：可以把`ArrayList<Integer>`向上转型为`List<Integer>`（`T`不能变！），但不能把`ArrayList<Integer>`向上转型为`ArrayList<Number>`（`T`不能变成父类）。

#### 28.3 擦拭法

Java的泛型是由编译器在编译时实行的，编译器内部永远把所有类型`T`视为`Object`处理，但是，在需要转型的时候，编译器会根据`T`的类型自动为我们实行安全地强制转型。

*1.`<T>`不能是基本类型，例如`int`，因为实际类型是`Object`，`Object`类型无法持有基本类型：*

 因为泛型在运行时统一被识别为 `Object`，而基本类型（如 `int`、`char` 等）不是对象类型，因此不能直接作为泛型参数。

```java
Pair<int> p = new Pair<>(1, 2); // compile error!
```

*2.无法取得带泛型的`Class`。观察以下代码：*

```java
Pair<String> p1 = new Pair<>("Hello", "world");
Pair<Integer> p2 = new Pair<>(123, 456);
Class c1 = p1.getClass();
Class c2 = p2.getClass();
System.out.println(c1==c2); // true
System.out.println(c1==Pair.class); // true
```

所有泛型实例，无论`T`的类型是什么，`getClass()`返回同一个`Class`实例，因为编译后它们全部都是`Pair<Object>`。

*3.无法判断泛型类型*

```java
Pair<Integer> p = new Pair<>(123, 456);
// Compile error:
if (p instanceof Pair<String>) {
}
```

并不存在`Pair<String>.class`，而是只有唯一的`Pair.class`。

*4.不能实例化`T`类型：*

```java
public class Pair<T> {
    private T first;
    private T last;
    public Pair() {
        // Compile error:
        first = new T();//擦拭后变成了 new Object();
        last = new T();//擦拭后变成了 new Object();
    }
}
```

这样一来，创建`new Pair<String>()`和创建`new Pair<Integer>()`就全部成了`Object`，显然编译器要阻止这种类型不对的代码。

可以借助反射，实例化`T`类型。

```java
public class Pair<T> {
    private T first;
    private T last;
    public Pair(Class<T> clazz) {
        first = clazz.newInstance();
        last = clazz.newInstance();
    }
}
Pair<String> pair = new Pair<>(String.class);
```

因为传入了`Class<String>`的实例，所以我们借助`String.class`就可以实例化`String`类型

#### 28.4泛型继承

在继承了泛型类型的情况下，子类可以获取父类的泛型类型。例如：`IntPair`可以获取到父类的泛型类型`Integer`。获取父类的泛型类型代码比较复杂,用到了反射：

```java
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class Pair<T> {...}
class IntPair extends Pair<Integer> {
    public IntPair(Integer first, Integer last) {
        super(first, last);
    }
}
public class Main {
    public static void main(String[] args) {
        Class<IntPair> clazz = IntPair.class;//获取了 IntPair 类的 Class 对象。
        Type t = clazz.getGenericSuperclass();//获取了 IntPair 类的直接超类，即 Pair<Integer>。这个超类的类型是 Type。
        if (t instanceof ParameterizedType) {//ParameterizedType 是 Java 反射 API 中的一个接口，表示一个带有实际类型参数的泛型类型（如 Pair<Integer>）。
            ParameterizedType pt = (ParameterizedType) t;
            Type[] types = pt.getActualTypeArguments(); //如果是 ParameterizedType，我们可以通过 getActualTypeArguments() 获取实际类型参数。对于 Pair<Integer>，只有一个实际类型参数，即 Integer。
            Type firstType = types[0]; // 取第一个泛型类型
            Class<?> typeClass = (Class<?>) firstType;//将这个类型参数转换为 Class 对象
            System.out.println(typeClass); // Integer
        }
    }
}
```

```
                      ┌────┐
                      │Type│
                      └────┘
                         ▲
                         │
   ┌────────────┬────────┴─────────┬───────────────┐
   │            │                  │               │
┌─────┐┌─────────────────┐┌────────────────┐┌────────────┐
│Class││ParameterizedType││GenericArrayType││WildcardType│
└─────┘└─────────────────┘└────────────────┘└────────────┘
 
```

#### 28.5 extends通配符

*为啥要用extends通配符？*

```java
class Pair<T>{}
public class Main {
    public static void main(String[] args) {
        Pair<Integer> p = new Pair<>(123, 456);
        int n = add(p);
        System.out.println(n);
    }
    static int add(Pair<Number> p) {...}
}
//incompatible types: Pair<Integer> cannot be converted to Pair<Number>
```

上述代码报错，因为`Pair<Integer>`不是`Pair<Number>`的子类，

```java
class Pair<T>{}
public class Main {
    public static void main(String[] args) {
        Pair<Integer> p = new Pair<>(123, 456);
        int n = add(p);
        System.out.println(n);
    }
    static int add(Pair<? extends Number> p) {...}
}
```

`<? extends Number>`定义泛型的上界，可以传入Pair<Double>、Pair<Integer>等

```java

public class Main {
    public static void main(String[] args) {
        Pair<Integer> p = new Pair<>(123, 456);
        int n = add(p);
        System.out.println(n);
    }
    static int add(Pair<? extends Number> p) {
        p.setFirst(new Integer(first.intValue() + 100));//报错
        p.setLast(new Integer(last.intValue() + 100));
        return p.getFirst().intValue() + p.getFirst().intValue();
    }
}
//incompatible types: Integer cannot be converted to CAP#1
//where CAP#1 is a fresh type-variable:
//    CAP#1 extends Number from capture of ? extends Number
```

还是由于擦拭法，例如，`Pair<Double>`满足`Pair<? extends Number>`，然而，`Pair<Double>`的`setFirst()`显然无法接受`Integer`类型。这就是`<? extends Number>`通配符的一个重要限制：方法参数签名`setFirst(? extends Number)`无法传递任何`Number`的子类型给`setFirst(? extends Number)`。`null除外。`

*extends通配符作用？*

```java
class Pair<T> {
    T a;
    Pair(T a) {this.a = a;}
    public T get() {return a; }
    public void set(T a) { this.a = a;}
}
public class Main {
    static void add(Pair<? extends Number> p){
        Number n=p.get();
        System.out.println(n); //ok
        p.set(new Integer(123));//err

    }
    public static void main(String[] args) {
      Pair<Integer> p=new Pair<>(123);
      add(p);
    }
}
```

作用就是用于`只读。`

*使用extends限定T类型*

```java
public class Pair<T extends Number> { ... }
Pair<Number> p1 = null;
Pair<Integer> p2 = new Pair<>(1, 2);
Pair<Double> p3 = null;
```

总结：

使用类似`<? extends Number>`通配符作为方法参数时表示：可以读，不能写。

使用类似`<T extends Number>`定义泛型类时表示：泛型类型限定为`Number`以及`Number`的子类。

#### 28.6super通配符

```java
class Pair<T> {
    private T first;
    public Pair(T first) {this.first = first;}
    public T getFirst() {return first;}
    public void setFirst(T first) {this.first = first;}
}
void set(Pair<? super Integer> p, Integer first) { p.setFirst(first);}
```

`Pair<? super Integer>`表示，方法参数接受所有泛型类型为`Integer`或`Integer`父类的`Pair`类型。可以知道类型限制的`下限`。set方法要确保传递的类型是自己或子类。

```java
? super Integer getFirst();
Integer x = p.getFirst();//err
```

如果传入的实际类型是`Pair<Number>`，编译器无法将`Number`类型转型为`Integer`。

如果传入的实际类型是`Pair<Object>`，编译器也无法将`Object`类型转型为`Integer`。

```
Object obj = p.getFirst();
```

所有类的超类是Object,唯一可以接收`getFirst()`方法返回值的是`Object`类型.

#### 28.7对比extends和super通配符

- `<? extends T>`允许调用读方法`T get()`获取`T`的引用，但不允许调用写方法`set(T)`传入`T`的引用（传入`null`除外）；(编译器知道上限，读没问题。但是，写可能会产生没有继承关系之间的转换)

- `<? super T>`允许调用写方法`set(T)`传入`T`的引用，但不允许调用读方法`T get()`获取`T`的引用（获取`Object`除外);（编译器知道下限，写没问题。但是，读可能会产生上转下的情况）

- 提供一个两个结合使用的例子：

  ```java
  public class Collections {
      // 把src的每个元素复制到dest中:
      public static <T> void copy(List<? super T> dest, List<? extends T> src) {
          for (int i=0; i<src.size(); i++) {
              T t = src.get(i);//安全的读
              dest.add(t);//安全的写
          }
      }
  }
  ```


#### 28.8无限定通配符<?>

- 不允许调用`set(T)`方法并传入引用（`null`除外）；因为`不知道下限`。
- 不允许调用`T get()`方法并获取`T`引用（只能获取`Object`引用）。因为`不知道上限`。

```java
static boolean isNull(Pair<?> p) {
    return p.getFirst() == null || p.getLast() == null;
}
//or
static <T> boolean isNull(Pair<T> p) {
    return p.getFirst() == null || p.getLast() == null;
}
```

- `<?>`通配符有一个独特的特点，就是：`Pair<?>`是所有`Pair<T>`的超类

```java
Pair<Integer> p = new Pair<>(123, 456);
Pair<?> p2 = p; // 安全地向上转型
System.out.println(p2.getFirst() + ", " + p2.getLast());
```

#### 28.9泛型和反射*

由于问的chatgpt，真实性存疑,

```java
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
       static <K> K[] pickTwo(K k1, K k2, K k3) {return asArray(k1, k2); }
       static <T> T[] asArray(T... objs) {return objs;}
        String[] arr = asArray("one", "two", "three");
        String[] firstTwo = pickTwo("one", "two", "three"); // ClassCastException:
    }
}
```

前言:

- 在编译时生成的类型（如 `String[]`）是在泛型具体化过程中确定的，并不会被擦除后的签名改变。
- ·类型擦除**主要影响**方法签名及泛型类型信息，但**不会改变**已经生成的具体类型数组。相反，当涉及到泛型方法调用（如 `pickTwo` 中的 `asArray`），擦除后的签名影响了最终返回的实际类型。

错误分析：

- *调用asArray("one", "two", "three")*    Java 编译器会推断 `T` 为 `String`，返回的是 `String[]`。类型擦除后，T为`Object`,返回的是`Object[]`。但是，由于编译时已生成具体类型的数组（即 `String[]`），类型擦除不会改变已经生成的数组类型。因此，在运行时返回的是 `String[]`。

- *调用pickTwo("one", "two", "three")*     java编译器会推断 `K` 为 `String`，返回的是`String[]`。调用asArray(String k1,String k2),期望返回String[]。类型擦除后，K为`Object`,返回`Object[]`。涉及到泛型方法调用,。调用asArray(Object k1, Object k2),返回`Object[]`。

### 29.Object和Class区别？

**`Object` 的职责**：`Object` 类的职责是作为所有类的根类，提供最基本的功能和行为。它定义了每个 Java 对象都应有的一些基础方法，比如 `equals`、`hashCode`、`toString` 等。这些方法是所有对象都需要的，而不仅仅是类（`Class`）对象。

**`Class` 的职责**：`Class` 类的职责是描述类的结构和行为，它是 Java 反射机制的核心。`Class` 提供了获取类的元数据（如方法、字段、构造器等）和执行反射操作的功能。`Class` 代表的是对类本身的描述和操作，而不是具体的对象行为。

为啥不能把Object中的功能集成到Class呢？

按照chagpt说法，主要是

1.从性能角度。如果集成了，每个对象实例会携带不必要的元数据或反射功能，浪费资源。反射是相对昂贵的操作，在少数情况下使用。

2.从兼容角度。一开始就是分开设计的，集成后对之前的版本造成影响。

### 30.集合

#### 30.1 使用list

|                     | ArrayList    | LinkedList           |
| ------------------- | ------------ | -------------------- |
| 获取指定元素        | 速度很快     | 需要从头开始查找元素 |
| 添加元素到末尾      | 速度很快     | 速度很快             |
| 在指定位置添加/删除 | 需要移动元素 | 不需要移动元素       |
| 内存占用            | 少           | 较大                 |

`List.of();只读`

List.contains()、List. indexOf()实例必须覆写equals方法，例如

```java
class Person{
    String name;
    int age;
    public Person(String name,int age){this.name=name;this.age=age};
    @Override
    public boolean equals(Object o) {
        if (o instanceof Person p) {//对引用类型用Objects.equals()比较，对基本类型直接用==比较
          return Objects.equals(this.name, p.name) && this.age == p.age;
        }
        return false;
    }
}
```

#### 30.2使用HashMap

```java
Map<String, Integer> map = new HashMap<>();
```

两个对象的key有`不同的内存地址`，但是`内容相同`。HashMap默认把它们视为`不同的键`。

两个对象的key有`不同的内存地址`，但是`内容相同`。而你希望它们被视为`同一个键`时,通过`hashcode()`获取哈希码，如果哈希码相同，它会使用`equals()`比较键的内容，确保是否为相同的键。

```java
class Person{
    String name;
    int age;
    public Person(String name,int age){this.name=name;this.age=age};
    @Override
    public boolean equals(Object o) {
        if (o instanceof Person p) {//对引用类型用Objects.equals()比较，对基本类型直接用==比较
          return Objects.equals(this.name, p.name) && this.age == p.age;
        }
        return false;
    }
    @Override
    public int hashCode() {return Objects.hash(firstName, age);}
}

```

两个对象的key有`不同的内存地址&&n内容不同`，但是`hashcode相同`。这种情况是哈希冲突，解决方法就是用`List`存储`hashCode()`相同的`key-value`。大白话，哈希冲突，考虑到了，hashcode一样的有很多个，找到匹配的一项进行修改，找不到匹配的一项就插入。

#### 30.3HashMap索引

直接用hashcode当索引是不可能的，太大了，太太太太`占用空间`了。要把hashcode转化成`有限空间最小索引`，减少空间占用。

假设数组长度`n = 16`（`n`总是2的幂），我们有一个对象的`hashCode()`返回`12345`。假设经过`扰动函数`处理减少哈希冲突，后得到的哈希值为`hash = 12356`。

```java
index = (16 - 1) & 12356  // 即 15 & 12356
```

二进制表示：

```
15  -> 0000 1111  （16 - 1 的二进制表示）
12356 -> 1100 0000 0100
```

进行位与运算：

```
          0000 1111
&    1100 0000 0100
-------------------
          0000 0100 -> 4
```

最终的下标是`4`，

如果下标超过当前数组`一定比例`长度，当前数组长度`翻倍`，然后重新计算每个下标位置。

#### 30.4*TreeMap

`SortedMap`在遍历时严格按照Key的顺序遍历，最常用的实现类是`TreeMap`；作为`SortedMap`的Key必须实现`Comparable`接口，或者传入`Comparator`；要严格按照`compare()`规范实现比较逻辑，否则，`TreeMap`将不能正常工作。

```java
 Map<Student, Integer> map = new TreeMap<>(new Comparator<Student>() {
            public int compare(Student p1, Student p2) {
                if (p1.score == p2.score) { return 0;}
                return p1.score > p2.score ? -1 : 1;
            }
});
```

 底层红黑树！目前已经实现二叉搜索树、平衡树、b树、b+树，

等哪天，无聊的时候，攻克红黑树。

红黑树攻克完毕！看我github

#### 30.5Iterator

自己编写集合类，满足以下条件：

- 集合类实现`Iterable`接口，该接口要求返回一个`Iterator`对象；
- 用`Iterator`对象迭代集合内部数据。

```java
import java.util.*;
class ReverseList<T> implements Iterable<T> {
    class ReverseIterator implements Iterator<T> {
        int index;

        ReverseIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index > 0;
        }

        @Override
        public T next() {
            index--;
            //ReverseList.this 外部类的引用
            return ReverseList.this.list.get(index);
        }
    }

    private List<T> list = new ArrayList<>();

    public void add(T t) {
        list.add(t);
    }

    @Override
    public Iterator<T> iterator() {
        return new ReverseIterator(list.size());
    }
}
public class Main {
    public static void main(String[] args) {
        ReverseList<String> rlist = new ReverseList<>();
        rlist.add("Apple");
        rlist.add("Orange");
        rlist.add("Pear");
        for (Iterator<String> it = rlist.iterator(); it.hasNext(); ) {
            String s = it.next();
            System.out.println(s);
        }
    }
}
```

### 31.IO

IO流是一种流式的数据输入/输出模型：

- `InputStream`: 从外部源读取`byte`到内存中。`OutputStream`: 从内存中写入`byte`到外部目标（如文件、网络等）。
- `Reader`: 从外部源读取`char`到内存中。`Writer`: 从内存中写入`char`到外部目标（如文件）。

Java标准库的`java.io`包提供了同步IO功能：

- 字节流接口：`InputStream`/`OutputStream`；
- 字符流接口：`Reader`/`Writer`。

#### 31.1 InputStream

`InputStream`并不是一个接口，而是一个抽象类，它是所有输入流的超类，这个抽象类定义的一个最重要的方法就是`int read()`。

```java
public abstract int read() throws IOException;
```

这个方法会读取输入流的下一个`无符号字节`，并返回字节表示的`int`值（0~255）。如果已读到末尾，返回`-1`表示不能继续读取了。

```java
 try (InputStream input = new FileInputStream("Notes\\test.txt")) {
        byte[] buffer = new byte[1000];// 定义1000个字节大小的缓冲区:
        int n;
        while ((n = input.read(buffer)) != -1) { //读取1000字节到缓冲区，这个read是阻塞的，必须等read返回后才能读取下一行。返回读取字节数
            System.out.println(n);
        }
}//InputStream底层实现了java.lang.AutoCloseable接口，编译器自动设定input.close()关闭资源;
```

   假设`Notes\\test.txt`里面有个‘7'，如下：

- 文件中字符 `'7'` ---->二进制表示为`00110111 `--->ASCII 值 `55`。ascii表就是ascii值和字符的映射。

- input.read()读到1byte（8bit）也就是`00110111`十进制转换后就是`55`。

  

但是，为了测试需要，也不能每次都在真实文件中写数据测试吧。`ByteArrayInputStream`可以在内存中模拟一个`InputStream`：

```java
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        byte[] data = { 72, 101, 108, 108, 111, 33 };//byte是个有符号的(-128,127)
        try (InputStream input = new ByteArrayInputStream(data)) {
            String s = readAsString(input);
            System.out.println(s);
        }
    }

    public static String readAsString(InputStream input) throws IOException {
        int n;
        StringBuilder sb = new StringBuilder();
        while ((n = input.read()) != -1) {//挨个读取 byte数组中的每个字节对应的十进制表示
            sb.append((char) n);//(char) n 将字节值 n 转换为对应的字符
        }
        return sb.toString();
    }
}
```

悟性,如下：

- ‘7’对应的二进制表示为 `00110111`，十进制表示为55 
- 7对应的二进制表示为`00000111`，十进制表示为7 
- 设计ascii就是为了，避免混淆数字和字符，统一用户接触的大部分是字符，然后系统后期根据ascii转换。



#### 31.2 OutputStream

`OutputStream`也是抽象类，它是所有输出流的超类。这个抽象类定义的一个最重要的方法就是`void write(int b)`，签名如下：

```java
public abstract void write(int b) throws IOException;
```

这个方法会写入一个字节到输出流。要注意的是，虽然传入的是`int`参数，但只会写入一个字节，即只写入`int`最低8位表示字节的部分

flush略

`try(resource)`来保证`OutputStream`在无论是否发生IO错误的时候都能够正确地关闭：

```java
public void writeFile() throws IOException {
    try (OutputStream output = new FileOutputStream("out/readme.txt")) {
        output.write("Hello".getBytes("UTF-8")); // Hello
    } // 编译器在此自动为我们写入finally并调用close()
}
```

和`InputStream`一样，`OutputStream`的`write()`方法也是阻塞的。

`ByteArrayOutputStream`可以在内存中模拟一个`OutputStream`：

```java
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] data;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            output.write("Hello ".getBytes("UTF-8"));
            output.write("world!".getBytes("UTF-8"));
            data = output.toByteArray();//ByteArrayOutputStream实际上是把一个byte[]数组在内存中变成一个OutputStream，
        }
        System.out.println(new String(data, "UTF-8"));
    }
}
```

复制

```java
// 读取input.txt，写入output.txt:
try (InputStream input = new FileInputStream("input.txt");
     OutputStream output = new FileOutputStream("output.txt"))
{
    input.transferTo(output); // transferTo的作用是复制
}
```

#### 31.3   读取classpath资源

`getResourceAsStream()` 是基于类路径查找的

```java
try (InputStream input = getClass().getResourceAsStream("/default.properties")) {
    // TODO:
}
```

`FileInputStream`使用绝对路径或相对路径手动加载文件，而不依赖类路径：

```java
try (InputStream input = new FileInputStream("lib/default.properties")) {
  
} 

```

### 32.日期和时间

<img src="https://s2.loli.net/2024/09/30/TdGxpr6RWbD4lzN.png" alt="image.png" style="zoom:67%;" />

### 33.单元测试

#### 33.1 编写JUnit测试

以下使用Intellij Idea测试的：

Project Stucture -->设置指定文件夹为 `Tests`。

编写class--->鼠标移动到class上,然后右击，再点击goto,再点击test,再点击Create New Test 自动生成测试模板了--->编写测试实现--->运行测试

`tips：`测试不要忘了写入口main函数

#### 33.2 使用Fixture

`@BeforeEach` 、`@AfterEach`、`@Test`

先收集所有的`@Test`方法。创建实例。`@BeforeEach` 初始化。`@Test`执行方法。`@AfterEach`清理。

#### 33.4异常测试

```java
@Test
void testNegative() {
    //assertThrows捕获到IllegalArgumentException类型的异常时，通过测试
    assertThrows(IllegalArgumentException.class, new Executable() {
        @Override
        public void execute() throws Throwable {
            Factorial.fact(-1);
        }
    });
}
```

#### 33.5条件测试

```java
@Disabled //禁止运行
@Test
void testBug101() {
    // 这个测试不会运行
}
.....
@EnabledOnOs(OS.WINDOWS) //只能在win上运行  
@DisabledOnOs(OS.WINDOWS)//禁止在win上运行
@DisabledOnJre(JRE.JAVA_8)//只能在java9以上运行
....

```

#### 33.6参数化测试

```java
@ParameterizedTest
@ValueSource(ints = { -1, -5, -100 })
void testAbsNegative(int x) {
    assertEquals(-x, Math.abs(x));
}
-（-1) == 1
-（-5) == 5
-（-100) == 100    
其他用法略
.....
```

### 34.正则表达式

单个字符的匹配规则如下：

| 正则表达式 | 规则                     | 可以匹配                       |
| ---------- | ------------------------ | ------------------------------ |
| `A`        | 指定字符                 | `A`                            |
| `\u548c`   | 指定Unicode字符          | `和`                           |
| `.`        | 任意字符                 | `a`，`b`，`&`，`0`             |
| `\d`       | 数字0~9                  | `0`~`9`                        |
| `\w`       | 大小写字母，数字和下划线 | `a`~`z`，`A`~`Z`，`0`~`9`，`_` |
| `\s`       | 空格、Tab键              | 空格，Tab                      |
| `\D`       | 非数字                   | `a`，`A`，`&`，`_`，……         |
| `\W`       | 非\w                     | `&`，`@`，`中`，……             |
| `\S`       | 非\s                     | `a`，`A`，`&`，`_`，……         |

多个字符的匹配规则如下：

| 正则表达式 | 规则             | 可以匹配                 |
| ---------- | ---------------- | ------------------------ |
| `A*`       | 任意个数字符     | 空，`A`，`AA`，`AAA`，…… |
| `A+`       | 至少1个字符      | `A`，`AA`，`AAA`，……     |
| `A?`       | 0个或1个字符     | 空，`A`                  |
| `A{3}`     | 指定个数字符     | `AAA`                    |
| `A{2,3}`   | 指定范围个数字符 | `AA`，`AAA`              |
| `A{2,}`    | 至少n个字符      | `AA`，`AAA`，`AAAA`，……  |
| `A{0,3}`   | 最多n个字符      | 空，`A`，`AA`，`AAA`     |

复杂匹配规则主要有：

| 正则表达式 | 规则                 | 可以匹配                             |
| ---------- | -------------------- | ------------------------------------ |
| ^          | 开头                 | 字符串开头                           |
| $          | 结尾                 | 字符串结束                           |
| [ABC]      | […]内任意字符        | A，B，C                              |
| [A-F0-9xy] | 指定范围的字符       | `A`，……，`F`，`0`，……，`9`，`x`，`y` |
| [^A-F]     | 指定范围外的任意字符 | 非`A`~`F`                            |
| AB\|CD\|EF | AB或CD或EF           | `AB`，`CD`，`EF`                     |

分组匹配：

```java
//时间匹配，(00-19|20-23):(00-59):(00-59)
String time = "23:01:59";
Pattern pattern = Pattern.compile("([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)");
Matcher matcher = pattern.matcher(time);

if (matcher.matches()) {
     String hour = matcher.group(1);
     String minute = matcher.group(2);
     String second = matcher.group(3);

     System.out.println("时: " + hour);
     System.out.println("分: " + minute);
     System.out.println("秒: " + second);
}
```

非贪婪匹配：

```java
123000
"(\\d+)(0*)",123000,“”
"(\\d+?)(0*)",123,"" 
"?"尽可能少的匹配  
```

搜索替换

```
分割 spilit
搜索 find（）
替换 replace
反向引用 replaceAll
```

### 35.加密与安全

**1.编码算法**

url编码、base64编码

**2.哈希算法**

哈希碰撞、MD5、加盐（HmacMD5）、SHA-1....

Java.security,允许第三方提供包无缝接入，可以引入外部加密算法

**3.加密算法**

 公钥加密私钥解密、数字证书是各种加密算法的集成....

### 36*.多线程

#### 36.1 多进程 vs 多线程

和多线程相比，多进程的缺点在于：

- 创建进程比创建线程开销大，尤其是在Windows系统上；
- 进程间通信比线程间通信要慢，因为线程间通信就是读写同一个变量，速度很快。

而多进程的优点在于：

多进程稳定性比多线程高，因为在多进程的情况下，一个进程崩溃不会影响其他进程，而在多线程的情况下，任何一个线程崩溃会直接导致整个进程崩溃。

#### 36.2 intellij idea多线程debugger技巧

1 thread模式 `Cannot evaluate, current stack frame doesn't support evaluation"`

```java
// 多线程
public class Main {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            //打断点，susepend thread**
            String message = "start new thread!";
            System.out.println(message);
        }, "Thread-1");
        t.start(); // 启动新线程
        Thread t2 = new Thread(() -> {
            //打断点，suspend thread**
            String message = "start new thread2!";
            System.out.println(message);
        }, "Thread-2");
        t2.start(); // 启动新线程
        System.out.println('a');
        System.out.println('b');
        System.out.println('c');
        System.out.println('d');
        System.out.println('e');
    }
}
```

均是thread模式。切到线程2，一只向下走，直到结束，报错`Cannot evaluate, current stack frame doesn't support evaluation"`，无法继续切回线程1。分析：

不是死锁，`线程1`只是被 debugger 挂起。`线程2`执行结束后，IDEA 可能丢失当前调试上下文，导致切回`线程1`异常。多线程调试时不要等某个线程执行完再切换，应该保持线程暂停状态进行切换。

技巧:下一步、下一步、当前任务结束了就 Resume，让线程池去调度下一个任务。

2 thread 和 all 的区别

| 模式       | 断点命中时                     | 其他线程有断点时               | 典型使用场景                                                 |
| ---------- | ------------------------------ | ------------------------------ | ------------------------------------------------------------ |
| **Thread** | 只有命中线程暂停，其他继续运行 | 其他线程跑到断点也会暂停       | 调试并发问题、锁竞争、线程间协作、观察其他线程在当前线程停住时会做什么 |
| **All**    | 所有线程立即全部暂停           | 所有线程已经暂停，不会跑到断点 | 查看稳定现场、检查共享变量状态、分析调用栈、排查死锁、获取某一时刻系统全貌 |

```java
//测试代码，
public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("t1 running");  // 断点1
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("t2 running");  // 断点2
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
```

#### 36.3 线程的状态

Java线程的状态有以下几种：

- New：新创建的线程，尚未执行；

- Runnable：运行中的线程，正在执行`run()`方法的Java代码；

- Blocked：运行中的线程，因为某些操作被阻塞而挂起；

- Waiting：运行中的线程，因为某些操作在等待中；

- Timed Waiting：运行中的线程，因为执行`sleep()`方法正在计时等待；

- Terminated：线程已终止，因为`run()`方法执行完毕。

  `BLOCKED` 和 `WAITING`和`TIMED_WAITING` 最大区别：

  > **BLOCKED 是“想运行，但进不去”-等锁**
  > **WAITING 是“进去了，但主动停下来等别人”-主动等待**
  >
  > **TIMED_WAITING是"最多等多久，事件到了自己恢复"**

```java
// 中断线程
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new MyThread();
        t.start();
        Thread.sleep(1000);
        t.interrupt(); // 中断t线程
        t.join(); // 等待t线程结束
        System.out.println("end");
    }
}

class MyThread extends Thread {
    public void run() {
        Thread hello = new HelloThread();
        hello.start(); // 启动hello线程
        try {
            hello.join(); // 等待hello线程结束
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
        }
        hello.interrupt();
    }
}

class HelloThread extends Thread {
    public void run() {
        int n = 0;
        while (!isInterrupted()) {
            n++;
            System.out.println(n + " hello!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("interrupted2!");
                break;
            }
        }
    }
}

```

**只有调用 `sleep()`、`join()`、`wait()` 这类可响应中断的方法时，线程处于阻塞状态，并且被其他线程调用 `interrupt()`，才会抛出 `InterruptedException`。**

其他方法用`共享内存标志位`

```java
// 中断线程
public class Main {
    public static void main(String[] args)  throws InterruptedException {
        HelloThread t = new HelloThread();
        t.start();
        Thread.sleep(1);
        t.running = false; // 标志位置为false
    }
}

class HelloThread extends Thread {
    public volatile boolean running = true; //violatile关键字，总是获取主内存最新值，被修改后，立刻回写到主内存，确保一致性。
    public void run() {
        int n = 0;
        while (running) {
            n ++;
            System.out.println(n + " hello!");
        }
        System.out.println("end!");
    }
}

```

tips：用js实现`多线程磁盘读取速率控制器`中，令牌桶的设计用到了共享内存，最终因为浏览器安全策略域名兼容等问题撤掉了。

#### 36.4 守护线程

守护线程是指为其他线程服务的线程。在JVM中，所有非守护线程都执行完毕后，无论有没有守护线程，虚拟机都会自动退出。

```java
Thread t = new MyThread();//这里面是个无限循环  while（true）{} ,这个线程不结束，jvm进程就不发结束。
t.setDaemon(true);//守护线程，不能持有需要关闭的资源（如打开文件等）
t.start();
```

#### 36.5 线程同步

##### 36.5.1 synchronized

`synchronized`关键字，锁对象。

```java
class Counter {
    private int count = 0;
    private final Object lock = new Object();  // 私有锁
    
    public void add() {
        synchronized (lock) {  // 锁的是 lock，不是 this
            count++;
        }
    }
}

// 使用
Counter c = new Counter();
new Thread(() -> c.add()).start();  // 锁的是 lock
// 外部无法用 synchronized(c) 干扰，更安全
```

锁实例

```java
class Counter {
    private int count = 0;
    
    // 方式1：同步方法
    public synchronized void add() {
        count++;
    }
    
    // 方式2：同步块锁 this（等价于方式1）
    public void add2() {
        synchronized (this) {
            count++;
        }
    }
}

// 使用
Counter c = new Counter();
new Thread(() -> c.add()).start();   // 锁的是 c
new Thread(() -> c.add2()).start();  // 锁的也是 c，互斥 ✅
```

涉及到读写的一致性，考虑加锁。

##### 36.5.2 volatile

```java
public class TaskRunner {
    private volatile boolean running = true;
    private final List<String> tasks = new ArrayList<>();
    
    // 停止信号 - 利用 volatile 的可见性
    public void stop() {
        running = false;
    }
    
    // 添加任务 - 需要同步保护集合操作
    public synchronized void addTask(String task) {
        tasks.add(task);
    }
    
    // 执行任务 - 结合两者
    public void execute() {
        while (running) {  // volatile 保证能及时看到停止信号
            String task = null;
            
            synchronized (this) {  // 同步保护集合操作
                if (!tasks.isEmpty()) {
                    task = tasks.remove(0);
                }
            }
            
            if (task != null) {
                process(task);
            }
        }
    }
    
    private void process(String task) {
        System.out.println("Processing: " + task);
    }
}

```

 各司其职: volatile 管可见性,synchronized 管原子性

##### 36.5.3  死锁

```java
public void add(int m) {
    synchronized(lockA) { // 获得lockA的锁
        this.value += m;
        synchronized(lockB) { // 获得lockB的锁
            this.another += m;
        } // 释放lockB的锁
    } // 释放lockA的锁
}

public void dec(int m) {
    synchronized(lockB) { // 获得lockB的锁
        this.another -= m;
        synchronized(lockA) { // 获得lockA的锁
            this.value -= m;
        } // 释放lockA的锁
    } // 释放lockB的锁
}
```

add 获取 lockA锁，dec获取lockB锁；

add等lockB释放，dec等lockA释放，产生死锁。

解决方案：都按照先lockA在lockB顺序。

##### 36.5.4 wait 和 notify

正确用法如下：

```java
class TaskQueue {
    Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            //释放this锁:
            this.wait();
            //重新获取this锁
        }
        return queue.remove();
    }
}

```

如果：

```java
public synchronized String getTask() {
        while (queue.isEmpty()) {
        }
        return queue.remove();
}
```

会导致addTask一直拿不到锁。

##### 36.5.5 ReentrantLock

```java
public class Counter {
    private final Lock lock = new ReentrantLock();
    private int count;

    public void add(int n) {
        lock.lock();
        try {
            count += n;
        } finally {
            lock.unlock();
        }
    }
}
```

或者

```java
if (lock.tryLock(1, TimeUnit.SECONDS)) { //最多等一秒去获取锁
    try {
        ...
    } finally {
        lock.unlock();
    }
}
```

`synchronized` 是 JVM 层面的锁。

`ReentrantLock` 是 Java API 层面的锁，高级控制（超时）用 它。

| 能力                   | `synchronized` | `ReentrantLock`                 |
| ---------------------- | -------------- | ------------------------------- |
| 自动释放锁             | ✅              | ❌（需 `finally` 中 `unlock()`） |
| 尝试获取锁             | ❌              | ✅ `tryLock()`                   |
| 超时等待               | ❌              | ✅ `tryLock(timeout)`            |
| 可中断等待             | ❌              | ✅ `lockInterruptibly()`         |
| 公平锁                 | ❌              | ✅                               |
| 多个等待条件           | ❌              | ✅ `Condition`                   |
| 指定唤醒哪类线程       | ❌              | ✅                               |
| 更适合构建复杂并发组件 | 一般           | ✅                               |

##### 36.5.6 ReentrantLock + Condition

```java
class TaskQueue {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void addTask(String s) {
        lock.lock();
        try {
            queue.add(s);
            condition.signalAll();//`唤醒`所有等待线程
        } finally {
            lock.unlock();//'放行'
        }
    }

    public String getTask() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                condition.await();
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }
}

```

##### 36.5.7，ReadWriteLock ,例子：手写读写锁

多线程同时读，只允许一个线程写

```java
public class Counter {
    private int[] counts = new int[10];
    // 当前正在读的线程数量
    // 多个读线程可以同时读，所以需要记录数量
    private int reading = 0;
    // 是否有写线程正在执行
    // 写是独占的，只允许一个线程写
    private boolean writing = false;
    public void inc(int index) {
        // 获取写权限
        synchronized (this) {
            // 当前对象锁保护 writing 和 reading 状态
            //
            // 如果：
            // 1. 有读线程正在读
            // 2. 已经有其他写线程正在写
            //
            // 当前写线程等待
            while (reading > 0 || writing) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // 标记自己获得写权限
            // 后续其他读线程看到 writing=true 会等待
            // 其他写线程也会等待
            writing = true;
        }
        // 真正写数据
        // 这里没有 synchronized
        // 因为上面 writing=true 已经保证：
        // 1. 没有读线程
        // 2. 没有其他写线程
        //
        // 所以当前线程可以独占修改 counts
        try {
            counts[index]++;
        } finally {
            // 释放写权限
            // synchronized(this) 的作用：
            // 1. 保证 writing=false 修改安全
            // 2. 保证 notifyAll() 合法调用
            // 3. 防止其他线程看到错误的 writing 状态
            synchronized (this) {
                writing = false;
                // 唤醒等待的读线程和写线程
                // 让它们重新竞争锁
                notifyAll();
            }
        }
    }



    public int[] get() {
        // 获取读权限
        synchronized (this) {
            // 如果当前有写线程
            // 说明 counts 正在修改
            // 当前读线程等待
            while (writing) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // 注册一个读线程
            // 作用：
            // 告诉写线程：
            // "现在有人正在读，不允许写"
            reading++;
        }
        // 真正读数据
        // 这里没有 synchronized
        //
        // 所以多个读线程可以同时执行
        try {
            return Arrays.copyOf(counts, counts.length);
        } finally {
            // 注销读线程
            // 必须加 synchronized
            // 因为多个读线程可能同时结束，
            // reading-- 必须保证线程安全
            synchronized (this) {
                reading--;
                // 最后一个读线程离开
                // 说明现在没有读者了
                // 可以唤醒等待写线程
                if (reading == 0) {
                    notifyAll();
                }
            }
        }
    }
}
```

写的有些复杂，如果有库能实现最好

```java
public class Counter {
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    // 注意: 一对读锁和写锁必须从同一个rwlock获取:
    private final Lock rlock = rwlock.readLock();
    private final Lock wlock = rwlock.writeLock();
    private int[] counts = new int[10];

    public void inc(int index) {
        wlock.lock(); // 加写锁
        try {
            counts[index] += 1;
        } finally {
            wlock.unlock(); // 释放写锁
        }
    }

    public int[] get() {
        rlock.lock(); // 加读锁
        try {
            return Arrays.copyOf(counts, counts.length);
        } finally {
            rlock.unlock(); // 释放读锁
        }
    }
}

```

使用`ReadWriteLock`可以提高读取效率：

- `ReadWriteLock`只允许一个线程写入；
- `ReadWriteLock`允许多个线程在没有写入时同时读取；
- `ReadWriteLock`适合读多写少的场景。

##### 36.5.8 StampedLock 乐观锁 + 悲观锁

真正的CAS乐观锁，自旋锁。

```java
import java.util.concurrent.atomic.AtomicInteger;
public class Counter {
    // AtomicInteger 底层就是 CAS
    private final AtomicInteger count = new AtomicInteger(0);
    /**
     * 自增
     */
    public void inc() {
        while (true) {
            // 读取旧值
            int oldValue = count.get();
            // 计算新值
            int newValue = oldValue + 1;
            // CAS
            // 如果 count 仍然是 oldValue，就改成 newValue
            if (count.compareAndSet(oldValue, newValue)) {
                return;
            }
            // 有人已经修改了
            // CAS失败
            // 继续循环重试
        }
    }
    /**
     * 获取值
     */
    public int get() {
        return count.get();
    }
}
```

版本号乐观锁，synchronized、AtomicInteger

```java
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
public class Counter {
    private int[] counts = new int[10];
    // 数据版本
    private final AtomicInteger version = new AtomicInteger();
    public void inc(int index) {
        synchronized (this) {
            counts[index]++;
            version.incrementAndGet();
        }
    }
    public int[] get() {
        while (true) {
            int v1 = version.get();
            int[] copy = Arrays.copyOf(counts, counts.length);
            int v2 = version.get();
            if (v1 == v2) {
                return copy;
            }
            // 有人修改了
            // 重试
        }
    }
}
```

调库。

```java
public class Point {
    private final StampedLock stampedLock = new StampedLock();
    private double x;
    private double y;
    public void move(double deltaX, double deltaY) {
        long stamp = stampedLock.writeLock(); // 获取写锁
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlockWrite(stamp); // 释放写锁
        }
    }

    public double distanceFromOrigin() {
        long stamp = stampedLock.tryOptimisticRead(); // 获得一个乐观读锁
        // 注意下面两行代码不是原子操作
        // 假设x,y = (100,200)
        double currentX = x;
        // 此处已读取到x=100，但x,y可能被写线程修改为(300,400)
        double currentY = y;
        // 此处已读取到y，如果没有写入，读取是正确的(100,200)
        // 如果有写入，读取是错误的(100,400)
        if (!stampedLock.validate(stamp)) { // 检查乐观读锁后是否有其他写锁发生
            stamp = stampedLock.readLock(); // 获取一个悲观读锁，把写入锁住
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlockRead(stamp); // 释放悲观读锁
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
```

使用场景：大部分情况下，读写不会冲突，即使冲突了，二次读写成本也不高。

##### 36.5.9Semaphore 限制线程并发数量

可以利用 `wait()/notifyAll()`：

```java
public class AccessLimitControl {
    private int permits = 3;
    public String access() throws InterruptedException {
        synchronized (this) {
            while (permits == 0) {
                wait();
            }
            permits--;
        }
        try {
            return UUID.randomUUID().toString();
        } finally {
            synchronized (this) {
                permits++;
                notifyAll();
            }
        }
    }
}
```

或者调库

```java
public class AccessLimitControl {
    // 任意时刻仅允许最多3个线程获取许可:
    final Semaphore semaphore = new Semaphore(3);
    public String access() throws Exception {
        // 如果超过了许可数量,其他线程将在此等待:
        semaphore.acquire();
        try {
            // TODO:
            return UUID.randomUUID().toString();
        } finally {
            semaphore.release();
        }
    }
}
//或者加入超时等待
if (semaphore.tryAcquire(3, TimeUnit.SECONDS)) {
    // 指定等待时间3秒内获取到许可:
    try {
        // TODO:
    } finally {
        semaphore.release();
    }
}
```

##### 36.5.10.使用Concurrent集合

| interface | non-thread-safe         | thread-safe                              |
| --------- | ----------------------- | ---------------------------------------- |
| List      | ArrayList               | CopyOnWriteArrayList                     |
| Map       | HashMap                 | ConcurrentHashMap                        |
| Set       | HashSet / TreeSet       | CopyOnWriteArraySet                      |
| Queue     | ArrayDeque / LinkedList | ArrayBlockingQueue / LinkedBlockingQueue |
| Deque     | ArrayDeque / LinkedList | LinkedBlockingDeque                      |

##### 36.5.11 Atomic

```java
Atomic类是通过无锁（lock-free）的方式实现的线程安全（thread-safe）访问。它的主要原理是利用了CAS：Compare and Set。
public int incrementAndGet(AtomicInteger var) {
    int prev, next;
    do {
        prev = var.get();
        next = prev + 1;
    } while ( ! var.compareAndSet(prev, next)); //当前值是prev，那么就更新为next，返回true；反之啥也不做，返回false。继续自旋。
    return next;
}
```

##### 36.5.12 各种锁总结、性能分析

1.锁

| 概念                    | 是什么                          | 是否加锁  | 是否阻塞线程 | 冲突处理           | 典型实现                        | 适用场景               |
| ----------------------- | ------------------------------- | --------- | ------------ | ------------------ | ------------------------------- | ---------------------- |
| **CAS**                 | 一种原子操作（Compare-And-Set） | ❌         | ❌            | 自旋重试           | CPU 指令 + `Unsafe`/`VarHandle` | 原子变量、无锁数据结构 |
| **乐观锁**              | 一种并发思想：先操作，最后检查  | ❌（通常） | ❌            | 重试或失败         | CAS、版本号                     | 冲突少                 |
| **悲观锁**              | 一种并发思想：先加锁再操作      | ✅         | ✅            | 等待锁释放         | `synchronized`、`ReentrantLock` | 冲突多                 |
| **`synchronized`**      | Java 内置锁                     | ✅         | ✅            | JVM 管理锁         | Monitor（对象监视器）           | 简单同步               |
| **`ReentrantLock`**     | JDK 提供的可重入锁              | ✅         | ✅            | AQS 队列           | `Lock` 接口                     | 更复杂的同步           |
| **`AtomicInteger`**     | 原子变量                        | ❌         | ❌            | CAS 自旋           | CAS                             | 简单计数器             |
| **`ConcurrentHashMap`** | 高并发 Map                      | 部分      | 部分         | CAS + 锁           | CAS + `synchronized`            | 高并发 Map             |
| **`BlockingQueue`**     | 阻塞队列                        | ✅         | ✅            | `await()/signal()` | `ReentrantLock` + `Condition`   | 生产者-消费者          |

2.线程等待策略

| 等待方式                  | CPU占用 | 是否切换线程 | 典型API                                                   | 适用场景              |
| ------------------------- | ------- | ------------ | --------------------------------------------------------- | --------------------- |
| **自旋（Spin）**          | ⭐⭐⭐⭐⭐   | ❌            | CAS、AtomicInteger                                        | 等待极短（纳秒~微秒） |
| **阻塞（Block/Park）**    | ⭐       | ✅            | `synchronized`、`LockSupport.park()`、`Condition.await()` | 等待较长              |
| **忙等（Busy Waiting）**  | ⭐⭐⭐⭐⭐   | ❌            | `while(flag){}`                                           | 一般不推荐            |
| **让出CPU（Yield）**      | ⭐⭐      | 可能         | `Thread.yield()`                                          | 很少使用              |
| **睡眠（Sleep）**         | ⭐       | ✅            | `Thread.sleep()`                                          | 定时等待              |
| **等待通知（Wait/Park）** | ⭐       | ✅            | `wait()`、`park()`                                        | 等事件发生            |

#### 36.5.6 线程池

- FixedThreadPool：线程数固定的线程池；

- CachedThreadPool：线程数根据任务动态调整的线程池；

- SingleThreadExecutor：仅单线程执行的线程池。

- ScheduledThreadPool：线程池定时执行

  具体细节，略。

  之所以使用线程池，是因为创建和销毁操作系统线程的成本较高。通过复用线程，可以减少线程创建、销毁和调度带来的开销，从而提高系统性能和资源利用率。

#### 36.5.7 Future 得到异步执行结果

```java
class Task implements Callable<String> {
    public String call() throws Exception {
        return longTimeCalculation(); //某长时间执行的函数，返回String 
    }
}
ExecutorService executor = Executors.newFixedThreadPool(4); 
// 定义任务:
Callable<String> task = new Task();
// 提交任务并获得Future:
Future<String> future = executor.submit(task);//因为 ExecutorService 专门提供了 submit(Callable<T>) 这个重载方法。可以识别Callable
// 从Future获取异步执行返回的结果:
String result = future.get(); // 可能阻塞
```

#### 36.5.8 CompletableFuture 自动调用回掉对象的回掉方法

并行执行，执行回掉函数。

```java
// CompletableFuture
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws Exception {
        // 两个CompletableFuture`并行执行`异步查询:
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://money.163.com/code/");
        });

        // 用anyOf合并为一个新的CompletableFuture:`谁快用谁`
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);

        // 两个CompletableFuture`并行执行`异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });

        // 用anyOf合并为一个新的CompletableFuture:`谁快用谁`
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:`最快的`
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}

```

断点调试技巧：step into、step into.......resume program、step into、step into....

之所以需要`resume program`是因为产生了新的任务，需要线程池继续调度，而这个任务可能由原来的线程执行，也可能由另一个线程执行。IDEA编辑器，debugger看到的是“当前正在执行任务的线程”，而不是线程池里的全部线程。

#### 36.5.9使用ForkJoin，分治并行线程任务

```java
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // 创建2000个随机数组成的数组:
        long[] array = new long[2000];
        long expectedSum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
            expectedSum += array[i];
        }
        System.out.println("Expected sum: " + expectedSum);
        // fork/join:
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }
}

class SumTask extends RecursiveTask<Long> {
    static final int THRESHOLD = 500;
    long[] array;
    int start;
    int end;

    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += this.array[i];
                // 故意放慢计算速度:
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        invokeAll(subtask1, subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        Long result = subresult1 + subresult2;
        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }
}

```

**分治（Divide and Conquer）**：是一种算法思想，完全可以自己实现，和 `ForkJoinPool` 没有必然关系。

**ForkJoinPool**：是一个专门为**递归分治 + 并行计算**设计的线程池，它提供了任务调度、工作窃取（Work Stealing）、`join()` 优化、负载均衡等机制，让分治算法能够高效利用多核 CPU。

#### 36.5.10 使用ThreadLocal 线程内部传参

ThreadLocal线程内部传参共享。

```java
static ThreadLocal<User> threadLocalUser = new ThreadLocal<>();
void processUser(user) {
    try {
        threadLocalUser.set(user); 
        step1();
        step2();
        log();
    } finally {
        threadLocalUser.remove();
    }
}
void step1() {
    User u = threadLocalUser.get();
    log();
    printUser();
}

void step2() {
    User u = threadLocalUser.get();
    checkUser(u.id);
}

void log() {
    User u = threadLocalUser.get();
    println(u.name);
}
```

使用结束后清除

```java
try {
    threadLocalUser.set(user);
    ...
} finally {
    threadLocalUser.remove();
}
也可以用`AutoCloseable`接口配合try (resource) {...}结构，让编译器自动为我们关闭。例
```

这是因为当前线程执行完相关代码后，很可能会被重新放入线程池中，如果`ThreadLocal`没有被清除，该线程执行其他代码时，会把上一次的状态带进去。

#### 36.5.11 虚拟线程

> **CPU 一次只能执行一个线程的一个调用栈。**

如果想执行别的任务，有两种办法：

1. **当前调用栈结束**（普通同步代码、JS 普通函数）。
2. **当前调用栈保存起来，稍后恢复**（JS `await`、Java 虚拟线程、协程）。

Java 虚拟线程借鉴了协程/async-await 的调度思想，和 JS Event Loop 在"等待即让出执行权，完成后恢复执行"这一点上非常相似。

### 37.网络编程

#### 37.1 TCP编程

```java
//针对客户端和服务端。 
var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
...
writer.write(s);//写入缓冲区
writer.flush();//强制发送。
...
String resp = reader.readLine();//读取
System.out.println("<<< " + resp);    
```

 客户端，`InputStream`要`接受`，服务端`OutputStream`的`输出`。

 服务端，`InputStream`要`接受`，客户端`OutputStream`的`输出`

