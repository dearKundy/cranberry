package com.kundy.cranberry.javabasis.multithread.control;

/**
 * @author kundy
 * @date 2020/7/21 2:44 PM
 */
public class JoinTest {

    public static void main(String[] args) throws Exception{

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        // 阻塞主线程【调用线程】，直到 thread 完成
        thread.join();

        thread2.start();

        System.out.println("主线程结束");


    }

}
