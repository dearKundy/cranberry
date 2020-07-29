
> 一个Java程序猿的自我修养：系统设计、常用技术栈、Java 基础、算法。

## 系统设计
- 数据库锁
    - [code-悲观锁](src/main/java/com/kundy/cranberry/systemdesign/dblock/PessimisticLock.java)
    - [code-乐观锁](src/main/java/com/kundy/cranberry/systemdesign/dblock/OptimismLock.java)
- [doc-分布式主键生成策略](docs/systemdesign/分布式主键生成策略.md)    
- 限流     
    - [code-单机限流【计数器算法实现】](src/main/java/com/kundy/cranberry/systemdesign/ratelimiter/StandAloneRateLimiter.java)
    - [code-分布式限流【基于 redis 实现】](src/main/java/com/kundy/cranberry/systemdesign/ratelimiter/RedisRateLimiter.java)
- 分布式锁
    - [code-基于数据库实现](src/main/java/com/kundy/cranberry/systemdesign/distributedlock/DbDistributedLock.java)
    - [code-基于 Redis 实现](src/main/java/com/kundy/cranberry/systemdesign/distributedlock/RedisDistributedLock.java)
    - [code-Redisson 分布式锁的简单使用](src/main/java/com/kundy/cranberry/systemdesign/distributedlock/RedissonDistributedLock.java)
    - 【TODO】基于 zk 实现 
    - [code-Curator 分布式锁的简单使用](src/main/java/com/kundy/cranberry/systemdesign/distributedlock/CuratorDistributedLock.java)
- example
    - [code-zkClient 简单使用](src/main/java/com/kundy/cranberry/systemdesign/example/ZkExample.java)
- [doc-redis 常见问题](docs/systemdesign/redis常见问题.md)
    - [code-数据库缓存双写一致性](src/main/java/com/kundy/cranberry/systemdesign/redisproblem/DbCacheDoubleWriteConsistency.java)
- 布隆过滤器
    - [code-简单手撸实现](src/main/java/com/kundy/cranberry/systemdesign/bloomfilter/SimpleBloomFilter.java)
    - [code-guava 布隆过滤器实现通过去重工具](src/main/java/com/kundy/cranberry/systemdesign/deduplication)
- [doc-CAP 与 BASE 理论](docs/systemdesign/CAP与BASE理论.md)
- [doc-分布式系统一致性问题](docs/systemdesign/分布式系统一致性问题.md)
- [doc-设计模式](docs/systemdesign/设计模式.md)
    - [code-适配器模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/adapter)
    - [code-命令模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/command)
    - [code-装饰器模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/decorator)
    - 工厂模式
        - [code-简单工厂](src/main/java/com/kundy/cranberry/systemdesign/designpattern/factory/simple)
        - [code-工厂方法](src/main/java/com/kundy/cranberry/systemdesign/designpattern/factory/factory)
        - [code-抽象工厂](src/main/java/com/kundy/cranberry/systemdesign/designpattern/factory/abstractfactory)
    - [code-观察者模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/observer)
    - [code-单例模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/singleton)
    - [code-状态模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/state)
    - [code-策略模式](src/main/java/com/kundy/cranberry/systemdesign/designpattern/strategy)

## 常用技术栈
- [doc-Spring](docs/thridparty/spring.md)
- [doc-SpringMVC](docs/thridparty/springmvc.md)
- [doc-MyBatis](docs/thridparty/mybatis.md)
- Spring 事务
    - [code-编程式事务【基于 PlatformTransactionManager】](src/main/java/com/kundy/cranberry/thirdparty/transaction/ProgrammingTx.java)
    - [code-编程式事务【基于 TransactionTemplate】](src/main/java/com/kundy/cranberry/thirdparty/transaction/TemplateTx.java)
    - [code-声明式事务【基于 @Transactional 注解】](src/main/java/com/kundy/cranberry/thirdparty/transaction/AnnotationTx.java)
- MySQL
    - [doc-MySQL explain](docs/thridparty/mysql/explain.md)
    - [doc-MySQL常见问题](docs/thridparty/mysql/MySQL常见问题.md)
    - [doc-索引失效](docs/thridparty/mysql/索引失效.md)
    - [doc-B+树索引](docs/thridparty/mysql/B+树索引.md)
    - [数据库事务]()
    
- [doc-ZooKeeper](docs/thridparty/zookeeper.md)
- [doc-Git](docs/thridparty/git.md)
- [code-Dozer](src/main/java/com/kundy/cranberry/thirdparty/dozer)
- [code-guava常用方法](src/main/java/com/kundy/cranberry/thirdparty/guava/GuavaUsage.java)
- [doc-Linux 常用命令](docs/thridparty/Linux常用命令.md)
- [RocketMQ]()

## Java 基础
- 关键字
    - [final]()
- Java 8 新特性
    - [code-lambda 表达式](src/main/java/com/kundy/cranberry/javabasis/newfeature/lambdaexpression)
    - [code-Stream API](src/main/java/com/kundy/cranberry/javabasis/newfeature/streamapi/StreamApiTest.java)
    - [code-Collection forEach](src/main/java/com/kundy/cranberry/javabasis/newfeature/collectionforeach/CollectionForEachTest.java)
- [code-Java SPI 机制](src/main/java/com/kundy/cranberry/javabasis/spi)
- 多线程
    - [code-线程的创建](src/main/java/com/kundy/cranberry/javabasis/multithread/create/HowCreateAThread.java)
    - [code-线程控制-join](src/main/java/com/kundy/cranberry/javabasis/multithread/control/JoinTest.java)
    - [code-锁-synchronized](src/main/java/com/kundy/cranberry/javabasis/multithread/lock/TestSynchronizedMain.java)
    - [doc-synchronized实现原理](docs/javabasis/multithread/synchronized实现原理.md)
    - [doc-reentrantLock实现原理](docs/javabasis/multithread/ReentrantLock实现原理.md)
    - [doc-AQS]()
    - [code-锁-ReentrantLock](src/main/java/com/kundy/cranberry/javabasis/multithread/lock/TestReentrantLockMain.java)
    - [doc-synchronized与ReentrantLock比对](docs/javabasis/multithread/synchronized与ReentrantLock比对.md)
    - [doc-Java锁](https://tech.meituan.com/2018/11/15/java-lock.html)
    - [code-线程通信 wait、notify、notifyAll](src/main/java/com/kundy/cranberry/javabasis/multithread/threadsignal/WaitAndNotifyAndNotifyAllMain.java)
    - [doc-线程通信](docs/javabasis/multithread/线程通信.md)
    - [code-利用线程通信实现生产者消费者模式-wait、notifyAll版本](src/main/java/com/kundy/cranberry/javabasis/multithread/threadsignal/producerconsumermodel/MyContainer1.java)
    - [code-利用线程通信实现生产者消费者模式-condition版本](src/main/java/com/kundy/cranberry/javabasis/multithread/threadsignal/producerconsumermodel/MyContainer2.java)
    - [code-wait、notify 实现两个线程交替输出](src/main/java/com/kundy/cranberry/javabasis/multithread/threadsignal/AlternateOutput.java)
    - [code-锁的可重入性质](src/main/java/com/kundy/cranberry/javabasis/multithread/lock/reentrant/ReentrantTestMain.java)
    - [code-公平锁与非公平锁](src/main/java/com/kundy/cranberry/javabasis/multithread/lock/fair/FairOrNoFairLock.java)
    - [doc-公平锁与非公平锁](docs/javabasis/multithread/公平锁与非公平锁.md)
    - [code-volatile](src/main/java/com/kundy/cranberry/javabasis/multithread/myvolatile/VolatileTest.java)
    - [doc-volatile](docs/javabasis/multithread/volatile.md)
    - [code-threadLocal](src/main/java/com/kundy/cranberry/javabasis/multithread/mythreadlocal/ThreadLocalTest.java)
    - [doc-threadLocal实现原理】](docs/javabasis/multithread/threadLocal实现原理.md)
    - [code-CountDownLatch、CyclicBarrier、Semaphore](src/main/java/com/kundy/cranberry/javabasis/multithread/tools)
    - [doc-CountDownLatch、CyclicBarrier、Semaphore](docs/javabasis/multithread/CountDownLatch、CyclicBarrier、Semaphore.md)
    - [doc-Java内存模型与线程 【volatile、ThreadLocal 深入探讨】](docs/javabasis/multithread/Java内存模型与线程.md)
    - [doc-并发编程知识点大杂烩](docs/javabasis/multithread/并发编程知识点大杂烩.md)
    - 线程池
        - [doc-线程池基础](/docs/javabasis/threadpool/线程池基础.md)
        - [code-线程中断与线程池关闭问题](src/main/java/com/kundy/cranberry/javabasis/threadpoolproblem/ThreadPoolShutdown.java)

- [doc-Java 集合](docs/javabasis/Java集合.md)
- [doc-类加载机制](docs/javabasis/类加载机制.md)
- [doc-JVM 内存分配与垃圾回收](docs/javabasis/垃圾回收.md)

## 算法
- 排序
    - [code-选择排序](src/main/java/com/kundy/cranberry/algorithm/sort/SelectSort.java)
    - [code-冒泡排序](src/main/java/com/kundy/cranberry/algorithm/sort/BubbleSort.java)
    - [code-插入排序](src/main/java/com/kundy/cranberry/algorithm/sort/InsertSort.java)

## 数据结构
- [doc-树](docs/datastructure/树.md)