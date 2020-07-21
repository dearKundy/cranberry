package com.kundy.cranberry.javabasis.multithread.lock;

/**
 * @author kundy
 * @date 2020/7/21 5:39 PM
 */
public class SynchronizedThread {

    private static final Object LOCK = new Object();

    private String name;

    public SynchronizedThread(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 对于普通方法的同步，锁是当前实例对象
     */
    public synchronized void add() {
        System.out.println(this.getName() + "进入SyncThread#add" + System.currentTimeMillis());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(this.getName() + "结束SyncThread#add" + System.currentTimeMillis());
    }

    /**
     * 对于静态方法的同步，锁是当前类的Class对象
     */
    public synchronized static void addV2() {
        System.out.println("进入SyncThread#addV2" + System.currentTimeMillis());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println( "结束SyncThread#addV2" + System.currentTimeMillis());
    }

    /**
     * 对于同步方法块，锁是sychronized括号里配置的对象
     */
    public void addV3() {
        synchronized (LOCK) {
            System.out.println("进入SyncThread#addV3" + System.currentTimeMillis());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("结束SyncThread#addV3" + System.currentTimeMillis());
        }
    }

}
