package com.kundy.cranberry.javabasis.multithread.mythreadlocal;

/**
 * @author kundy
 * @date 2020/7/25 7:51 PM
 */
public class ThreadLocalTest {

    private static ThreadLocal<String> threadLocal;

    public static void main(String[] args) {

        MyThreadLocalObj myThreadLocalObj = new MyThreadLocalObj();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                myThreadLocalObj.method();
            }).start();
        }

    }


}
