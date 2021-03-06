学习笔记

### 简介
1. 作用:rpc框架主要作用于异构类分布式系统之间通信
2. 概念:远程过程调用,像调用本地方法一样调用远程方法,屏蔽网络调用
3. 二方库:公司内部封装的,上传到maven的neusx上面的依赖
4. 三方库:第三方的依赖
5. stub远程进程存根:本地远程代理，rpc运行时库传输网络中调用。
    - 屏蔽网络调用涉及细节,编码解码操作等
6. IDL：接口定义语言
7. service嵌套问题
    - 循环依赖问题
    - 事务
8. websocket是http基础上的tcp的流，http上指定head(ugrade)
### RPC原理
1. rpc如果实现将本地调用转换到远程调用?
2. RPC组件:
    - 本地代理存根
    - 序列化和反序列化
    - 网络通信
    - 远程序列化和反序列化
    - 远程服务存根
    - 调用实现
    - 原路返回结果
### RPC设计1
1. 远程和本地需要共享信息
    - 接口定义和POJO实体类定义
### RPC设计2
1. 代理
    - java里面可以选择动态代理和AOP代理模式
### RPC设计3
1. 序列化和反序列化选择
    - java语言原生RMI,Remoting
    - 二进制平台无关:Hessian,avro,kyro,fst ， 可读性差,节省空间
    - 文本,json,xml , 占用空间大,可读性强
### RPC设计4
1. 网络传输
    - TCP/SSL
    - HTTP/HTTPS
### RPC设计5
1. 实现类查找
    - 通过接口查找服务端的实现类
    - dubbo默认将接口和实现配置在spring中
### 业务和框架分离
1. 框架内容单独抽到新的项目,将业务和框架剥离
### RPC走向服务化
1. 多个相同服务如何管理,比如一个服务部署多个节点,如何管理这些节点
    - 多机器
    - 动态配置
    - 多版本
    - 多环境
2. 服务注册和发现
    - 自动获取新加入的机器
    - 监控服务可用性,如果机器不可用,自动将服务从可用列表中删除
3. 如何负载均衡,路由
    - 机器配置不同承受能力不一样,流量分流
    - 通过tag标记(例如head)走到指定机器上
4. 熔断和限流能力，在流量大的情况下保持一定输出能力
    - 熔断:中断服务能力
    - 限流:流量限制
5. 重试策略
6. 高可用,监控,性能等等