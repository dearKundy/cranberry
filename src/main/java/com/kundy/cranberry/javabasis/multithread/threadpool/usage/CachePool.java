package com.kundy.cranberry.javabasis.multithread.threadpool.usage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 来多少任务，就创建多少线程（前提是没有空闲的线程在等待执行任务，否则还是会复用之前旧（缓存）的线程），
 * 直接你的机器能支撑的线程数的极限为止。
 * <p>
 * SynchronousQueue：一个不存储元素的队列。每一个put操作必须等待一个take操作，否则不能继续添加元素。
 */
public class CachePool {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println("初始线程池状态：" + service);

        for (int i = 0; i < 12; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println("线程提交完毕之后线程池状态：" + service);

        TimeUnit.SECONDS.sleep(50);
        System.out.println("50秒后线程池状态：" + service);

        TimeUnit.SECONDS.sleep(30);
        System.out.println("80秒后线程池状态：" + service);
    }

}