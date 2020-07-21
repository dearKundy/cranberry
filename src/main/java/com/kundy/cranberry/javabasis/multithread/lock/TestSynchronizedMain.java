package com.kundy.cranberry.javabasis.multithread.lock;

/**
 * synchronized 测试类
 *
 * 在使用层面上，锁是加在对象上的，搞清楚当前的锁对象是哪个就可以了。
 *
 * @author kundy
 * @date 2020/7/21 5:41 PM
 */
public class TestSynchronizedMain {

    public static void main(String[] args) {
        testTwo();
    }

    /**
     * 普通同步方法测试
     */
    private static void testOne() {

        SynchronizedThread syncThread = new SynchronizedThread("同步测试对象1");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                syncThread.add();
            }).start();
        }

        SynchronizedThread syncThread1 = new SynchronizedThread("同步测试对象2");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                syncThread1.add();
            }).start();
        }
    }

    /**
     * 静态同步方法测试
     */
    private static void testTwo() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SynchronizedThread.addV2();
            }).start();
        }
    }

    /**
     * 测试同步方法块
     */
    private static void testThree() {

    }
}
