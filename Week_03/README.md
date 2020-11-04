学习笔记

### 高性能
- 高并发用户 (Concurrent Users)，同时存在多个用户访问应用系统，从系统外部角度出发。

- 高吞吐量 (Throughout)，每秒可以接收请求数(QPS)，每秒处理的交易数(TPS)，是从系统内部角度出发。

- 低延迟 (Latency)，通常指请求进入系统到出系统的时间差,主要是从系统角度出发,响应时间是用户发出请求到得到响应的时间差,从用户角度出发的，延迟指标需要看p90或者p99。

### 如何做到高性能
1. 高并发意味着系统有海量连接请求处理能力
2. 高吞吐量意味系统的业务和IO方面处理能力
3. 低延迟意味系统响应速度快 

### 高性能的另一面
1. 系统复杂度*10以上
2. 建设与维护成本高
3. 故障或者BUG导致的破坏性*10以上 

### 应对策略

##### 稳定性建设(混沌工程)
1. 容量 (并发量和吞吐量)
2. 爆炸半径  (系统变更造成的影响范围)  
3. 工程方面积累与改进（外部人为系统问题总结经验）

### Netty如何实现高性能
1. Netty定位是抽象统一网络应用开发框架
```
a.异步，可以实现高性能
b.事件驱动，通过事件驱动可以实现模块间解耦
c.基于NIO
d.灵活 统一 高效  
```
2. 编程模型的统一,API层的统一,针对不同网络协议或者IO方式和服务器或者客户端编程形式都是统一,只需要调整个别参数配置即可。
3. 适用场景:
```
a.服务器
b.客户端
c.tcp/udp
```
### 事件处理机制
![8e96e0743ce1213e0d53a00c01b8f731.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p32)
### 事件处理机制到Reactor模型
![1bec2f20f3df4200aaa528e1180505c6.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p37)
1. Reactor模式首先是事件驱动的,有一个或者多个并发输入源,有一个serviceHandler和多个EventHandlers。

2. 这个Service Handler会同步的将输入的请求多路复用的分发相应的Event Handler。

3. 职责:serviceHandler接收请求和分发请求,eventHandler处理请求。

### Netty的Reactor模型
![0a7711fb6d3b203434e7026b3479cfe5.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p31)
1. Boss EventLoopGroup 接收请求,分发请求
2. Worker EventLoopGroup 实际处理请求 
### Netty运行原理
![7d3cb0c85214c69e46595e8e51b7468b.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p23)
1. 设计目的可以提高灵活性，不同业务或者不同网络协议使用不同处理器进行处理。
2. EventLoop是不断轮询单线程处理器。
3. 单线程相比于多线程优势:不存在线程之间的上线文切换。
### 关键对象
![368b86aa4896efd814e326357d5fc47b.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p34)
- Bootstrap:启动线程,开启Socket
- EventLoopGroup
- EventLoop
- SocketChannel：连接
- Channelinitializer：初始化
- channelpipeline：处理器链
- channelHandler：处理器
![703acd2f2a8ddbf2d0f8b5d507b474f8.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p33)
- ServerBootstrap是服务器引导器
- Bootstrap是客户端引导器
### Channelpipeline
![4bc3c8a05b981da2d6a0bac5370f57bf.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p38)
- ChannelinboundHandler:入站处理器
- ChannelOutboundHandler:出站处理器
### Event & Handler
1. 入站事件:
```
a.通道激活和停用:激活通道
b.读操作事件:读取通道数据
c.异常事件:异常处理
d.用户事件:用户业务处理
```
2. 出站事件:
```
a.打开连接
b.关闭连接
c.写入数据
d.刷新数据
```
3. 事件处理程序接口:
```
a.ChannelHandler
b.ChannelOutboundHandler
c.ChannelInboundHandler
```
4. 适配器(空实现需要继承使用)
```
a. ChannelInboundHandlerAdapter
b. ChannelOutboundHandlerAdapter
```
5. Netty应用组成:
```
a.网络事件
b.应用程序逻辑事件
c.事件处理程序
```
### Netty网络程序优化
##### 粘包和拆包
![8418785d595e79c1f14f1b89426d03b7.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p29)
1. 问题:服务器响应数据没有指定报文长度,客户端是不知道接收数据什么时候结束，会一直等待,如果服务器强制关闭连接,客户端会出现异常。

2. 解决可以通过请求头：Content-length指定长度

3. 拆包:接收数据时,无法判断哪些数据包是一个完整报文体

4. 粘包:发送数据时,发送数据是不同数据包放在一起

6. 原因：服务器和客户端没有定义好数据传输规则

7. 解决方案:规范好发送数据包长度。

### Netty解决拆包和粘包问题方案

1. FixedLengthFrameDecoder:定义协议解码器,我们可以指定固定的字节数算一个完整报文。

2. LineBasedFrameDecoder:行分隔符解码器,遇到\n或者\r\n,则认为是一个完整的报文。\n换行(mac linux)\r\n回车(windows),\r是13个字节,\n是10个字节 

3. DelimiterBasedFrameDecoder:分隔符解码器,分隔符可以自定义。

4. LengthFieldBasedFrameDecoder:长度解码器,将豹纹化为报文头/报文体,头部指定长度。

5. Http的chunk是处理大文件的响应报文体
```
a.请求头->chunk
b.请求体-> 每次发一个固定长度
c.结束->发送空的报文
```
6. JsonObjectDecoder:json格式解码器.当检测到匹配数量的"{""}"或者[]时,则认为是一个完整的json对象或者数组。

### Http断点续传问题 

- 下载过程中出现问题停止,下次可以回到上传下载的位置继续下载。

### Nagle与TCP_NODELAY

- MTU: Maxitum Transmisson Unit 最大传输 单位 1500byte 表示我们发送一帧数据大小

- 传输结构:报文头（ip头 tcp头）报文体 

- MSS: Maxitum Segment Size 最大分段大小 1460byte表示我们真实数据大小

- 发送数据大小:
```
a. 过小:发送的次数过程可能会造成网络用度
b.超过1500byte,tcp底层会进行分包处理
```
- 网络拥堵与Nagle算法优化TCP_NODELAY
```
a.Nagle算法触发条件：缓冲区满了，达到超时(200ms)
b.发送过程:用户进程->内核缓冲区(nagle算法)->网卡发送
c.发送频率高，或者对延迟不敏感可以开启nagle算法
```
### 连接优化
##### TCP建立连接过程:
![48febc3c57d7e731f83e1f59ea7d6992.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p35)
1. 客户端接收服务器ack会把自己置为一个连接状态

2. 服务接收到客户端ack后把自己置为一个连接状态

##### TCP释放连接:
![add95dbe5f37688307d586dab04a8f35.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p30)
1. 客户端time_wait 

2. 出现端口占用问题是因为释放连接后会存在一个等待过程周期后客户端才会真正释放连接
```
a.降低客户端time_wait时间
b.通过操作系统复用参数进行复用
```
### Netty优化
1. 不要阻塞EventLoop

2. 系统参数优化 linux->fd文件描述大小默认1024k ulimit -a  time_fin_timeout 断开等待时间

3. 缓冲区优化
```
SO_RCVBUF 接收缓冲区大小

a.过小可能会阻塞或者数据丢弃

b.太大可能会很大才会满

SO_SNDBUF 发送缓冲区大小

SO_BACKLOG 保持连接状态，配置正在连接个数

REUSEXXX（Addr和Port） 关闭一半的连接,重用连接端口（未消耗端口TIME_WAIT）
```
4. 心跳周期优化

心跳机制与断线重连,判断连接是否断开

5. 内存与byteBuffer优化

Directbuffer与HeapBuffer

6. 其他优化
```
-ioRatio io与非io比例 默认50:50
-Watermark 缓冲区水位
-TrafficShaping 水位操作流控（限流操作）
```
### API网关
![822c9088706b79604815b6d60ec6d2ee.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p27)
![0d88857ac968c47ca93b2307f8c93148.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p39)
网关作用:

1. 业务系统与客户 端进行隔离,流量控制和安全认证

![eb17a6bb95b4636e6040092913b3fdcc.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p21)
### 网关分类
- 流量网关
- 业务网关
### 经典API网关
![3fd064b1928654e15bd9d2147eb2cd75.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p22)
```
Zuul是基于BIONetflix 开源的 API 网关系统，它的主要设计目标是动态路由、监控、弹性
和安全。

Zuul 的内部原理可以简单看做是很多不同
功能 filter 的集合，最主要的就是 pre、
routing、post 这三种过滤器，分别作用
于调用业务服务 API 之前的请求处理、直
接响应、调用业务服务 API 之后的响应处
理。
```
### Zuul2
![1045f3bffc8ffec6156b88b933ef6899.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p36)
Zuul 2.x 是基于 Netty 内核重构的版本。

核心功能：

1. Service Discovery

2. Load Balancing

3. Connection Pooling

4. Status Categories

5. Retries

6. Request Passport

7. Request Attempts

8. Origin Concurrency Protection

9. HTTP/2

10. Mutual TLS

11. Proxy Protocol

12. GZip

13. WebSockets

### Spring Cloud Gateway
![8bf1e9aad7e5a9b6c507e0dcc0fef974.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p24)
1. 基于webflux是基于netty的Reactor 

### 网关对比

- 流量网关:性能好,适合流量网关

- 业务网关:拓展性好,适合业务网关,二次开发

### 手动实现网关
![46d0de8e413112ff7fe6c30aee6ecb45.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p25)
1. 网关是代理,通过网关获取后端服务数据

2. 过程:

    client -> gateway -> httpclient -> server
    
![0b760d328bed93177b2ce92a7e54e2a0.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p26)
- 过滤器-netty的handler (前置 后置)

![8263aabe3552f3e586e3be1049826671.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p28)
- router路由服务 - 控制访问后端服务

### childoption设置worker层级参数设置

- ServerBootStrap设置参数

- 绑定group(boss worker)

- channel

- channelinit处理channelpipeline

- handler boss本身的handler childhandler是worker的handler 

- 客户端只需要WorkerEventLoopGroup

### Reactor模型
1. 单线程的Reactor模型
```
(1) 接收所有的请求到处理都是一个线程完成
```
2. 多线程的Reactor模型
```
(1) 接收所有的请求是单线程处理
(2) 具体业务处理线程是多线程
(3) 接收请求和处理请求进行拆分
```
3. 主从Reacotr模型
```
(1) 接收请求由单线程改变多线程
```


-----------------------------

### netty这三种Reacotr模型都支持
1. EventLoop对应的是线程
2. EventLoopGroup对应的是线程池
3. 默认线程数是2倍cup内核数
4. eventloop是单线程的,避免出现阻塞情况,影响其他业务处理,尽量单业务和IO分离
5. 核心对象 
```
1) Bootstrap对象是引导器其他是挂在它下面
2) EventLoopGroup是Reactor
3) Channel
4) handler
```
6. EventLoopGroup线程数配置
```
(1) 业务处理中存在IO操作采用同步操作时,可以配置大一些
(2) 正常配置cup内核数*2
```
7. 设计
```
(1)把工具转化为实际,设计应用结构
(2)理清概念,建立概念之间关系

```
业务设计:通过设计将业务复杂度降低  
设计:业务复杂度(业务概念流程规则)和技术复杂度  
抽象:概念理清(代码和数据库之间层次划分),正确命名 理清相互之间关系 
组合:组件之间相互关系