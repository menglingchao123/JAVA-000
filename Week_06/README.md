### Spring配置发展
1. xml--全局
2. 注解--类
3. java配置类--方法
4. spring4.0--springboot
5. Spring发展过程中功能越来越复杂（bean工厂,ApplicationContext,Aop,SpringSecurity,SpringBatch)
### Springboot出发点
1. 常用功能进行整合，做出最佳实践，也就是脚手架
2. 开发变简单
3. 配置变简单
4. 运行变简单
5. 实现是基于约定大于配置:按照开发规范开发,尽量不需要配置,大部分都有默认配置
6. SpringBoot是一个整合范围更大的脚手架
### SpringBoot简化开发实现
1. 框架是各种工具整合在一起跟业务无关
2. 脚手架是对各种框架进行整合,我们可以直接进行开发
3. 解决方案是针对某一领域问题的一系列问题解决方案
4. 简化开发
```
    1、Spring本身技术的成熟与完善，各方面第三方组件的成熟集成 
    2、Spring团队在去web容器化等方面的努力 
    3、基于MAVEN与POM的Java生态体系，整合POM模板成为可能 
    4、避免大量maven导入和各种版本冲突 Spring Boot 是 Spring 的一套快速配置脚手架，       关注于自动配置，配置驱动。
```
### SpringBoot定义
1. 限定性视角:Springboot已经搭配好的东西,我们引入第三方组件只需要引入一个搭配好的starter
2. 定义:SpringBoot使独立运行生成级别的Spring应用变得容易,可以独立运行。我们对Spring平台和第三方库采用限定性视角,以此让大家在最小成本上下手,大部分SpringBoot应用只需要少量应用和配置。
3. 特性
```
1. 创建独立运行的Spring应用
2. 直接嵌入Tomcat或Jetty，Undertow，无需部署WAR包 
3. 提供限定性的starter依赖简化配置（就是脚手架）
4. 在必要时自动化配置Spring和其他三方依赖库
5. 提供生产production-ready 特性，例如指标度量，健康检查，外部配置等 
6. 完全零代码生产和不需要XML配置
```
4. Maven
```
(1)SpringBoot的jar包运行 --- java -jar
(2)通过maven启动,使用maven官方插件 --- shade
```
### SpringBoot核心原理
##### 自动化配置
1. Configuration EnableXX Condition
2. application.yaml->configuration->bean
3. 实现原理:
    - @EnbaleAutoConfiguration
    - 创建配置类@Configuration
    - 创建自动配置类@Import
    - 创建spring.factories文件引入配置自动配置类
4. SpringBoot自动化配置注解
```
@SpringBootApplication 
SpringBoot应用标注在某个类上说明这个类是SpringBoot的主配置类，SpringBoot就会运行这 个类的main方法来启动SpringBoot项目。 
•@SpringBootConfiguration 
•@EnableAutoConfiguration 
•@AutoConfigurationPackage              •@Import({AutoConfigurationImportSelector.class}) 
加载所有META-INF/spring.factories中存在的配置类（类似SpringMVC中加载所converter）
```
5. 条件化装配
```
@ConditionalOnBean 
@ConditionalOnClass 
@ConditionalOnMissingBean 
@ConditionalOnProperty 
@ConditionalOnResource 
@ConditionalOnSingleCandidate 
@ConditionalOnWebApplication
```
##### SpringBoot-start脚手架核心
1. 整合第三方类库和协作工具
2. spring-boot-parent引入定义类库之间依赖关系
3. 引入starter
4. 引入springboot打包插件
5. 配置文件前缀->一组配置->starter组件
##### 约定大于配置
优势:
```
一、Maven的目录结构：默认有resources文件夹存放配置文件。默认打包方式为jar。 
二、默认的配置文件：application.properties 或 application.yml 文件
三、默认通过 spring.profiles.active 属性来决定运行环境时的配置文件。 
四、EnableAutoConfiguration 默认对于依赖的 starter 进行自动装载。 
五、spring-boot-start-web 中默认包含 spring-mvc 相关依赖以及内置的 web容器， 使得构建一个 web 应用更加简单。
```

### SpringBoot starter
![048aa5118007b5fcfe63b88903e44474.jpeg](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p85)
1. Spring.provides
2. Spring.factories
3. additonal-meta
4. 自定义Configuration类
![83906f77066b0c73e9df799efd7ed922.jpeg](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p86)
```
(1)Configuartion定义是一个java配置类
(2)@ComponentScan添加依赖的组件
(3)设置开启配置条件(使用Spring的properties配置,前置条件,配置条件)
```
### JDBC
![f0ccb78db241f3cf533939975cbc24d4.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p87)
![3aefdddfc781fb9ae9a296af32db6fd2.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p88)
1. JDBC 定义了数据库交互接口： 
DriverManager 
Connection 
Statement 
ResultSet 
2. 后来又加了DataSource--Pool
```
   (1)数据库连接池作用:解决数据库连接资源频繁的创建和销毁
```
3. java里面操作各种数据库的类库,实际上是在jdbc基础之上做的增强实现。
```
   (1)加上XA事物 --- XAConnection
   (2)从连接池获取 --- poolConnection
   (3)Mysql驱动jdbc接口 --- connection
      a.通过代理模式方式,可以通过poolConnection获取连接,添加事物
```
##### 数据库连接池
- c3p0
- dbcp -- apache-commonpool
- druid
- hikari
- json序列化,字节码增强工具,数据库连接池
- 连接池中获取数据库连接的差异是在真实创建数据库连接上面,但是我们在使用过程中是连接池中获取,所以速度差异不是很大
### ORM框架
1. ORM框架作用:sql关系代数语言,java是面向对象的语言,orm框架是将对象转成数据库中一条sql
##### Hibernate
1. hbm映射文件:指的是实体类对象和数据库表结构的关系
2. HQL语言:hql语言通过hbm文件翻译成sql
3. 通过映射文件和实体类生产数据库表结构
##### Mybatis
1. 功能
- 定制化sql
- 存储过程
- 查询结果集映射
2. 简化
- 省略jdbc参数配置
- 手动获取结果集
##### Mybatis - 半自动ORM
1. mapper.xml作用:设置映射规则和定义sql
2. 避免mapper.xml和mapper接口覆盖问题,使用新建mapper.xml和接口进行继承
### Mybatis与hibernate比较
1. hibernate对sql控制不好，执行时候才生产sql语句，不利于sql优化
2. mybatis优点:原生sql,对DBA友好
3. hibernate优点:简单场景不用写sql(HQL,SQL,cretiral)
4. mybatis缺点:繁琐需要生产响应xml文件,可以用mybatis-plus或者mybatis-generator
### JPA
1. java持久化API,是一套ORM框架,通过注解方式描述实体类与表关系映射，运行期将对象保存到数据库中。
2. 核心类:EntityManager
### Spring JDBC与ORM
1. JDBC DataSource Spring JDBC
2. JPA EntityManager Spring ORM 
### Spring事物管理
![295d7856edcb04931c27bd60b24ae2dc.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p89)
1. 编程式事物
2. 声明式事物:事物管理器+AOP
3. 使用
    - 事物传播特性
    - 隔离级别
    - 只读
    - 事物的超时性
    - 回滚
4. 事物嵌套
    - service之间不进行相互调用
    - service调用其业务dao
    - service之间通过加个外层方式
 ### Spring事物和传播机制
 
1.本地事务（事务的设计与坑） 
2、多数据源（配置、静态制定、动态切换）
 - 事物指定数据源
 - mapper接口指定数据源
3、线程池配置（大小,重连,超时） 
4、ORM内的复杂SQL，级联查询 
5、ORM辅助工具和插件

-----------------------------------------------------------------------------------------------

### 性能
##### 高性能
1. 低延迟
2. 高吞吐量
3. 通过控制指标发现偏离正常指标的时刻发生的事件进行分析可以发现问题
4. 80/20原则:解决系统前10个问题,那么系统百分之8090问题都解决了
##### 高可用(性能感知能力)
1. 单个节点宕机后,整体对外提供服务不受影响,从节点代替主节点对外提供服务
2. 网络中断,连接池有探活能力,重连机制
##### 高稳定性
##### 可维护性
### 关系型数据库
1. Tuple:即向量(列名,列名,...)
2. 定义:关系代数理论做为数学基础的数据库,codd提出的数据库的数据关系模型
3. 数据库:
 - 行:实例
 - 列:属性
 - 表:实体
 - 单个字段查询:即投影,将向量多维投影到一个维度上面。
### 数据库设计范式
1. 作用:设计数据库的规约,统一数据库设计标准
#### 数据库设计第一范式
1. 关系R属于第一范式,当且仅当R中的每一个属性A的值域只包含原子项(每个字段都是不可分割)
2. 作用:声明表中每一列数据项都是不可再分的数据项,即每个数据项都是原子的。
#### 数据库设计第二范式
1. 作用:消除部分依赖,表中没有列只与主键的部分相关,即每一行都被主键唯一标识,每一行都会有一个主键。（即表中主键可以唯一标识表中每一列）
#### 数据库设计第三范式
1. 作用:消除传递依赖,消除表中列不依赖主键,而是依赖表中非主键部分,即没有列不与主键相关(即表中每一列都与主键相关)
#### BCNF
1. 作用:消除对主属性对码的部分与传递函数依赖。
### SQL
1. DQL:查询SQL
2. DML:增删改SQL
3. TCL:事务SQL
4. DDL:创建修改删除表结构SQL
5. DCL:权限控制操作SQL
6. CCL:游标用于对一个表或者多个表单独行的操作。
7. SQL解析器parser
### Mysql数据库版本
- 4.0支持InnoDB，事务，InnoDB是第三方的引擎
- 2003年 5.0,大规模使用
- 5.6===>历史上使用最多版本,长期稳定版本
- 5.7===>近期使用最多版本
- 8.0===>最新和功能最完善的版本
- mysql的sql有一些不规范的地方,postGreSql相对规范
#### 5.6与5.7区别
1. 功能 性能 可靠性
2. 5.7增强
 - 多主
 - MGR高可用,主节点宕机后从节点代替主节点
 - 分区表(关键字partition),创建表时将表中数据按照不同条件存到不同文件中
 - json支持
 - 性能增强
 - 修复XA事务(分布式事务支持)
#### 5.7与8.0区别
- 通用表达式,例如sql编程,查询一个树结构
- 窗口函数,例如分组统计,例如统计课程分数前三名的学生
- 持久化参数,例如设置字符集默认是对当前连接有效,重启失效,通过persistence关键字(set persistence 参数)
- 自增列持久化,8.0之前自增列不会持久化
- 默认字符集utf8mb4
- DDL原子性
- JSON增强
- 不再对groupby进行隐式排序,8.0之前进行分组会先排序后分组
- Mysql8.0的加密方式改变了
### 深入理解数据库原理
- Connection pool:处理连接请求
- SQL Interface
- parser:解析sql语句
- Optimizer:通过策略或之前的统计数据分析,生成执行计划
- Caches&Buffer:缓存
- Engine:
    - MyIsAm:速度快,一致性差,不支持事务
    - InnoDB:支持事务
    - Archive:压缩归档
    - Memory:内存,临时表,重启后消失
- File System
- files & logs
#### mysql引擎对比
![aa22b266c402fc3bcecce564086598f0.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p90)
#### mysql引擎状态
![453d44947a45673082e95709c0e7751f.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p91)
### Mysql存储
独占模式
1）、日志组文件：ib_logfile0和ib_logfile1，默认均为5M
2）、表结构文件：*.frm 
3）、独占表空间文件：*.ibd (存数据)
4）、字符集和排序规则文件：db.opt（指定字符集）
5）、binlog 二进制日志文件：记录主数据库服务器的 DDL 和 DML 操作
6）、二进制日志索引文件：master-bin.index
共享模式 
innodb_file_per_table=1
1）、数据都在 ibdata1
2) infomation_schema是存储mysql基础信息,例如字符集，分区,sql列表,视图,tables是所有表
3)创建数据库实例 create database 库名 或者 create schema 库名
4) mysql server是数据库实例,show database 展示是数据库
5）select table_name from tables 查询所有表名称
6) show create table 表明 获取建表语句 DDL
7) show columns  from table 获取建表字段
### mysql执行流程
![02d44b210d3e2fff362387640be98af7.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p92)
### Mysql组件
1. 连接器:管理连接,权限认证
2. 解析器:分析sql(语法 词法)
3. 优化器:生成执行计划,索引选择
4. 执行器:操作执行引擎返回结果
### Mysql日志文件
1. undo log
2. redo log
3. bin log 
### mysql索引
1. 数据是按页划分
2. 聚集索引:按主键关系存储到数据文件中
3. InnoDB索引:B+tree实现聚集索引
### Mysql对sql执行顺序
![7fcb1d1a3cae223de2ea77b9b6e610c5.png](evernotecid://516996F9-1252-4EFC-AC10-228CA09F63B5/appyinxiangcom/31649193/ENResource/p105)
### 参数配置优化
1. 命令:
    - mysql -h ip地址 -p 端口 -u 用户名 -e "select * from test.test";
    - mysql -h ip地址 -p 端口 -u 用户名 -e "show variables";
2. 配置文件my.cnf
 - [mysqld] server端
 - [mysql] client端 mysql命令行
#### 连接参数配置
1. 连接请求变量
 - max_connections
 - back_log
 - wait_timeout和interative_timeout
2. 缓冲区
 - key_buffer_size
 - query_cache_size（查询缓存简称 QC)
 - max_connect_errors： 
 - sort_buffer_size： 
 - max_allowed_packet=32M 最大发包大小
 - join_buffer_size=2M
 - thread_cache_size=300
3. InnoDB参数配置
 - innodb_buffer_pool_size
 - innodb_flush_log_at_trx_commit
 - innodb_thread_concurrency=0
 - innodb_log_buffer_size
 - innodb_log_file_size=50M
 - innodb_log_files_in_group=3
 - read_buffer_size=1M
 - read_rnd_buffer_size=16M
 - bulk_insert_buffer_size=64M
 - binary log
#### 数据库优化
1. 引擎的选择 -- 根据引擎的特点和实际业务场景选择
2. 库表命名 -- 数据库规范制定(见名知意)
3. 宽表拆分 -- 主表只存储主要信息,附属信息挂在子表上面
4. 恰当选择数据类型 -- 明确 尽量小(查找方面优势) clob blob 影响性能不推荐使用
5. 文件 图片是否要插入数据库 -- 不建议半结构化数据太大,会打破数据存储,响应数据库性能
6. 时间日期存储方式 -- 根据自己需要选择时间格式 默认UTC 操作系统默认中八区跟UTC差8小时 存储时间戳
7. 有效数字精度问题 -- 整数和精度分成两个字段
8. 外建和触发器 -- 外建保证数据关系 触发器在不同机器上面时间可能不同,引入不确定性
9. 唯一约束和索引
10. 冗余字段
11. 是否使用 游标 变量 视图 存储过程 自定义函数 -- 不利于维护 调试
12. 自增主键使用问题 -- 单机可以使用 分布式不建议使用
13. 在线修改表结构 -- 一般在没人使用时进行操作
14. 逻辑删除和物理删除 -- 重要数据是逻辑删除,非重要数据物理删除
15. 创建修改时间戳 -- 做批量处理时候的时间戳
16. 数据库碎片 -- 通过压缩命令优化数据库碎片
17. 快速导入导出 备份数据 -- 数据还原 导入导出命令 去掉外建约束