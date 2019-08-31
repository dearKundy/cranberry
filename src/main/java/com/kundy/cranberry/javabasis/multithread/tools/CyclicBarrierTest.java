package com.kundy.cranberry.javabasis.multithread.tools;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier：
 * <p>
 * 栅栏：可以让一组线程在 await() 处等待至某个状态之后再全部同时执行（多线现在阻塞在 await() 处，当 await() 的线程数量到达一个指定数值之后，全部线程放行）
 * 循环：当所有等待线程都被释放以后，CyclicBarrier 可以被重用。
 *
 * @author kundy
 * @date 2019/8/30 9:54 PM
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        int count = 4;
        CyclicBarrier barrier = new CyclicBarrier(count);

        for (int i = 0; i < count; i++) {
            new Thread(new Writer(barrier)).start();
        }

        System.out.println("主线程正在执行。。。");

        Thread.sleep(5000);

        System.out.println("CyclicBarrier 重用");

        for (int i = 0; i < count; i++) {
            new Thread(new Writer(barrier)).start();
        }

    }

    static class Writer implements Runnable {

        private CyclicBarrier barrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.barrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "开始写入数据。。。");
            try {
                Thread.sleep(3000);
                // 阻塞，直到被阻塞的线程数达到4
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getName() + "放行。。。。");
        }
    }

}
