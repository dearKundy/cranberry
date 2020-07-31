package com.kundy.cranberry.javabasis.multithread.threadpool.usage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池中的线程数目是固定的，不管你来了多少的任务。
 */
public class MyFixThreadPool {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个线程数固定为5的线程池【并不是直接创建5个线程，而是submit1个任务，才会新建一个线程，懒加载】
        ExecutorService service = Executors.newFixedThreadPool(5);

        System.out.println("初始线程池状态：" + service);

        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println("线程提交完毕之后线程池状态：" + service);

        //会等待所有的线程执行完毕才关闭，shutdownNow：立马关闭
        service.shutdown();
        //所有的任务执行完了，就会返回true
        System.out.println("是否全部线程已经执行完毕：" + service.isTerminated());
        System.out.println("是否已经执行shutdown()" + service.isShutdown());
        System.out.println("执行完shutdown()之后线程池的状态：" + service);

        TimeUnit.SECONDS.sleep(5);
        System.out.println("5秒钟过后，是否全部线程已经执行完毕：" + service.isTerminated());
        System.out.println("5秒钟过后，是否已经执行shutdown()" + service.isShutdown());
        System.out.println("5秒钟过后，线程池状态：" + service);
    }

}