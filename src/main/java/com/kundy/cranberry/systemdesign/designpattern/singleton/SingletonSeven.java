package com.kundy.cranberry.systemdesign.designpattern.singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 不使用 Synchronized 和 lock 实现 单例 【使用 CAS 实现】
 * <p>
 * - 通过静态成员变量实现单例模式：因为类的初始化是由 `ClassLoader` 完成的，这其实就是利用了 `ClassLoader` 的线程安全机制。
 * - ClassLoader 的 loadClass 方法在加载类的时候使用了 synchronized 关键字。
 *
 * @author kundy
 * @date 2019/9/28 5:27 PM
 */
public class SingletonSeven {

    private static final AtomicReference<SingletonSeven> INSTANCE = new AtomicReference<>();

    private SingletonSeven() {
    }

    public static SingletonSeven getInstance() {
        for (; ; ) {
            SingletonSeven singletonSeven = INSTANCE.get();
            if (null != singletonSeven) {
                return singletonSeven;
            }

            singletonSeven = new SingletonSeven();
            if (INSTANCE.compareAndSet(null, singletonSeven)) {
                return singletonSeven;
            }
        }
    }

}
