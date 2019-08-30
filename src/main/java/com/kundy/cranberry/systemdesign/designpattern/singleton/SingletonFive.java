package com.kundy.cranberry.systemdesign.designpattern.singleton;

/**
 * 饿汉式static final field
 *
 * @author kundy
 * @date 2019/6/3 1:57 PM
 * <p>
 * 因为单例的实例被声明成 static 和 final 变量了，在第一次加载类到内存中时就会初始化，所以创建实例本身是线程安全的。
 */
public class SingletonFive {

    /**
     * 类加载时就初始化
     */
    private static final SingletonFive INSTANCE = new SingletonFive();

    private SingletonFive() {

    }

    public static SingletonFive getInstance() {
        return INSTANCE;
    }

}
