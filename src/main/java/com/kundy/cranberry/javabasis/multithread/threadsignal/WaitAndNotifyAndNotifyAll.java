package com.kundy.cranberry.javabasis.multithread.threadsignal;

/**
 * @author kundy
 * @date 2020/7/21 3:31 PM
 */
public class WaitAndNotifyAndNotifyAll {

    public synchronized void add(String name) {
        System.out.println(name + "进入add");

        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + "退出add");

    }

    public synchronized void subAndNotify(String name) {
        System.out.println(name + "进入subAndNotify");
        this.notify();
        System.out.println(name + "退出subAndNotify");
    }

    public synchronized void subAndNotifyAll(String name) {
        System.out.println(name + "进入subAndNotifyAll");
        this.notifyAll();
        System.out.println(name + "退出subAndNotifyAll");
    }

}
