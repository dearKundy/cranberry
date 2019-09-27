package com.kundy.cranberry.javabasis.aop.proxy.staticproxy;

import com.kundy.cranberry.javabasis.aop.proxy.IUserDao;
import com.kundy.cranberry.javabasis.aop.proxy.UserDao;

/**
 * 静态代理：我个人觉得其实静态代理跟装饰模式几乎是差不多的。
 * <p>
 * 优点：
 * 可以在不修改目标对象的前提下扩展目标对象的功能。
 * <p>
 * 缺点：
 * 1. 冗余，由于代理对象要实现与目标对象一致的接口，会产生过多的代理类。
 * 2. 不易维护，一旦接口增加方法，目标对象与代理对象都要进行修改。
 *
 * @author kundy
 * @date 2019/9/21 7:10 PM
 */
public class TestStaticProxy {

    public static void main(String[] args) {
        // 目标对象
        IUserDao target = new UserDao();
        // 代理对象
        UserDaoProxy proxy = new UserDaoProxy(target);
        proxy.save();
    }

}
