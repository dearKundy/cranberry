package com.kundy.cranberry.javabasis.multithread.create;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author kundy
 * @date 2020/7/14 10:00 PM
 */
public class HowCreateAThread {

    public static void main(String[] args) throws Exception {

        // way1:继承Thread类，并复写run方法，创建该类对象，调用start方法开启线程。
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("第1种方式:new Thread 1");
            }
        };
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        // way2:实现Runnable接口，复写run方法，创建Thread类对象，将Runnable子类对象传递给Thread类对象。调用start方法开启线程。
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("第2种方式:new Thread 2");
            }
        });
        thread2.start();
        TimeUnit.SECONDS.sleep(1);

        // way3:创建FutureTask对象，创建Callable子类对象，复写call(相当于run)方法，将其传递给FutureTask对象（相当于一个Runnable）。 创建Thread类对象，将FutureTask对象传递给Thread对象。调用start方法开启线程。
        // FutureTask 重写了 Runnable 的 run 方法，run 方法实际调用的是 callable 的 call 方法
        FutureTask<String> futureTask1 = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "第3种方式:new Thread 3";
            }
        });
        Thread thread3 = new Thread(futureTask1);
        thread3.start();

        // 阻塞直到拿到结果
        String result = futureTask1.get();
        System.out.println(result);

        // --------------------- 创建多个 FutureTask 任务，并按顺序取回结果 ---------------------
        List<FutureTask<String>> futureTasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return Thread.currentThread().getName();
                }
            });

            // 启动线程
            new Thread(futureTask).start();

            futureTasks.add(futureTask);
        }

        futureTasks.forEach(futureTask -> {

            try {
                System.out.println(futureTask.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
