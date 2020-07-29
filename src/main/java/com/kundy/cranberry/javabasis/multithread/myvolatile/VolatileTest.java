package com.kundy.cranberry.javabasis.multithread.myvolatile;

/**
 * volatile关键字，使一个变量在多个线程间可见，A B线程都用到一个变量，java默认是A线程中保留一份copy（线程A会把该变量copy到自己的缓冲区中，不会再在主内存中读取变量），
 * 这样如果B线程修改了该变量，则A线程未必知道。使用volatile关键字，会让所有线程都会读取变量的修改值（如果变量发生变化，会通知线程A，变量已过期，需要重新读取）
 *
 * @author kundy
 * @date 2020/7/25 10:37 AM
 */
public class VolatileTest {

    public Boolean running = true;
//  public  volatile Boolean running = true;

    void m() {
        System.out.println("m start");
        while (running) {

        }
        System.out.println("m end");
    }

    public static void main(String[] args) {

        VolatileTest volatileTest = new VolatileTest();

        new Thread(volatileTest::m, "t1").start();

        try {
            // 一定要睡一下，否则主线程运行太快，线程t1还没开始，值就设置完成了，无法体现出效果
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        volatileTest.running = false;

        System.out.println("设置成功");
    }


}
