package com.kundy.cranberry.systemdesign.designpattern.singleton;

/**
 * 懒汉式，线程不安全
 *
 * @author kundy
 * @date 2019/6/3 11:04 AM
 */
public class SingletonOne {

    /**
     * 这里必须使用 静态变量，所有实例共享一个静态变量
     */
    private static SingletonOne singletonOne;

    private SingletonOne() {

    }

    public static SingletonOne getSingleton() {
        if (singletonOne == null) {
            singletonOne = new SingletonOne();
        }
        return singletonOne;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(System.identityHashCode(SingletonOne.getSingleton()));
            }).start();
        }
    }

}
