package com.kundy.cranberry.javabasis.multithread.tools;

import java.util.concurrent.Semaphore;

/**
 * Semaphore：每次只允许N个线程同时运行
 *
 * @author kundy
 * @date 2019/8/30 9:54 PM
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        // 工人数
        int workerNum = 8;
        // 机器数目
        int machineNum = 5;
        Semaphore semaphore = new Semaphore(machineNum);
        for (int i = 0; i < workerNum; i++) {
            new Thread(new Worker(i, semaphore)).start();
        }
    }

    static class Worker implements Runnable {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人" + this.num + "占用一个机器在生产...");
                Thread.sleep(2000);
                semaphore.release();
                System.out.println("工人" + this.num + "释放出机器");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
