package com.kundy.cranberry.systemdesign.designpattern.singleton;

/**
 * 懒汉式，暴力线程安全
 *
 * @author kundy
 * @date 2019/6/3 11:04 AM
 */
public class SingletonTwo {

    private static SingletonTwo singletonOne;

    private SingletonTwo() {

    }

    public static synchronized SingletonTwo getSingleton() {
        if (singletonOne == null) {
            singletonOne = new SingletonTwo();
        }
        return singletonOne;
    }

}
