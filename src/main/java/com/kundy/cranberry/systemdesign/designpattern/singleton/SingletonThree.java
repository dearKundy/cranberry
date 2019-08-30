package com.kundy.cranberry.systemdesign.designpattern.singleton;

/**
 * 双重检验锁
 *
 * @author kundy
 * @date 2019/6/3 11:04 AM
 */
public class SingletonThree {

    private static SingletonThree singletonOne;

    private SingletonThree() {

    }

    public static SingletonThree getSingleton() {
        if (singletonOne == null) {
            // 第一次需要实例化的时候才加锁
            synchronized (SingletonOne.class) {
                // 虽然加了锁，依然还是会有很多线程进入到该位置，所以我们还需要进行一次判空操作
                if (singletonOne == null) {
                    singletonOne = new SingletonThree();
                }
            }
        }
        return singletonOne;
    }

}
