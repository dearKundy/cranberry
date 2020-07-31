package com.kundy.cranberry.javabasis.multithread.threadpool.usage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 从头到尾整个线程池都只有一个线程在工作
 */
public class SingleThreadPool {

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            final int j = i;
            service.execute(() -> {
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }
    }

}