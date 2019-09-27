package com.kundy.cranberry.javabasis.aop.proxy.cglibproxy;

import com.kundy.cranberry.javabasis.aop.proxy.UserDao;

/**
 * cglib 代理：cglib is a powerful, high performance and quality Code Generation Library. It can extend JAVA classes and implement interfaces at runtime.
 * <p>
 * - cglib 是一个第三方代码生成类库，运行时在内存中动态生成一个子类对象从而实现对目标对象功能的扩展。
 * - cglib 包的底层是通过使用一个小而快的字节码处理框架ASM，来转换字节码并生成新的类。
 * <p>
 * cglib 与 JDK动态代理最大的区别就是：
 * 使用动态代理的对象必须实现一个或多个接口;
 * 使用cglib代理的对象则无需实现接口，达到代理类无侵入;
 *
 * @author kundy
 * @date 2019/9/22 10:21 AM
 */
public class CglibTest {

    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();
        System.out.println(target.getClass());
        //代理对象
        UserDao proxy = (UserDao) new CglibProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());
        //执行代理对象方法
        proxy.save();
    }

}
