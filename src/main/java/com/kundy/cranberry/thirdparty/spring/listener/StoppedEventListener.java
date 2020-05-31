package com.kundy.cranberry.thirdparty.spring.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @author kundy
 * @date 2019/10/29 9:35 PM
 */
@Component
public class StoppedEventListener implements ApplicationListener<ContextStoppedEvent> {

    @Override
    public void onApplicationEvent(ContextStoppedEvent contextStoppedEvent) {
        System.out.println("ContextStoppedEvent");
    }
}
