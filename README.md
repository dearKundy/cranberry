
# 主要内容

- Spring 事务
    - 编程式事务【基于 PlatformTransactionManager】
    - 编程式事务【基于 TransactionTemplate】
    - 声明式事务【基于 @Transactional 注解】
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