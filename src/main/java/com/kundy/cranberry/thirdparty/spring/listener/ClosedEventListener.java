package com.kundy.cranberry.thirdparty.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author kundy
 * @date 2019/10/29 9:36 PM
 */
@Component
public class ClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        System.out.println("ContextClosedEvent...");
    }
}
