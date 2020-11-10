学习笔记
### 多线程出现

1. cup决定计算机计算能力,cup进化特别快,cup也越来越细到达7纳米。

2. 分布式由来,单台机器性能不能满足需要,通过多台机器来堆积性能。

3. 多核:多个单台机器硬件集成到一台机器内(最大核心数目前是256核)，多核意味需要更好组织和管理。
### CUP模型
![d70f581d70ef12adcbe938e7cb3cc26e.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p40)
```

多 CPU 核心意味着同时操作系统有更多的并行计算资源可以使用。

操作系统以线程作为基本的调度单元。

单线程是最好处理不过的。

线程越多，管理复杂度越高。

```
- SMP架构，多个cup共享一块内存

- NUMA机构，将一部分cup单独和内存绑在一起，不同组cup交互通过router进行交互，易拓展 

- CUP和内存交互是通过总线进行交互,机器32位还是64位是总数决定。

- 线程管理,上线文切换,线程创建运行消耗的管理

### 线程创建过程
![b7df20c3f3f8b20d9d8678bd5d997453.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p41)
1. 操作系统基本运行单位是线程

2. cup的调度单位是线程

3. 进程内的资源是可以共享

4. 进程之间的资源是隔离的

5. linux的一切都是fd(文件具柄描述符)，任何软件或者硬件都是以fd形式呈现的,内核角度出发任何进程或者线程都是线程，存在父子进程关系的进程是可以复用端口的。
6. 线程创建过程:

```

(1)java语言层面,java创建线程方式:Thread#start()

(2)jvm层面创建线程javaThread

(3)调用os的api创建一个os层面的OSThread

(4)jvm虚拟机栈分配内存空间

(5)heap上的TLAB分配内存空间

(6)启动:java层面->Thread#run(),os层面ready(申请cup时间片)

(7)终止:os层面Terminate

(8)jvm层面需要与os交互和jvm内部进行内存分配管理

```

### Thread相关

1. 线程创建方式

```

(1)Thread t = new Thread()

(2)Runnable task = new Runnable(){

...

}

```

2. 直接使用Runnable#run()不会创建线程

3. 守护线程,指后台线程,为前瞻线程服务

4. 前瞻线程,指做业务计算的线程

5. 当前瞻线程都消耗，守护线程也就没有意义了,jvm会关闭守护线程 

### 基础接口

1. Runnable和Thread本质是一样的,Thread会实现Runnable

2. Thread#start()创建线程

3. Thread#run()本线程执行调用

### 线程状态
![0fde0c2a2e9a65d8b76f4748e29ead7b.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p42)
1. Runnable状态====&gt;等待cup调度执行
2. Running状态======&gt;cup在执行当前线程
3. Non-Runnable_wait========&gt;等待状态
4. Terminated========&gt;消耗状态
### Sleep与wait

1. sleep(arg1，arg2) ===> arg1表示毫米,arg2表示纳秒,参数2大于1则加1处理。

2. sleep不是精确,跟OS和时钟系统相关，机器与机器时间不是绝对准确的。

3. windows文件创建时间精确到秒。

### Thread类
![fbcfc87a39abeb8eec3d87b674a29003.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p43)
- Thread内部存在线程Id的,代表是第几个线程

### wait&notify
![8ffefdba31b90afd9fda87519c59e8a0.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p44)
- wait方法会释放cup,同时释放锁资源

### 线程状态改变操作

1. Thread#join()/Thread#join(long millis)让当前线程阻塞,等待其他线程执行完毕后执行,是线程聚合点，释放t线程锁。

2. Thread.sleep(long millis)，一定是当前线程调用此方法，当前线程进入 TIMED_WAITING 状态，但不释放对象锁，millis 后线程自动苏醒进入就绪状态。作用：给其它线程执行机会的最佳方式。

3. Thread.yield()，一定是当前线程调用此方法，当前线程放弃获取的 CPU 时间片，但不释放锁资源，由运行状态变为就绪状态，让 OS 再次选择线程。作用：让相同优先级的线程轮流执行，但并不保证一定会轮流执行。实际中无法保证yield() 达到让步目的，因为让步的线程还有可能被线程调度程序再次选中。Thread.yield() 不会导致阻塞。该方法与sleep() 类似，只是不能由用户指定暂停多长时间。

4. t.join()/t.join(long millis)，当前线程里调用其它线程 t 的 join 方法，当前线程进入WAITING/TIMED_WAITING 状态，当前线程不会释放已经持有的对象锁。线程t执行完毕或者 millis 时间到，当前线程进入就绪状态。

5. obj.wait()，当前线程调用对象的 wait() 方法，当前线程释放对象锁，进入等待队列。依靠 notify()/notifyAll() 唤醒或者 wait(long timeout) timeout 时间到自动唤醒。

6. obj.notify() 唤醒在此对象监视器上等待的单个线程，选择是任意性的。notifyAll() 唤醒在此对象监视器上等待的所有线程。

### Thread 的中断与异常处理
1. wait,sleep中可以调用线程Thread#interrupt()方法时会抛出InterruptedException,终止线程
2. 如果线程中没有调用sleep,wait调用Thread#interrupt()方法时不会抛出InterruptedException,只会重置线程状态Thread#isInterrupted()会false->true,ture->false

----------------------------------------------------

### 线程池

池化，主要是资源重复利用,避免频繁创建和销毁的带来的资源消耗

```

(1)资源创建:从线程池中获取线程

(2)资源消耗:使用后将线程归还给线程池

```

线程池相关类

```

(1)Excutor:执行者-顶层接口

(2)ExcutorService:接口API

(3)ThreadFactory:线程工厂

(4)Excutors:工具类,创建线程池

```
### Executor执行者

方法: Executor#execute(Runnable command) 执行任务方法

- 线程池从功能上看,就是一个执行器

- submit方法 -> 有返回值,用Future封装

- execute方法 -> 无返回值

- submit方法还可以将异常可以在主线程中catch到。

- execute方法执行任务是捕获不到异常的。
![6c3e32762d782f45dfbb5d5e928ed029.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p56)

### ExecutorService
![d190a83aec484df8224ea137bbadfd44.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p57)
- shutdown();停止接收新任务,原来的任务继续执行

- shutdownNow();停止接收新任务,原来的任务停止执行

- awaitTermination(Long timeout,TimeUnit unit);当前线程阻塞

- ThreadFactory

- ThreadFactory#newThread(Runnable r) 创建新线程

- ThreadPoolExecutor提交任务逻辑:

```

(1)判读corePoolSize创建

(2)加入workQueue

(3)判断maximumPoolSize 创建

(4)执行拒绝策略处理器

```

- CUP密集型，处理请求过多通过队列进行缓冲

- IO密集性,处理请求过程先通过队列缓冲，然后开启最大线程

### 线程池参数

##### 缓冲队列

- BlockingQueue 是双缓冲队列。BlockingQueue 内部使用两条队列，允许两个线程同时向队列一个存储，一个取出操作。在保证并发安全的同时，提高了队列的存取效率。

- ArrayBlockingQueue:规定大小的 BlockingQueue，其构造必须指定大小。其所含的对象是 FIFO 顺序排序的。

- LinkedBlockingQueue:大小不固定的 BlockingQueue，若其构造时指定大小，生成的 BlockingQueue 有大小限制，不指定大小，其大小有 Integer.MAX_VALUE 来决定。其所含的对象是 FIFO 顺序排序的。

- PriorityBlockingQueue:类似于 LinkedBlockingQueue，但是其所含对象的排序不是 FIFO，而是依据对象的自然顺序或者构造函数的 Comparator 决定。

- SynchronizedQueue:特殊的 BlockingQueue，对其的操作必须是放和取交替完成。

##### 拒绝策略

- ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。

- ThreadPoolExecutor.DiscardPolicy：丢弃任务，但是不抛出异常。

- ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提交被拒绝的任务

- ThreadPoolExecutor.CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务,通过主线程处理任务方式减缓向线程池放置任务方式，进行缓冲

##### ThreadFactory

使用方式

```

(1)实现ThreadFactory

(2)重写newThread(Runnable r)设置线程属性

```

### ThreadPoolExecutor
![d96b13b39538fc972601ca7e383feb90.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p58)

### 创建线程池方法

1. newSingleThreadExecutor

创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池保证所有任务的执行顺序按照任务的提交顺序执行。

2. newFixedThreadPool

创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。

3. newCachedThreadPool

创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添

加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。

4. newScheduledThreadPool

创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。

### 创建线程池经验

1. cup密集型应用,则线程池大小设置为N或N+1

2. IO密集型应用,则线程池大小设置为2N或者2N+2

### Callable接口

方法:Callable#call() 调用执行

对别：

```

(1)Runnable#run()没有返回值

(2)Callable#call()方法有返回值

```
### Future
![6bdfb61cf355b49f435ec2561bb824ce.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p59)
- 异步操作注意问题

(1)多线程协作

(2)异步结果获取

(3)相同资源冲突问题

### java.util.concurrency

分类:

```

(1)锁

(2)线程池

(3)原子类

(4)工具类

(5)集合类

```

### 锁

为什么会出现显示的锁

```
(1)灵活性:可以控制加锁或者解锁的条件

(2)锁定时间:可以控制锁定时间

(3)控制获取锁的方式
```

### lock 更自由的锁

1. 使用方式灵活,可以控制加锁或者解锁的方式

2. 性能开销小

3. 锁工具包:

- java.utils.concurrency.locks

##### 基础接口-lock
![4a7be5c592e955cd6d3b789e502f2b5b.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p60)
- condition 给锁增加信号机制,可以通过信号通知方式进行控制

- 重入锁,当前获取一把锁资源后,在没有释放锁资源的时候可以再次获取同一锁资源。

- 公平锁:获取锁的顺序按先老后到

##### 读写锁 - 接口与实现
![ecff4f95aeeba2b7ee020accc53221b2.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p61)
1. 读写锁使用场景:

(1)读取和写入操作同时进行时,控制数据一致性

2. 优化锁原理：

(1)减少锁的范围,锁的代码块大小

(2)锁分离,一把锁变成n把锁

##### 基础接口 - Condition
![840fffaae13279c571d8e72085507810.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p62)
- 类比:Object自带的monitor
##### LockSupport--锁当前线程
![91d00f3afdbdae9839cf3fc56d81ac83.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p63)
- Object blicker:表示锁定对象

### 用锁的最佳实践

- 永远只在更新对象的成员变量时加锁

- 永远只在访问可变的成员时加锁

```

(1)加锁是为了,保证读取数据是最新的,需要强一致性

(2)对数据一致性要求不高,可能不加锁

```

- 永远不要在调用其他对象方法时加锁

```

(1)对象将锁相关机制放在对象内部,不要进行暴露

```

- 锁的另一个作用是强刷内存。 根据hapens-before规则，你得到锁的时候「看见标志位改变」，之前临界区内的操作已经全部刷到内存了。

- 读写锁，能保证你写入之后，其他线程读取的数据都是最新的 这算是内部实现，看 threadDump，暂停的线程栈内部一般都是 park 指令的方法。

- 偏向锁:优先给获取过锁的线程

- 可见性的读屏障不垮线程

### 并发原子类

##### Atomic工具类:

1. 原子类工具包:java.util.concurrency.atomic

2. 作用:并发场景下,可以准确计数

3. AtomicInteger , AtomicLong , LongAdder, DoubleAdder

##### 无锁工具类 - Atomic工具类

1. 无锁技术的底层实现原理

- volatile 保证可见性

- Unsafe API - Compare-And-Swap

- CUP硬件指令支持:CAS指令

2. 比较替换 

##### 锁与无锁之争

- 并发低:有锁无锁差不多,本身对性能要求不高,访问量比较小

- 并发高:无锁要更好一些,不需要进行等待

- 并发特别高:无锁情况下,可能大部分资源在进行自旋抢锁

##### LongAdder改进 

- 无锁基础上，通过分段形式提高并发性

- 每个线程进行自己相加操作,然后合并

### Semaphore - 信号量
- 控制并发访问线程数
- 控制可以同时获取锁的线程数量,N=1时等价于独占锁
- 控制权重(设置访问数量数量)
### CountDownLatch
![d1a91a1f68a846ba68e235b4ffbfe2b1.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p70)
- 场景: 多线程任务状态聚合点,master线程等待worker都执行完后进行执行
- 使用注意: countDownlatch.countDown()放在finally中,并发出现异常后没有释放资源,导致程序卡死
### CyclicBarrier
![d81cba2f7eef94c7fb6e8fa204b8b468.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p71)
- 特点:等待所有任务到达某个时间点，所有线程一起执行
- 场景: 任务执行到一定阶段, 等待其他任务对齐
![05147a46b4b60a59120dc624fa76f616.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p73)
![d131b7ebfd066beee0ad0ad8c8b747ec.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p74)

### Future/FutureTask/CompletableFuture
![5e953101f3ec4f389585cdedf79ae56a.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p75)
- Future,FutureTask主要功能是获取单线程执行结果

![21ef7e896b690c73dd4e80b3ac85ba62.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p72)
- CompletableFuture主要功能是异步,组合,回调。
### AQS
- Sync extends AbstractQueuedSynchronizer
- 抽象队列式的同步器
- Lock 的底层实现原理两种资源共享方式: 独占 | 共享
- 子类负责实现公平 OR 非公平

--------------------------------------------------


### JDK基础数据类型与集合类型
1. 基础类型
```
 (1)原生类型
 (2)数组类型
 (3)对象引用类型
```
2. 线性数据结构
```
 (1)List:ArrayList,LinkedList,Vector,Stack
 (2)Set:LinkedSet,HashSet,TreeSet
 (3)Queue->Deque->LinkedList
```
3. 字典类数据结构
```
 (1)Map:HashMap,LinkedHashMap,TreeMap
 (2)Dictionary->HashTable->Properties
```
```
(1)Properties的key,value都是String类型
(2)yaml文件是存在数据类型,可以转化为Properties
(3)yaml文件转化为Properties,properties将非String类型忽略
```
### ArrayList
1. 基本特点:基于数组,便于按index访问,超过数组需要扩容,扩容成本较高
2. 用途:大部分情况下操作一组数据都可以用ArrayList
3. 使用数组模拟列表,默认大小10,扩容*1.5,newCapacity=oldCapacity+(oldCapacity>>1)
4. 注意:
```
(1)存放元素的数组在进行序列化时是不会被序列化的
(2)ArrayList重写了readObject,writeObject方法将存放数据写出去
(3)考虑到数组中元素可能不一定等于数组长度,可以减少序列化成本
```
5. 安全问题:
 - 写冲突:
 	- 两个写,相互操作冲突
 - 读写冲突:
 	- 读,特别是iterator的时候,数据个数变了,拿到了非预期数据或者报错
 		(1)增加元素,可能读取不到,因为原始数据会向后移动
 		(2)删除元素,读取可能出现异常
 		(3)删除数据要倒向删除,因为正向删除数据会前移,造成读取数据错误
 	- 产生ConcurrentModificationException
### LinkedList
1. 基本特点:使用链表实现,无需扩容
2. 用途:不知道容量,插入变变动多的情况
3. 原理:使用双向指针将所有节点连接起
4. 问题:
 - 读取过程中可能出现死循环
 - 读取过程中数据断掉
5. 安全问题:
 - 写冲突:
 	- 两个写,相互操作冲突
 - 读写冲突:
 	- 读,特别是iterator的时候,数据个数变了,拿到了非预期数据或者报错 
 - 产生ConcurrentModificationException

  ### List线程安全的简单办法
  1. 既然线程安全和读写冲突导致的,简单办法就是读写都加锁。
  2. 例如:
  - 1.ArrayList的方法都加上synchronized -> Vector 
  - 2.Collections.synchronizedList，强制将List的操作加上同步 
  - 3.Arrays.asList，不允许添加删除，但是可以set替换元素 - 4.Collections.unmodifiableList，不允许修改内容，包括添加删除和set
 3. 如果两把锁之间没有关系,那么不能保证读写同时操作的原子性

 ### CopyOnWriteArrayList
 ![f113a547b03aba7872f97dbb883dbc9b.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p64)
 核心改进原理：
 1. 写加锁，保证不会写混乱 
 2. 写在一个Copy副本上，而不是原始数据上 （GC young区用复制，old区用本区内的移动） 
 特点:
  - 读写分离 
  - 最终一致
  - 老的完成操作后,将老的换成新的(优雅停机或者滚动发布)
  - 写完成之前获取是老数据,写完成之后读取获取的是新数据
 4. 使用迭代器的时候， 直接拿当前的数组对象 做一个快照，此后的List 元素变动，就跟这次迭 代没关系了。
### HashMap
![c859d8f0fb4cabf49d5b66b451329579.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p65)
1. 基本特点:空间换时间,哈希冲突不大的情况下查找数据性能很高
2. 用途:存放指定key的对象,缓存对象
3. 原理:使用hash原理，存k-v数据，初始容量16，扩容x2，负载因子0.75 JDK8以后，在链表长度到8 & 数组长度到64时，使用红黑树。
4.安全问题： 
 - 写冲突 
 - 读写问题，可能会死循环(写入数据时,链表中元素互相指向自己)
 - keys()无序问题
 (不依赖HashMap顺序,自己定义排序,使用LinkedHashMap)
5.使用问题
(1)hash冲突严重,HashMap退化成单链表,效率很低(效率低于单链表)
(2)性能高的原因,数据分散均匀
(3)单链表变成红黑树,降低时间复杂度o(N)->o(logN)
数据库查找
(1)非索引情况下,遍历查找
(2)索引情况下
有序问题
(1)内部有序(TreeMap)
(2)插入顺序(LinkedHashMap)
### LinkedHashMap
- 基本特点：继承自HashMap，对Entry集合添加了一个双向链表 用途：保证有序，特别是Java8 - stream操作的toMap时使用 原理：同LinkedList，包括插入顺序和访问顺序 
- 安全问题： 同HashMap
### java7ConcurrentHashMap
![0f1d02369d500b3a5d04919a8a99dcae.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p66)
- 分段锁 默认16个Segment，降低锁粒度。 
- concurrentLevel = 16 
- 想想： Segment[] ~ 分库 HashEntry[] ~ 
- 原理:Segment是一个桶,Segment里面是一个HashMap,相当于进行两层Hash,理想情况下是16个并发同时进行,如果hash到同一个Segment时需要进行排队
![bbe16354da5b1e8bd3021caf9df8a916.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p67)
### java8ConcurrentHashMap
![3fed7d36b1e1dc8b91d56f221745d5ed.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p68)
1. 线程安全:
  - 先get判断是否null,如果null，就是new一个对象存放进去这个操作不是原子性,使用putIfAbsent方法
2. Cas方法替换分段锁
![a299ff926f7baef304b95dd433876fb9.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p69)
### ThreadLocal
![57144cd8093704f4b2cf231e18f753b1.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p76)
- 通过线程上下文进行参数传递,不改变方法签名隐式传参

- 线程本地变量

- 场景：每个线程一个副本,线程之间相互不影响

- 使用后,要及时清理,避免内存泄漏 
### 并行-paralle-Stream

- 集合进行并行操作,内部自动帮我们进行开启线程操作

- 将stream后面的操作包装成任务方式放入一个线程池中,线程数是cup内核的2倍

- 通过关键字将业务和多线程分离

- 数据规模,业务计算复杂度,可并行和可串行比例

### 并发问题

- 浏览器客户端,表单重复提交问题

（1）客户端控制

（2）服务器控制,通过每个表单生成唯一序列号方式去重方式

（3）下单后通知客户已经收到,然后进行后续处理,更新到页面

- 分布式下的锁和计数器

  (1)分布式下多台机器协作
### 加锁需要考虑的问题

- 粒度

- 性能

- 重入

- 公平

- 自旋锁:cup使用不高情况下使用

- 场景:脱离业务场景谈性能都是耍流氓

- 业务系统 - 数据库sql

首先确定,可以并行操作和只能串行操作,然后确定哪些步骤需要加锁,哪些步骤不用加锁，需要平衡一致性

### 线程协作

1. 线程共享

- static/实例变量(堆内存)

- Lock

- synchronized

2. 线程间协作:

- Thread#join()

- Object#wait/noify/noitfyAll

- Future/Callable

- CountDownLatch

- CyclicBarrier

3. 进程之间

- 操作系统级别信号量

- 共享内存

- 共享文件

- 管道 - 将流向下传递

- socket

- 数据库

- mq

- redis