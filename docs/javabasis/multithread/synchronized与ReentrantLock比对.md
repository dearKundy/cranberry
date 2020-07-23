## synchronized 和 ReentrantLock 有什么不同
- ReentrantLock 拥有 Synchronized 相同的并发性和内存语义，此外还多了锁投票，定时锁等候和中断锁等候
    - 线程A和B都要获取对象 O 的锁定，假设 A 获取了对象 O 锁，B 将等待 A 释放对 O 的锁定，如果使用 synchronized ，如果 A 不释放，B 将一直等下去，不能被中断如果 使用 ReentrantLock ，如果 A 不释放，可以使B在等待了足够长的时间以后，中断等待，而干别的事情。
    - ReentrantLock获取锁定与三种方式：
        1. `lock()` ：如果获取了锁立即返回，如果别的线程持有锁，当前线程则一直处于休眠状态，直到获取锁。
        2. `tryLock()`： 如果获取了锁立即返回true，如果别的线程正持有锁，立即返回false。
        3. `tryLock(long timeout,TimeUnit unit)`： 如果获取了锁定立即返回 true，如果别的线程正持有锁，会等待参数给定的时间，在等待的过程中，如果获取了锁定，就返回true，如果等待超时，返回false。
        4. `lockInterruptibly`：如果获取了锁定立即返回，如果没有获取锁定，当前线程处于休眠状态，直到或者锁定，或者当前线程被别的线程中断。

- `synchronized` 是在 `JVM` 层面上实现的，不但可以通过一些监控工具监控 `synchronized` 的锁定，而且在代码执行时出现异常，`JVM` 会自动释放锁定，但是使用 `Lock` 则不行，`lock` 是通过代码实现的，要保证锁定一定会被释放，就必须将 `unLock()` 放到`finally{}` 中。

- 简单总结：
    - `两者的共同点`：
        1. 协调多线程对共享对象、变量的访问。
        2. `可重入`，`同一线程` 可以 `多次` 获得 `同一个锁`。
        3. 都保证了可见性和互斥性。

    - `两者的不同点`：
        1. `ReentrantLock` 显示获得、释放锁，`synchronized` 隐式获得释放锁。
        2. `ReentrantLock` 可响应中断、可轮回，`synchronized` 是不可以响应中断的，为处理锁的不可用性提供了更高的灵活性。
        3. `ReentrantLock` 是 `API` 级别的，`synchronized` 是 `JVM` 级别的。
        4. `ReentrantLock` 可以实现 `公平锁`。
        5. `ReentrantLock` 通过 `Condition` 可以绑定多个条件。
        6. 底层实现不一样， `synchronized` 是同步阻塞，使用的是 `悲观` 并发策略，`lock` 是同步非阻塞，采用的是 `乐观` 并发策略。

- 什么时候选择使用 `synchronized`，什么使用选择使用 `ReentrantLock`
仅当 `synchronized` 不能满足时才使用 `ReentrantLock`，因为使用 `ReentrantLock` 要非常小心，不释放锁将影响其他需要该锁的代码块运行
    - 不能使用synchronized不满足的情形：
        1. 公平性。
        2. 可中断。
        3. 分块结构的加锁，比如jdk1.7ConcurrentHashMap的分段锁。
- `synchronized` 和 `ReentrantLock` 两者之间性能的比较
从 jdk1.5 以后，性能就差不多了，因为 `jvm` 对 `synchronized` 进行了很多优化。