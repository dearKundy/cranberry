package com.kundy.cranberry.javabasis.multithread.lock.reentrant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁的可重入性质【一个线程得到一个对象锁后再次请求该对象锁，是永远可以拿到锁的。】
 *
 * @author kundy
 * @date 2020/7/22 11:06 AM
 */
public class ReentrantObj {

    private final Lock lock = new ReentrantLock();

    public synchronized void method1() {
        System.out.println("进入method1");

        this.method2();

        System.out.println("退出method1");
    }

    private synchronized void method2() {
        System.out.println("进入method2");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("退出method2");
    }

    public void method3() {

        lock.lock();

        try {
            System.out.println("进入method3");
            this.method4();
            System.out.println("退出method3");
        } finally {
            lock.unlock();
        }
    }

    private void method4() {

        lock.lock();

        try {
            System.out.println("进入method4");
        } finally {
            lock.unlock();
        }
    }

}
