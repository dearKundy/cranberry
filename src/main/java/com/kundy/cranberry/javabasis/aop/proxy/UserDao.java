package com.kundy.cranberry.javabasis.aop.proxy;

import com.kundy.cranberry.javabasis.aop.proxy.IUserDao;

/**
 * 目标对象
 *
 * @author kundy
 * @date 2019/9/21 5:43 PM
 */
public class UserDao implements IUserDao {

    @Override
    public void save() {
        System.out.println("保存数据");
    }

    public void hey(){
        System.out.println("hey");
    }
}
