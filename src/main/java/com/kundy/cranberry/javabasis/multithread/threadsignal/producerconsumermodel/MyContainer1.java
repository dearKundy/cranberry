package com.kundy.cranberry.javabasis.multithread.threadsignal.producerconsumermodel;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * <p>
 * 使用wait和notify/notifyAll来实现
 * <p>
 * <p>
 * 为什么要用while不用if？
 * 首先记住wait在99%的情景下都是搭配while使用的，当上面条件成立的时候，锁释放了，如果是if判断直接往下走，
 * 那么当list.add()执行之前，其他线程也进来执行了list.add()，那么就出问题了。而while则会重新再判断一遍。
 * <p>
 * <p>
 * 为什么一定要调notifyAll？
 * 假如在put方法中，队列满了，然后调用notify方法，很有可能这时候唤醒的还是生产者线程，这个时候整个程序就卡死不动了。
 * 而notifyAll是唤醒所有的等待的线程就不会出现这个问题。
 */
public class MyContainer1<T> {

    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t) {
        // 为什么用while而不是用if？
        while (list.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.add(t);
        ++count;
        // 通知消费者线程进行消费
        this.notifyAll();
    }

    public synchronized T get() {
        T t;
        while (list.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = list.removeFirst();
        count--;
        //通知生产者进行生产
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        MyContainer1<String> container1 = new MyContainer1<>();
        // 启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(container1.get());
                }
            });
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    container1.put(Thread.currentThread().getName() + " " + j);
                }
            }, "p" + i);
        }
    }

}