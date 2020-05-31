package com.kundy.cranberry.thirdparty.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author kundy
 * @date 2019/10/27 1:42 PM
 */
@Service
public class SpringBeanLifeCycle implements InitializingBean, DisposableBean {

    private String msg;

    public SpringBeanLifeCycle() {
        System.out.println("SpringBeanLifeCycle 无参构造函数");
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void sayHi() {
        System.out.println("hi from SpringBeanLifeCycle");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("bean 初始化完成（调用完构造函数）之后，调用该方法。");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Spring 容器被销毁的时候调用该方法。");
    }
}
