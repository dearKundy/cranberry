package com.kundy.cranberry.listener;

import com.google.common.hash.BloomFilter;
import com.kundy.cranberry.config.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author kundy
 * @date 2019/8/25 1:16 PM
 */
@Component
public class TestApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private ApplicationContextProvider contextProvider;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        BloomFilter testBloomFilter = contextProvider.getApplicationContext().getBean("testBloomFilter", BloomFilter.class);
        System.out.println("application started。。。。");
        System.out.println("contextProvider" + System.identityHashCode(contextProvider));
        System.out.println("testBloomFilter" + System.identityHashCode(testBloomFilter));
        testBloomFilter.put(1);
        testBloomFilter.put(2);
        testBloomFilter.put(3);
        testBloomFilter.put(4);
        testBloomFilter.put(99);
    }
}
