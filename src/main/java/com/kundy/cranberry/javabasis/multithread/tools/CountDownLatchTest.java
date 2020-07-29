package com.kundy.cranberry.javabasis.multithread.tools;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch：一个任务需要等待其他 N 个任务完成才能执行。
 *
 * @author kundy
 * @date 2019/8/30 9:53 PM
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
                // latch -1
                latch.countDown();
            }).start();
        }

        // 阻塞主线程
        latch.await();
        System.out.println("3个子线程执行完毕，主线程继续执行");

    }

}
