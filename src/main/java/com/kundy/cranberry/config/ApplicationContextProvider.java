package com.kundy.cranberry.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取 Spring 上下文对象
 * <p>
 * 当一个类实现ApplicationContextAware接口后，当这个类被spring加载后，
 * 就能够在这个类中获取到spring的上下文操作符ApplicationContext，
 * 通过ApplicationContext 就能够轻松的获取所有的spring管理的bean。
 *
 * @author kundy
 * @date 2019/8/23 8:34 PM
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

}
