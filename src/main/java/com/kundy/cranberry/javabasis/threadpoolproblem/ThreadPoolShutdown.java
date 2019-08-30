package com.kundy.cranberry.javabasis.threadpoolproblem;

import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池关闭的几个问题
 * <p>
 * 线程池自动关闭的两个条件：1、线程池的引用不可达；2、线程池中没有线程；
 * <p>
 * 参考文章：https://www.jianshu.com/p/bdf06e2c1541
 *
 * @author kundy
 * @date 2019/8/27 10:03 PM
 */
public class ThreadPoolShutdown {

    /**
     * 运行异常:java.lang.OutOfMemoryError: unable to create new native thread
     * <p>
     * FixedThreadPool 的 coreSize 大于0，就算是空闲的线程也会被保留下来，并不会自动关闭。
     */
    public static void fixPoolNoShutdown() {
        while (true) {
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            executorService.execute(() -> System.out.println("running"));
            executorService = null;
        }
    }

    /**
     * 运行结果：
     * 【调用 service.shutdown()】: 控制台打印 1 3 4 2 ，程序在输出完 2 【2秒后】运行结束。
     * 【不调用 service.shutdown()】: 控制台打印 1 3 4 2，程序在输出完 2 【60秒后】运行结束。因为线程默认存活 60 秒
     * 【不调用 service.shutdown()，且设置 keepAliveTime 为 1ns】：控制台打印 1 3 4 2，程序在输出完 2 【2秒后】运行结束。
     */
    public static void cachePoolNoShutdown() {
        // 默认 keepAliveTime 为 60s
        ExecutorService service = Executors.newCachedThreadPool();
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) service;
        // 设置 keepAliveTime 为 1ns
//        threadPoolExecutor.setKeepAliveTime(1, TimeUnit.NANOSECONDS);
        service.execute(() -> {
            try {
                System.out.println("1");
                Thread.sleep(3000);
                System.out.println("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("3");
        service.shutdown();
        System.out.println("4");
    }

    /**
     * 调用 shutdown 之后是否还能否接收新任务？【不能，会执行拒绝策略】
     * <p>
     * java.util.concurrent.RejectedExecutionException
     */
    public static void canAcceptNewTaskAfterShutdown() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 19, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executor.execute(() -> {
            System.out.println("before shutdown");
        });
        executor.shutdown();
        executor.execute(() -> {
            System.out.println("after shutdown");
        });

    }

    /**
     * 关闭线程池之后等待队列里的任务是否还会执行？
     * 【调用 shutdown】会继续执行，直到全部执行完毕。
     * 【调用 shutdownNow】不会继续执行。
     */
    public static void canExecuteTaskInBlockingQueueAfterShutdown() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        for (int i = 0; i < 100; i++) {
            workQueue.add(new Task(String.valueOf(i)));
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, workQueue);
        executor.execute(new Task("0"));
//        executor.shutdown();
        List<Runnable> dropList = executor.shutdownNow();
        System.out.println("workQueue size = " + workQueue.size() + " after shutdown");
        System.out.println("dropList size = " + dropList.size());
    }

    /**
     * 关于线程中断：
     * <p>
     * 首先，一个线程不应该由其他线程来强制中断或停止，而是应该由线程自己自行停止。
     * 所以，Thread.stop, Thread.suspend, Thread.resume 都已经被废弃了。
     * <p>
     * 而 Thread.interrupt 的作用其实也不是中断线程，而是「通知线程应该中断了」，【具体到底中断还是继续运行，应该由被通知的线程自己处理，通过 Thread.currentThread().isInterrupted() 判断】。
     * <p>
     * 具体来说，当对一个线程，调用 interrupt() 时：
     * ① 如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常。仅此而已。
     * ② 如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。被设置中断标志的线程将继续正常运行，不受影响。
     * interrupt() 并不能真正的中断线程，需要被调用的线程自己进行配合才行。
     * <p>
     * 也就是说，一个线程如果有被中断的需求，那么就可以这样做：
     * ① 在正常运行任务时，经常检查本线程的中断标志位，如果被设置了中断标志就自行停止线程。
     * ② 在调用阻塞方法时正确处理InterruptedException异常。（例如，catch异常后就结束线程。）
     * <p>
     * Thread.currentThread().interrupt()：通知线程应该中断了。
     * Thread.currentThread().isInterrupted()：返回当前线程中断标志位的值
     * Thread.interrupted():返回当前线程中断标志位的值，并中断标志位置为 false。
     */
    public static void aboutInterrupted() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(i);
//                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(Thread.interrupted());
            }
        }).start();
    }

    /**
     * 调用 shutdownNow 之后正在执行的任务是否会立即中断？【会被中断】
     */
    public static void willInterruptTaskOnRunningAfterShutdown() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(() -> {
            for (int i = 0; i < 100 && !Thread.currentThread().isInterrupted(); i++) {
                System.out.println(i);
                System.out.println(Thread.currentThread().isInterrupted());
            }
        });
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdownNow();

    }

    /**
     * 关于 awaitTermination
     * 阻塞当前线程，【直到】下面其中之一的情况发生（以下情况发生，则放行程序）：
     * 1. 线程池中所有的任务完成。
     * 2. 阻塞时间超过 timeout。
     * 3. 当前线程被中断。
     * <p>
     * 返回：线程池队列中的任务是否全部完成
     */
    public static void aboutAwaitTermination() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        service.shutdown();
        while (!service.awaitTermination(100, TimeUnit.SECONDS)) {
            System.out.println("线程池中任务还没有全部完成");
        }
        System.out.println("线程池中任务全部完成");
    }

    static class Task implements Runnable {

        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("task " + name + " is running");
            }
            System.out.println("task " + name + " is over");
        }
    }


    public static void main(String[] args) throws Exception {
        aboutAwaitTermination();

    }
}
