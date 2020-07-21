package com.kundy.cranberry.javabasis.multithread.lock;

/**
 * @author kundy
 * @date 2020/7/21 6:55 PM
 */
public class TestReentrantLockMain {

    public static void main(String[] args) {

        ReentrantLockThread reentrantLockThread = new ReentrantLockThread("ReentrantLock测试对象1");

        ReentrantLockThread reentrantLockThread2= new ReentrantLockThread("ReentrantLock测试对象2");

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                reentrantLockThread.add();
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                reentrantLockThread2.add();
            }).start();
        }

    }

}
