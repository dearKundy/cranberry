package com.kundy.cranberry.thirdparty.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

/**
 * @author kundy
 * @date 2019/10/29 9:37 PM
 */
@Component
public class RequestHandledEventListener implements ApplicationListener<RequestHandledEvent> {

    @Override
    public void onApplicationEvent(RequestHandledEvent requestHandledEvent) {
        System.out.println("RequestHandledEvent...");
    }
}
