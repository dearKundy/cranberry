package com.kundy.cranberry.javabasis.multithread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kundy
 * @date 2020/7/21 6:43 PM
 */
public class ReentrantLockThread {

    private final Lock lock = new ReentrantLock();

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReentrantLockThread(String name) {
        this.name = name;
    }

    /**
     * 锁效果相当于加了 synchronized 的实例方法
     */
    public void add() {

        lock.lock();
        try {

            System.out.println(this.getName() + "成功获取锁" + System.currentTimeMillis());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(this.getName() + "任务完成" + System.currentTimeMillis());

        } finally {
            lock.unlock();
        }

    }

}
