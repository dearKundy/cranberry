
> 一个Java程序猿的自我修养：系统设计、常用技术栈、Java 基础、算法。

## 系统设计
- 数据库锁
    - [悲观锁](src/main/java/com/kundy/cranberry/systemdesign/dblock/PessimisticLock.java)
    - [乐观锁](src/main/java/com/kundy/cranberry/systemdesign/dblock/OptimismLock.java)
- [分布式主键生成策略](docs/systemdesign/分布式主键生成策略.md)    
- 限流     
    - [单机限流【计数器算法实现】](src/main/java/com/kundy/cranberry/systemdesign/ratelimiter/StandAloneRateLimiter.java)
    - [分布式限流【基于 redis 实现】](src/main/java/com/kundy/cranberry/systemdesign/ratelimiter/RedisRateLimiter.java)
- 分布式锁
    - [基于数据库实现](src/main/java/com/kundy/cranberry/systemdesign/distributedlock/DbDistributedLock.java)
    - [基于 Redis 实现](src/main/java/com/kundy/cranberry/systemdesign/distributedlock/RedisDistributedLock.java)
    - [Redisson 分布式锁的简单使用](src/main/java/com/kundy/cranberry/systemdesign/distributedlock/RedissonDistributedLock.java)
    - 【TODO】基于 zk 实现 
    - [Curator 分布式锁的简单使用](src/main/java/com/kundy/cranberry/systemdesign/distributedlock/CuratorDistributedLock.java)
- example
    - [zkClient 简单使用](src/main/java/com/kundy/cranberry/systemdesign/example/ZkExample.java)
- [redis 常见问题](docs/systemdesign/redis常见问题.md)
    - [数据库缓存双写一致性](src/main/java/com/kundy/cranberry/systemdesign/redisproblem/DbCacheDoubleWriteConsistency.java)
- 布隆过滤器
    - [简单手撸实现](src/main/java/com/kundy/cranberry/systemdesign/bloomfilter/SimpleBloomFilter.java)
    - [guava 布隆过滤器实现通过去重工具](src/main/java/com/kundy/cranberry/systemdesign/deduplication)
- [CAP 与 BASE 理论](docs/systemdesign/CAP与BASE理论.md)
- [分布式系统一致性问题](docs/systemdesign/分布式系统一致性问题.md)
- [设计模式](docs/systemdesign/设计模式.md)
    - [适配器模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/adapter)
    - [命令模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/command)
    - [装饰器模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/decorator)
    - 工厂模式
        - [简单工厂](src/main/java/com/kundy/cranberry/systemdesign/designpattern/factory/simple)
        - [工厂方法](src/main/java/com/kundy/cranberry/systemdesign/designpattern/factory/factory)
        - [抽象工厂](src/main/java/com/kundy/cranberry/systemdesign/designpattern/factory/abstractfactory)
    - [观察者模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/observer)
    - [单例模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/singleton)
    - [状态模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/state)
    - [策略模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/strategy)

## 常用技术栈
- [Spring](docs/thridparty/spring.md)
- [SpringMVC](docs/thridparty/springmvc.md)
- [MyBatis](docs/thridparty/mybatis.md)
- Spring 事务
    - [编程式事务【基于 PlatformTransactionManager】](src/main/java/com/kundy/cranberry/thirdparty/transaction/ProgrammingTx.java)
    - [编程式事务【基于 TransactionTemplate】](src/main/java/com/kundy/cranberry/thirdparty/transaction/TemplateTx.java)
    - [声明式事务【基于 @Transactional 注解】](src/main/java/com/kundy/cranberry/thirdparty/transaction/AnnotationTx.java)
- MySQL
    - [MySQL explain](docs/thridparty/mysql/explain.md)
    - [MySQL常见问题](docs/thridparty/mysql/MySQL常见问题.md)
    - [索引失效](docs/thridparty/mysql/索引失效.md)
    - [B+树索引](docs/thridparty/mysql/B+树索引.md)
    - [数据库事务]()
    
- [ZooKeeper](docs/thridparty/zookeeper.md)
- [Git](docs/thridparty/git.md)
- [Dozer](src/main/java/com/kundy/cranberry/thirdparty/dozer)
- [guava常用方法](src/main/java/com/kundy/cranberry/thirdparty/guava/GuavaUsage.java)
- [Linux 常用命令](docs/thridparty/Linux常用命令.md)

## Java 基础
- 线程池
    - [线程中断与线程池关闭问题](src/main/java/com/kundy/cranberry/javabasis/threadpoolproblem/ThreadPoolShutdown.java)
- Java 8 新特性
    - [lambda 表达式](src/main/java/com/kundy/cranberry/javabasis/newfeature/lambdaexpression)
    - [Stream API](src/main/java/com/kundy/cranberry/javabasis/newfeature/streamapi/StreamApiTest.java)
    - [Collection forEach](src/main/java/com/kundy/cranberry/javabasis/newfeature/collectionforeach/CollectionForEachTest.java)
- [Java SPI 机制](src/main/java/com/kundy/cranberry/javabasis/spi)
- 多线程
    - [并发编程知识点大杂烩](docs/javabasis/multithread/并发编程知识点大杂烩.md)
    - [CountDownLatch、CyclicBarrier、Semaphore](docs/javabasis/multithread/CountDownLatch、CyclicBarrier、Semaphore.md)
    - [Java内存模型与线程 【volatile、ThreadLocal 深入探讨】](docs/javabasis/multithread/Java内存模型与线程.md)
- [Java 集合](docs/javabasis/Java集合.md)
- [类加载机制](docs/javabasis/类加载机制.md)
- [JVM 内存分配与垃圾回收](docs/javabasis/垃圾回收.md)

## 算法
- 排序
    - [选择排序](src/main/java/com/kundy/cranberry/algorithm/sort/SelectSort.java)
    - [冒泡排序](src/main/java/com/kundy/cranberry/algorithm/sort/BubbleSort.java)
    - [插入排序](src/main/java/com/kundy/cranberry/algorithm/sort/InsertSort.java)

## 数据结构
- [树](docs/datastructure/树.md)