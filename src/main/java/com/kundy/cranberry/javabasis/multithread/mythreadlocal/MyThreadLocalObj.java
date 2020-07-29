package com.kundy.cranberry.javabasis.multithread.mythreadlocal;

/**
 * 每个线程都会有一份自己独有的 ThreadLocal 变量
 * <p>
 * Q：ThreadLocal 与 局部变量有什么区别呢？
 * A：从安全角度，他们都是线程私有的变量。从使用角度，ThreadLocal（声明为public 的全局变量）可以被其他对象或者本类的所有方法调用。但是局部变量就只能是本方法自己使用了。
 *
 * @author kundy
 * @date 2020/7/26 10:27 AM
 */
public class MyThreadLocalObj {

    private ThreadLocal<String> threadLocal;
//    private String threadLocal;


    public void method() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "的threadLocal" + ",设置为" + name);
        this.threadLocal.set(name);
//        this.threadLocal = name;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println(name + ":" + threadLocal.get());
//        System.out.println(name + ":" + threadLocal);
    }

}
