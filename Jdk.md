[TOC]

### 1.JAVA JDK默认编码 UTF-8

```JAVA
1.编译的时候
  javac -encoding utf-8 xxx.java
2.全局变量
  变量名:JAVA_TOOL_OPTIONS
  变量值:-Dfile.encoding=UTF-8
      
tips:给jdk升级到最新版22，就默认utf-8了
```

### 2.jar包使用

```java
|--lib---
|     |--mail.jar
|     |--activation.jar
|--mail.java

 1.直接用命令行
        javac -cp ".;lib/mail.jar;lib/activation.jar" mail.java
        java -cp ".;lib/mail.jar;lib/activation.jar" mail
   虽然可以使用，但是itellij写代码没提示
 2.itellij编辑器，添加libray
         直接用itellij的编译，可以正常运行。
         用命令行编译，
               javac -cp ".;lib/*" mail.java
               java -cp ".;lib/*" mail
```

### 3.java编译方式

```java
假设有个 a.java
第一种    
javac  a.java  //将源代码，编译成字节码
java   a       //运行编译好的字节码
第二种
java   a.java   //JDK11后提供的快捷方式，编译后运行
```

### 4.Intellij Idea 多线程debug

```js
打断点 Suspend:Thread
```

### 5.包命令

```java
code/
│
├── work/
│   └── bin/
│       └── src/
│           └── come/
│               └── Main.java                 
|               └── Person.java
把Main.java和Person.java 编码后到 work\bin
    
编译字节码 javac -d work\bin work\src\com\Main.java work\src\com\Person.java
运行字节码 java -cp work\bin work.src.com.Main    
```

### 6.新项目 Intellij Idea无法识别

```java
Project Structure-->SDK、Compiler output
Project Structure-->modules-->添加module以及mark as Sources等
```

### 7.jar打包流程

```java
project-root/
├── bin/                # 编译后的字节码 (.class 文件) 存放目录
├── lib/                # 外部库（JAR 文件）存放目录（这里暂时没有 JAR 文件）
├── src/                # Java 源代码文件 (.java 文件) 存放目录
│   └── com/
│       └── example/
│           ├── MyLibraryClass.java
│           └── Test.java
|   └──Fucks/
        └──"Fuck.java" //假设，此时我已经把MyLibraryClass打包jar了，并且把com目录下都删除了，并且我还要用这个jar。
└── README.md           # 可选的项目说明文件
    
1.源代码
    就是Test调用MyLibraryClass中的某个方法
2.编译源代码
   javac -d bin src/com/example/MyLibraryClass.java src/com/example/Test.java
4.运行
   java -cp "bin;" com.example.Test
5.把源代码打包成jar
   jar cf lib/my-library.jar -C bin com/example/MyLibraryClass.class
   c：创建新的 JAR 文件。
   f：指定 JAR 文件的文件名。
   lib/my-library.jar：JAR 文件的输出路径和文件名。
   -C bin：切换到 bin 目录（包含编译后的 .class 文件）。
   com/example/MyLibraryClass.class：要打包的 .class 文件路径。
6.验证jar文件
   jar tf lib/my-library.jar
7.使用jar文件,
    javac -cp "lib/my-library.jar" src/Fucks/Fuck.java
    java -cp "src;lib/my-library.jar" Fucks.Fuck 
```

### 8.模块编写

```java
//https://liaoxuefeng.com/books/java/oop/basic/module/index.html
oop-module
├── bin
├── build.sh
└── src
    ├── com
    │   └── itranswarp
    │       └── sample
    │           ├── Greeting.java
    │           └── Main.java
    └── module-info.java //模块描述文件
//模块描述文件 module-info.java   
module hello.world {
	requires java.base; // 可不写，任何模块都会自动引入java.base
	requires java.xml;//没办法Main.java用到了
}
1.编译源代码
javac -d bin src/module-info.java src/com/itranswarp/sample/*.java 
*********************************/
2.bin目录下所有class打包成jar
jar --create --file hello.jar --main-class com.itranswarp.sample.Main -C bin .

3.把jar包转成模块
jmod create --class-path hello.jar hello.jmod

4.运行模块
java --module-path hello.jar --module hello.world
    
5.打包jre
jlink --module-path hello.jmod --add-modules java.base,java.xml,hello.world --output jre/
    
6.运行jre
jre/bin/java --module hello.world    
```

