package com.kundy.cranberry.javabasis.aop.proxy.staticproxy;

import com.kundy.cranberry.javabasis.aop.proxy.IUserDao;

/**
 * 静态代理对象：需要实现IUserDao接口
 *
 * @author kundy
 * @date 2019/9/21 7:00 PM
 */
public class UserDaoProxy implements IUserDao {

    private IUserDao target;

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }

    @Override
    public void save() {
        System.out.println("开启事务");
        target.save();
        System.out.println("提交事务");
    }
}
