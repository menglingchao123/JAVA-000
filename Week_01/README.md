学习笔记
### java语言
- java语言简介
```
    (1)java是一门面向对象,静态语言,编译执行,有虚拟机和运行时,有gc的跨平台高级语言。
    (2)GC(内存管理器):包含内存申请,内存运行时使用,内存的回收。
    (3)运行时:语言的运行的上下文环境-jvm虚拟机。
    (4)跨平台:编译后的class文件可以在任何有java虚拟机的环境下运行。
    (5)兼容性:jdk版本升级不会对之前的产生影响,可以向下兼容。
```
- 字节码,类加载器,虚拟机
```
    字节码文件->类加载器->虚拟机(class)->初始化->对象
```
### 字节码
1. 简介
```
    (1)java bytecode由单字节(byte)的指令组成,最多支持256操作码,实际上java只有200左右的操作,还有一些操作码时留给调试者使用的。
    (2)java bytecode实际是jvm虚拟机的指令码。
    (3)java虚拟机实际是基于栈的机器,实际运算通过栈来完成。
```
2. 字节码分类:
```
    (1)栈操作指令,包含与局部变量交互的指令。
    (2)程序流程控制指令。
    (3)对象操作指令,包括方法调用的指令。
    (4)算术运算与类型转换的指令。
```
3. 生成字节码:
```
    (1)javac xxx.java 编译
    (2)javap -c xxx.class 查看字节码
    (3)javap -c -verbose xxx.class 查看更多信息
```
4. 字节码运行时结构:
```
jvm是基于栈的计算器。
每个线程都有一个独属于自己的线程栈(JVM 栈),用于存储栈帧。
每次调用方法,JVM会自动创建一个栈帧。
栈帧是有操作数栈和局部变量表和class引用组成。
Class引用指向当前方法运行时常量池中对应的class。
```
5. 方法调用指令:
```
    (1)inokestatic:调用某个类中的静态方法,也是调用指令中最快一个。
    (2)inokespecial:构造函数调用,但也可以调用同一个类中的private方法,还有超类中的方法。
    (3)inokevirtual:如果是具体目标对象,inokevirtual用于调用公共,受保护,package级方法。
    (4)inokeinterface:通过接口引用来调用方法
    (5)inokedyamic
```
### 类加载器
1. 简介
```
    (1)完成class文件加载jvm虚拟机中,并放入内存中
```
2. 类的生命周期
```
    (1)加载:发现class文件
    (2)验证:验证格式,依赖
    (3)准备:静态字段,方法表
    (4)解析:符号解析引用
    (5)初始化:构造器,静态变量赋值,静态代码块
    (6)使用
    (7)卸载
```
3. 类加载时机
```
    (1)虚拟机启动时,初始化用户指定的主类,就是启动执行main的类。
    (2)当遇到用以新建目标类实例的new指令时,初始化new指令的目标类,就是new一个类时候一定要先初始化。
    (3)调用静态方法时,要先初始化静态方法所在类。
    (4)当遇到静态字段调用的指令时候,初始化该静态字段所在类。
    (5)子类初始化时,一定会触发父类的初始化。
    (6)如果接口定义了一个default方法,那么直接或者间接实现该接口的类的初始化,会触发接口初始化。
    (7)反射
    (8)methodhandle
```
4. 不初始化(可能被加载)
```
    (1)通过子类引用父类静态字段,只会触发父类的初始化,而不会触发子类的初始化。
    (2)定义对象数组,不会触发该类的初始化。
    (3)常量在编译期会引入到调用类的常量池中,实际上被没用引用定义常量的类,不会触发定义常量的类的初始化。
    (4)通过类名获取class对象,不会初始化。
    (5)class.forName如果initializer(false)不进行初始化。
    (6)通过classloader的loaderclass方法只进行加载不会初始化，
```
5. 三大类加载器
```
    类加载器：
    (1)BootstrapClassLoader 启动类加载器
    (2)ExtclassLoader  扩展类加载器
    (3)appclassLoader  应用类加载器
    特点:
    (1)双亲委派
    (2)负载依赖
    (3)缓存加载
    要点:
    (1)双亲委托解决重复加载问题
    (2)加载一个类通过时候要去加载这个类所依赖的类
    (3)类被加载后放入缓存中不会二次加载
    (4)用不同类加载器加载相同类,进行类型转换会出现问题
    (5)扩展包：jre/lib/ext
```
6. 显示classloader加载的jar包
7. 自定义classloader
8. 添加引用类几种方式
```
    (1)添加到jdk的lib/ext文件夹下,或者-Djava.ext.dirs
    (2)java -cp/classpath或者class文件放在当前路径下
    (3)自定义classloader
    (4)获取当前类加载,强制转换为URLclassloader，通过反射调用addUrl方法添加
```
### jvm内存模型
1. jvm内存结构
```
(1)每个线程只能访问自己的线程栈
(2)每个线程都不能访问其他线程的局部变量
(3)所有原生类型的局部变量都存在线程栈中,所以其他线程是看不见的。
(4)线程可以将原生局部变量副本传给其他线程,原生局部变量本身不能共享。
(5)堆中包含了java代码中创建的所有对象,不管是哪个线程创建的。其中涵盖了包装类(Integer Long Boolean之类)
(6)不管是创建对象将其赋值给局部变量,还是赋值给其他对象的成员变量,创建的对象都会放入堆内存中。
(7)如果两个线程同时访问一个对象的成员变量,线程实际操作的变量的副本,他们之间是相互独立。
```
2. 堆内存和栈
```
(1)堆:存储线程创建对象(对象是具有生命周期,而且对象大小是不固定的)
(2)栈:存储原生类型局部变量和对象引用(栈中变量使用完就会销毁,而且大小是固定的)
```
3. 原生类型和引用类型
```
(1)原生类型:变量内容保存在线程栈中。
(2)引用类型:变量内容保存在堆中,线程栈的槽位中保存的是对象引用地址。
```
4. 引用类型存储
```
(1)对象本身和对象成员变量(无论是原生还是引用类型)都会保存在堆内存中。
(2)静态变量和类定义都会放在堆中。
```
5. jvm内存整体结构
```
整体结构包含,堆栈非堆,jvm本身:
(1)每启动一个线程,JVM会在栈空间分配一个与线程对应的线程栈,大小控制参数比如:Xss1m
(2)线程栈又叫java方法栈,如果使用了JNI方法则会分配一个单独的本地方法栈（Native stack）
(3)线程执行过程中,一般会有多个方法组成调用栈(Stack Trace),如果A调用B,B调用C,每执行一个方法则会创建一个与之对应的栈帧。
```
6. 栈帧
```
栈帧是一个逻辑概念,具体大小基本在编写完成方法后就确定。
具体包含返回值,局部变量表,操作数栈,class对象指针(标识这个栈帧对应哪个class类,指向非堆的class对象)
```
7. 堆:
```
(1)堆内存是jvm的线程共享内存空间,Heap内存分为年轻代(Young generation)和老年代(old generation)。
(2)年轻代分为三个内存池新生代(eden space),和存活区(survivor space),在大部分GC算法中有两个存活区(S0,S1),S0和S1任何时刻都有一个是空的，一般空间比较小。
(3)非堆本质上是堆,但是不归GC管理,分为三个内存池
(4)Meta space,ccs区存放class信息,code cache存放jit编译后本地机器代码。
young区:新创对象会在年轻代中和生命周期短的
old区:对象生命周期长的
eden区:新创对象
survivor区:经过gc为销毁的对象
S0和S1内存大小一致,S0满了可以将S0存活对象放入S1中,解决内存空间不连续问题
对象特别大会直接进入老年代,年轻经过几次gc还存在对象会直接进入老年代
```
### jvm运行参数
1. 概念
```
启动java命令后面参数
```
2. 参数说明
```
(1)程序参数:是String[] args
(2)命令行参数:
    a.标准参数-开头,比如-server -client
    b.-D设置系统属性
    c.-X开头非标准参数,比如-Xmx8g,-Xms8g,-Xss4g
    d.-XX:开头非稳定参数,比如-XX:key=value,-XX:+UseG1GC(+表示开-表示关)，-XX:MaxMetaspceSize元数据空间大小配置,-XX:MaxDirectMemorySize=40堆外内存配置
xmx最大堆内存,xms最小堆内存,xss线程栈大小
```
3. 启动参数分类
```
(1)系统属性参数
(2)运行模式参数
(3)堆内存设置参数
(4)GC设置参数
(5)分析诊断参数
(6)javaAgen参数
```
4. 说明:
```
堆内存参数配置:
Xmx4g jvm运行需要5.2-5.3g运行内存
用到内存地方:栈 堆 堆外 非堆 jvm本身 操作系统
Xmx不能超过内存70%
```
5. 问题
```
类的定义和静态变量为什么是放在堆上?
对象大小多大会直接进入老年代
对象结构,常量池在constanpool?
```
### 参数说明
1. jvm启动参数-系统属性
```
配置方式:
(1)通过配置系统环境变量
(2)启动命令参数方式,比如-Dfile.encoding=utf8
(3)System.setProperty("1",100);
获取属性方式:
(1)System.getProperty("1");
```
2. jvm启动参数-运行模式
```
-server:
(1)设置jvm使用server模式
(2)优点:性能和内存管理方面效率很高
(3)缺点:启动速度慢
(4)使用场景:生成环境
-client:
(1)设置jvm使用client模式
(2)优点:启动速度快
(3)缺点:性能和内存管理方面效率低
(4)使用场景:客户端,开发,调试
-Xint:
(1)设置JVM解释执行模式,强制JVM解释执行字节码
(2)缺点:运行速度慢,通常低10倍或更多
-Xcomp:
(1)与-Xint模式正好相反,JVM会把字节码一次性编译到本地执行
-Xmixed:
(1)混合执行模式
(2)解释执行和编译模式混合使用,有JVM决定,默认模式,也是推荐模式
```
3. JVM启动参数-堆内存  
```
问题:
(1)堆外内存概念
(2)meta space与堆内存大小配置有关系吗
(3)堆外配置跟什么有关系
```


-------------------------------------------------------------------------



### GC背景和一般原理
#### 引用计数
```
仓库与引用计数:计数是0
简单粗暴,一般有效

实际情况复杂一点,仓库与仓库之间也有关系。
比如:形成环导致大家的计数永远不为0。
这些库无法再调用:内存泄漏->内存溢出(jvm没有内存可用)
```
#### 引用跟踪
1. 标记清除(Mark and Sweeping)
```
(1)Marking(标记):标记所有可达对象,并在本地(native)分门别类的记录下来。
(2)Sweeping(清除):这一步保证了,所有不可达对象所占用的内存,在之后进行内存分配时可重用。
(3)并行GC,和CMS的基本原理
(4)优势:解决循环依赖问题,只扫描部分对象
(5)除了标记清除,还要进行压缩整理出连续内存空间。
(6)对象之间关系实际运行中比较复杂,变化比较大,在标记和清除时如何处理它们之间关系,引出STW概念
```
2. STW
```
作用:进行标记和清除过程中,然后程序静止下来,等待动作都完成后,让程序在进行运行。
```
3. 分代假设
```
(1)大部分新创建对象很快无用，存活时间较长对象,存活时间可能更长。
(2)不同类型对象(存活时间长短),不同区域(存放不同内存空间),不同处理策略(使用不同GC策略)
```
4. GC时对象在内存池中转移
```
如下参数控制提升阈值:
-XX: MaxTenuringThreshold=15(默认值)
YoungGC经过15次还存活对象,转移到老年代。
Young Generation为什么采用复制方式？
```
```
老年代默认都是存活对象,采用移动方式:
(1)标记所有通过GCRoot可达对象
(2)删除所有不可达对象
(3)整理老年空间中内容,将所有存活对象复制,从老年代开始地方依次存放。
```
5. 持久代和元数据空间
```
参数配置:
(1)-XX:MaxPermSize=256m
(2)-XX:MaxMetaspaceSize=256m
```
6. GCRoot选取
```
(1)当前正在执行的方法的局部变量和参数
(2)活动线程
(3)类中的引用的静态变量
(4)JNI引用

在此阶段,堆内存大小,对象的总数没有直接关系,而是由存活对象数量决定的,所以增加堆内存大小不会影响占用时间。

可能存在跨代的情况:
(1)年轻代引用老年代,老年代引用年轻代
(2)处理通过RSET集合存储跨达的对象,年轻代GC处理年轻代和RSET指向年轻代的对象,老年代也是如此。
(3)Tlab是新生代里面给线程留出缓冲区,需要少量内存
```
#### 垃圾回收算法
1. 标记清除(Mark-Sweep)
```
(1)缺点:产生内存碎片
```
2. 标记整理(Mark-Sweep-compact)
```
(1)整理需要移动对象,速度慢
```
3. 标记复制算法(Mark-copy)
```
(1)需要预留内存空间,造成内存空间浪费
```
#### 串行GC(serial GC)/并行GC(parallel GC)
1. GC触发时机
```
(1)内存不足
(2)设定阈值
```
2.SerialGC/ParNewGC串行GC
```
(1)配置串行GC:
-XX:+useSerialGC
(2)垃圾回收算法:
串行GC对应新生代垃圾回收算法是Mark-Copy,对老年代的垃圾回收算法是Mark-Sweep-Compact
(3)线程:
单线程处理,会进行SWT,停止所有应用线程
(4)缺点:
不能充分利用多核cup,不能cup是几核,都是单线程处理
(5)特点：cup利用率高,简单粗暴。
(6)适用场景:堆内存比较小,客户端应用程序。
(1)配置parNewGC:
-XX:+useParNewGC
```
#### 并行GC(Parallel GC)
1. 配置：
```
-XX:+useParallelGC
-XX:+useParallelOldGC
-XX:+ParallelGCThreads=N 设置线程数
```
3. SWT
```
年轻代和老年代垃圾回收时都会触发SWT事件
```
4. 使用GC算法
```
年轻代：Mark-Copy
老年代：Mark-Sweep-Compact
```
5. 核心配置:
-XX:+ParallelGCThreads=N 设置线程数,默认是Cup内核数,一般不用修改
6. 特点:
```
适用于多核服务器,主要目标增加吞吐量,因为对系统资源有效使用,能达到更高吞吐量：
(1)在GC期间,所有Cup的内核都在进行垃圾回收处理,总暂停时间短
(2)GC周期间隔,没有线程在处理GC,不会消耗任何资源。
```
5. 注意：
```
(1)SerialGC和ParNewGC不能和parallelGC或者parallelGC混合使用
(2)JVM参数默认配置:
a.Xmx默认机器内1/4机器内存。
b.初始化的NewSize+OldSize=1/64的机器内存
```
#### CMS(Mostly current Mark and Sweep Garbage Collector) GC
1. 目标
```
(1)减少应用停顿时间,所以老年代采用都是标记清除垃圾回收算法,去掉了整理步骤
(2)维护空闲内存空间的空闲列表
```
2. 优点
```
暂停时间比较短
```
3. 缺点
```
(1)处理步骤复杂
(2)处理效率不高
(3)堆内存比较大情况,由于内存碎片问题出现不可预测的暂停时间
```
4. CMSGC阶段    
```
(1)inital mark(初始化标记)
a.找到存活跟对象和跟对象引用的第一个对象
b.处理跨代问题,也会标记RSET中存活对象
(2)Concurrent Mark(并发标记)
a.标记第一个引用对象引用其他对象
(3)Concurrent PreClean(并发与清除)
并发运行过程中一些对象引用关系发生变化，JVM通过card方式将发送变化对象进行标记,这就是卡片标记(Card Mark)
(4)Final Remark(最终标记)
对三阶段发生变化对象引用关系,进行最终确认
(5)Concurrent Sweep(并发清除)
清除空闲内存
(6)Concurrent Reset(并发重置)
(7)默认的堆内存:
MaxNewSize=64*4*13/10(64位*并行GC线程数*13/10)
```
#### G1垃圾回收器
1. 发展趋势
```
(1)jdk9以后,G1GC是默认的垃圾回收器
(2)CMSGC在jdk9以后被标记为过时的垃圾回收器
```
2. G1的定义
```
(1)G1全称是Garbage-First,意为垃圾优先,哪一块垃圾多就优先处理它。
(2)设计目标:将SWT的停顿时间变成预期且可配置。
(3)事实上G1 Gc是一款软实时的垃圾回收器,可以为其设置某项特定指标。为了达成可预期停顿时间的指标,G1 GC有一些独特的实现。
(4)内存划分：
G1gc不在分成年代和老年代,而是划分为多个(2048)可以存放对象的小块区域,每个小块可以定义为eden区,一会被指为surviror区或者Old区,在逻辑上eden区和surviror和起来是年轻代,old合并起来是老年代。
(5)配置参数:
-XX:+UseG1GC -XX:MaxGCPauseMillis=50(默认200)
(6)垃圾回收处理方式:
a.每次只处理一部分小堆块内存,采用增量方式进行处理。
b.每次暂停都会回收年轻代的内存,但只会回收部分老年代
c.在并发阶段会估算每个小堆块存活对象数,构建回收原则垃圾最多小块会优先回收。
```
3. 垃圾回收时机
```
(1)默认触发时机,达到堆内存的45%,并发标记
(2)运行一段时间后,会根据统计数据在进行调整垃圾回收的频率和触发条件,从而到达设置预期的期望值。
```
4. 处理步骤
```
(1)年轻代模式转移
    G1GC会通过前面一段运行情况不断调整自己的回收策略和行为,以此来稳定自己的暂停时间。应用程序刚启动时候,G1还没有采集到足够的信息就处于初始的fully-young模式。年轻代满后应用程序会被暂停,将年轻代内存中存活对象拷贝到存活区,如果没有存活区则选择任意一块空闲区域作为存活区。
(2)并发标记
    a.G1很多概念是建立的CMS的基础之上的。
    b.并发标记在开始阶段通过起始快照方式,标记出所有存活的对象
    c.通过存活对象可以构建出每个小堆块的存活状态,以便能更高效选择
    d.这些信息用来执行老年代的垃圾回收
(3)有两种情况可以完全可以并发执行:
    a.在标记阶段确定某个小堆块没有存活对象,只包含垃圾
    b.在暂停转移期间,同时包含垃圾和存活对象的老年代小堆块。
(4)并发标记的触发时机
    a.当堆内存使用比例达到一定值时会触发,默认是45%,可以通过jvm参数 InitiatingHeapOccupancyPercent 来设置。
    b.并发标记分为几个阶段,部分阶段会触发SWT事件
```
5. 并发标记的具体步骤
```
(1)初始化标记阶段
    标记所有GC根对象直接引用的对象
(2)Root Regions Scan GC根区域扫描
    标记出所有从根区域可达的存活对象。根区域包含非空堆区域,标记过程中不得不收集的区域。
(3)Concurrent Mark 并发标记
(4)Remark再次标记
触发SWT事件,并标记所有初始化标记阶段为标记的存活对象。
(5)cleanup(清理)
统计小堆块中所有存活对象,对小堆块进行排序,用来提升GC的效率,不包含存活对象区域,会在这个阶段被回收,有一部分任务可以并发执行:回收不存在存活对象的小堆块,统计对象存活率,在此阶段会进行短暂的SWT。
```
6. 暂停转移(混合模式)
```
(1)定义：
并发标记完成之后,G1会进行一次混合收集,不只是年轻代收集,还包含部分老年代的区域也会加入到收集集中。
(2)触发条件:
很多规则和历史数据会影响到混合模式的启动时机,比如老年代腾出很多小堆块就不会进行混合模式。
(3)并发标记和混合模式中间可能会出现多次fully-young
(4)添加到老年代的小堆块的大小和顺序也是基于很多规则来判断的,包含软实时指标,存活率,并发标记期间收集GC效率等数据,外加一部分jvm配置选项。
(5)收集过程基本和fully-youngGC一致。
```
7. G1使用注意事项
```
G1在某些特别情况可能会出现fullGC,G1会退化为serialGC,用单线程形式进行垃圾回收,暂停时间可能达到秒级。
(1)并发模式失败
启动标记周期,老年代就被填满了,G1会放弃本次标记周期
解决方法:
a.增加堆内存大小
b.调整标记周期,增加处理GC线程数
(2)晋升失败
没有足够空间供存活对象晋升使用,会出现fullGC
解决方案:
a.增加预留空间:-XX:G1ReservePercent
b.也可以通过增加 –XX：ConcGCThreads 选项的值来增加并行标记线程的数目
c.通过减少 –XX：InitiatingHeapOccupancyPercent 提前启动标记周期。
(3)巨型对象分配失败
巨型对象分配内存空间失败,就会触发fullgc
解决方法:
(1)增加内存大小或者增加-XX:G1HeapRegionSize
```
8. 应用线程和GC线程区别  
```
GC是全力进行垃圾回收,业务线程比如进行IO时候会有等待时间，GC线程过多可能会出现CUP利用率过高。
```
#### 各种GC对比

收集器 | 串行并行or并发 | 新生代老年代 | 算法 | 目标 | 适用场景 |
---|---|---|---|---|---|
serial | 串行 | 新生代 | 复制算法 | 响应速度优先 | 单核client模式 |
serial Old | 串行 | 老年代 | 标记整理 | 响应速度优先 | 单核client模式 |
parNew | 并行 | 新生代 | 复制算法 | 响应速度优先 | 配置CMS使用 |
parallel Scavenge | 并行 | 新生代 | 复制算法 | 吞吐量优先 | 在后台运算不需要太多交换的任务 |
parallel Old | 并发 | 老年代 | 标记整理 | 吞吐量优先 | 在后台运算不需要太多交换的任务 |
cms | 并发 | 老年代 | 标记清除 | 响应速度优先 | 互联网或者b/s的java应用|
G1 | 并发 | both | 标记整理+复制算法 | 响应速度优先 | 面向服务端应用,将来代替cms |

#### 常用GC组合
1. serial + serial Old 单线程低延时CUP
2. parallel Scavenge+parallel Old 多线程高吞吐量
3. parNew + cms 低延时
#### GC如何选择
1. 如何考虑吞吐量优先,Cup最大程度处理业务,选择并行Parallel GC
2. 考虑低延时,每次GC尽量用短暂时间,选择CMS GC
3. 如何系统堆内存比较大,希望整体来看GC平均时间整体可控使用G1。
```
内存大小考虑:
(1)一般4g以上是比较大,建议使用G1
(2)一般超过8G,比如16G-64G选择G1
(3)默认Xmx=物理内存/4
(4)newSize+oldSize=物理内存的/64*1024m
```
4. jdk8默认GC是parallel GC,jdk9以后默认GC是G1
### 问题记录
1. 新生代和老年代垃圾回收采用的不同算法的好处?
2. 为什么使用ParNew配置CMS?

-------------------------------------------------------------------------



### JVM命令行-jps和jinfo
1. 查看java进程 - jps
2. 查看java进程具体信息 - jinfo pid
3. 查看java进程具体信息 - jps -lmv
### JVM命令行-jstat命令
1. 查看gc和内存使用情况 - jstat -gc pid 1000 1000
```
(1)举例说明:S1U表示survivor1区使用情况,S1C表示Survivor1区容量
(2)GC使用情况:YGC执行次数,YGCT执行耗时
(3)安字节来表示
```
2. 查看内存使用率 -jstat -gcutil pid 1000 1000
```
查看内存使用率 = 使用量/容量
```
### JVM命令行-jmap命令
1. jmap -histo pid 查看java进程类的实例使用情况
2. jmap -heap pid 查看java进程堆内存使用情况

### jstack命令
1. jstack -l pid 查看线程和线程锁的相关情况
2. kill -3 pid 同上
### jcmd混合命令
### jrunscript/jjs
### jconsole
1. 远程连接打开jmx,在启动参数中配置jmx相关参数
### jvisualVM
### visualGC
### jcmd