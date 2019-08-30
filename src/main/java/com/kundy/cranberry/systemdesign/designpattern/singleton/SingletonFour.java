package com.kundy.cranberry.systemdesign.designpattern.singleton;

/**
 * 双重检验锁
 *
 * @author kundy
 * @date 2019/6/3 11:04 AM
 */
public class SingletonFour {

    /**
     * new SingletonFour() 并非原子操作，事实上在JVM中这句话做了下面3件事情：
     * 1. 给 instance 分配内存
     * 2. 调用 Singleton 的构造函数来初始化成员变量
     * 3. 将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
     * <p>
     * 但是在 JVM 的即时编译器中存在指令重排序的优化。也就是说上面的第二步和第三步的顺序是不能保证的，
     * 如果在 3 执行完毕、2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），
     * 所以线程二会直接返回 instance，然后使用，然后顺理成章地报错。
     * <p>
     * 解决方法：
     * 我们只需要将 singletonFour 变量声明成 volatile 就可以了。
     */
    private volatile static SingletonFour singletonFour;

    private SingletonFour() {

    }

    public static SingletonFour getSingleton() {
        if (singletonFour == null) {
            // 第一次需要实例化的时候才加锁
            synchronized (SingletonOne.class) {
                // 虽然加了锁，依然还是会有很多线程进入到该位置，所以我们还需要进行一次判空操作
                if (singletonFour == null) {
                    singletonFour = new SingletonFour();
                }
            }
        }
        return singletonFour;
    }

}
