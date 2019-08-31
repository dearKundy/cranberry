
> 一个Java程序猿的自我修养：系统设计、常用技术栈、Java 基础、算法。

## 系统设计
- 数据库锁
    - 悲观锁
    - 乐观锁
- 限流     
    - 单机限流【计数器算法实现】
    - 分布式限流【基于 redis 实现】
- 分布式锁
    - 基于数据库实现
    - 基于 Redis 实现
    - Redisson 分布式锁的简单使用
    - 【TODO】基于 zk 实现 
    - Curator 分布式锁的简单使用
- example
    - zkClient 简单使用
- redis 常见问题
    - 数据库缓存双写一致性
- 布隆过滤器
    - 简单手撸实现
    - guava 布隆过滤器实现通过去重工具
- 设计模式
    - 适配器模式
    - 命令模式
    - 装饰器模式
    - 工厂模式
        - 简单工厂
        - 工厂方法
        - 抽象工厂
    - 观察者模式
    - 单例模式
    - 状态模式
    - 策略模式

## 常用技术栈
- Spring 事务
    - 编程式事务【基于 PlatformTransactionManager】
    - 编程式事务【基于 TransactionTemplate】
    - 声明式事务【基于 @Transactional 注解】


## Java 基础
- 线程池
    - 线程中断与线程池关闭问题
- Java 8 新特性
    - lambda 表达式
    - Stream API
    - Collection forEach
- Java SPI 机制
- 多线程
    - 工具
        - [CountDownLatch、CyclicBarrier、Semaphore](https://github.com/dearKundy/cranberry/docs/javabasis/multithread/tools/CountDownLatch、CyclicBarrier、Semaphore.md)


## 算法
- 排序
    - 选择排序
    - 冒泡排序
    - 插入排序