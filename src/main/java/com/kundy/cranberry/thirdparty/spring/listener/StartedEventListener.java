package com.kundy.cranberry.thirdparty.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * @author kundy
 * @date 2019/10/29 9:33 PM
 */
@Component
public class StartedEventListener implements ApplicationListener<ContextStartedEvent> {

    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        System.out.println("ContextStartedEvent.....");
    }
}
