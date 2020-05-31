package com.kundy.cranberry.thirdparty.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author kundy
 * @date 2019/10/29 9:34 PM
 */
@Component
public class RefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("ContextRefreshedEvent...");
    }
}
