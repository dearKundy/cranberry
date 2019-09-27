package com.kundy.cranberry.javabasis.aop.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * 动态代理对象
 *
 * @author kundy
 * @date 2019/9/21 9:01 PM
 */
public class JdkProxyFactory {

    /**
     * 维护一个目标对象
     */
    private Object target;

    public JdkProxyFactory(Object target) {
        this.target = target;
    }

    /**
     * 为目标对象生成代理对象
     */
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (proxy, method, args) -> {

            System.out.println("开启事务");

            // 执行目标对象方法
            Object returnValue = method.invoke(target, args);

            System.out.println("提交事务");

            return returnValue;
        });
    }
}
