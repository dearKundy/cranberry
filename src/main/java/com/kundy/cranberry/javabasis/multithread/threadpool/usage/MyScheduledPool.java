package com.kundy.cranberry.javabasis.multithread.threadpool.usage;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 可以在指定延迟后或周期性地执行线程任务的线程池。
 * <p>
 * 通过使用 DelayedWorkQueue 存储任务
 */
public class MyScheduledPool {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + new Random().nextInt(1000));
        }, 0, 500, TimeUnit.MILLISECONDS);
    }
}