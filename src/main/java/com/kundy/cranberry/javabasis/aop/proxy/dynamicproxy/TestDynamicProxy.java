package com.kundy.cranberry.javabasis.aop.proxy.dynamicproxy;

import com.kundy.cranberry.javabasis.aop.proxy.IUserDao;
import com.kundy.cranberry.javabasis.aop.proxy.UserDao;

/**
 * 动态代理：
 * 动态代理利用了 JDK API，动态地在内存中构建代理对象，从而实现对目标对象的代理功能。动态代理又称为 JDK代理或接口代理。
 * <p>
 * 静态代理与动态代理的区别主要在：
 * - 静态代理在编译时就已经实现，编译完成后代理类是一个实际的class文件。
 * - 动态代理是在运行时动态生成的，即编译完成后没有实际的class文件，而是在运行时动态生成类字节码，并加载到 JVM 中。
 * <p>
 * 特点：动态代理对象不需要实现接口，但是要求目标对象必须实现接口，否则不能使用动态代理。
 * <p>
 * JDK主要使用 java.lang.reflect.Proxy#newProxyInstance(java.lang.ClassLoader, java.lang.Class[], java.lang.reflect.InvocationHandler) 来生成代理对象，InvocationHandler 定制方法执行过程；
 *
 * @author kundy
 * @date 2019/9/21 7:41 PM
 */
public class TestDynamicProxy {

    public static void main(String[] args) {
        IUserDao target = new UserDao();
        System.out.println(target.getClass());

        // 通过 JDK proxy 动态生成 IUserDao 实现类
        IUserDao proxy = (IUserDao) new JdkProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());

        proxy.save();
    }

}
