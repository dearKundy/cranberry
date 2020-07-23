package com.kundy.cranberry.javabasis.multithread.threadsignal;

/**
 * @author kundy
 * @date 2020/7/21 3:31 PM
 */
public class WaitAndNotifyAndNotifyAllMain {

    //TODO 为啥wait、notify、notifyAll 只能用在同步方法，或者同步代码块中通过锁对象调用
    public static void main(String[] args) {

        WaitAndNotifyAndNotifyAll waitAndNotifyAndNotifyAll = new WaitAndNotifyAndNotifyAll();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                // 5个线程都被阻塞，等待被唤醒
                waitAndNotifyAndNotifyAll.add(Thread.currentThread().getName());
            }).start();
        }

        // 随机唤醒一个在等待池中的线程
        waitAndNotifyAndNotifyAll.subAndNotify(Thread.currentThread().getName());

        // 唤醒在等待池中的所有线程
//        waitAndNotifyAndNotifyAll.subAndNotifyAll(Thread.currentThread().getName());

    }

}
