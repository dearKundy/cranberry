#### CountDownLatch、CyclicBarrier、Semaphore

> J.U.C 中提供了三个同步工具CountDownLatch、CyclicBarrier、Semaphore，用来进行线程的任务协调。

相关测试代码：
- com.kundy.cranberry.javabasis.multithread.tools

1. `CountDownLatch`：门闩
    - `使用场景`：一个任务需要等待其他 N 个任务完成才能执行。
    - `重要方法`：
        - `public void await()`：调用 await() 方法的线程会被挂起，它会等待直到 count 值为 0 才继续执行。
        - `public boolean await(long timeout, TimeUnit unit)`：和 await() 类似，只不过等待一定的时间后 count 值还没变为 0 的话就会继续执行。
        - `public void countDown()`：将 count 值减 1。
        
![CountDownLatch.jpg](https://i.loli.net/2019/08/31/eljGSztmUn41PBp.png)        
        
        
        
2. `CyclicBarrier`：循环栅栏
    - `栅栏`：可以让一组线程等待至某个状态之后再全部同时执行（多线现在阻塞在 await() 处，当 await() 的线程数量到达一个指定数值之后，全部线程放行）。
    - `循环`：当所有等待线程都被释放以后，CyclicBarrier 可以被重用。
    
![CyclicBarrier.jpg](https://i.loli.net/2019/08/31/SGhAHWCy9LpYEMB.png)
    
    
    
3. `Semaphore`：信号量
    - `使用场景`：每次只允许N个线程同时运行。
    - `重要方法`
        - `void acquire()`：获取一个许可。
        - `void acquire(int permits)`：获取permits个许可。
        - `void release()`：释放一个许可。
        - `void release(int permits)`：释放permits个许可。
        
![Semaphore.jpg](https://i.loli.net/2019/08/31/hylBtb8PDu7sgiG.png)