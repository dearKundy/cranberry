package com.kundy.cranberry.javabasis.multithread.lock.fair;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kundy
 * @date 2020/7/23 10:36 PM
 */
public class FairOrNoFairLock {

    /**
     * 公平锁
     */
    private final Lock queueLock = new ReentrantLock(false);

    public static void main(String[] args) throws Exception {

        FairOrNoFairLock fairOrNoFairLock = new FairOrNoFairLock();


        for (int i = 1; i <= 10; i++) {

            new Thread(() -> {
                fairOrNoFairLock.printJob();
            }).start();

//            Thread.sleep(100);
        }
    }

    private void printJob() {
        queueLock.lock();
        try {
            long duration = (long) (Math.random() * 10000);
            System.out.printf("%s: Print a Job during %d\n",
                    Thread.currentThread().getName(), duration / 1000);
//            Thread.sleep(duration);
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        finally {
            queueLock.unlock();
        }
    }
}
