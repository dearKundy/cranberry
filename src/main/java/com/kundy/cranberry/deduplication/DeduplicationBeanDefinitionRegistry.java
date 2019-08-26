package com.kundy.cranberry.deduplication;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.kundy.cranberry.constant.DeduplicationBloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 动态向容器中注册 布隆过滤器
 *
 * @author kundy
 * @date 2019/8/25 3:35 PM
 */
@Slf4j
@Component
public class DeduplicationBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    private static final Integer DEFAULT_EXPECTED_INSERTIONS = 10000000;
    private static final Double FPP = 0.01;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("Dynamically register bloomFilter into IOC");
        List<String> bloomList = DeduplicationProperties.getBloomList();
        if (bloomList == null) {
            return;
        }
        for (String s : bloomList) {
            this.registerBloomFilterBean(beanFactory, s);
            // 初始化布隆过滤器的初始化状态
            DeduplicationBloomFilter.INIT_STATUS.put(s, false);
        }
        log.info("Successfully registered {} bloomFilter into IOC!", bloomList.size());
    }

    /**
     * 将名为 beanId 的布隆过滤器动态注入到 IOC 容器中
     */
    private void registerBloomFilterBean(ConfigurableListableBeanFactory beanFactory, String beanId) {
        Integer expectedInsertions = DEFAULT_EXPECTED_INSERTIONS;
        Map<String, Object> expectedInsertionsMap = DeduplicationProperties.getExpectedInsertions();
        if (expectedInsertionsMap != null && expectedInsertionsMap.containsKey(beanId)) {
            expectedInsertions = Integer.valueOf((String) expectedInsertionsMap.get(beanId));
        }
        Integer finalExpectedInsertions = expectedInsertions;
        log.info("beanId={} expectedInsertions={}", beanId, finalExpectedInsertions);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BloomFilter.class, () -> {
                    return BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), finalExpectedInsertions, FPP);
                }
        );
        BeanDefinition bloomFilterDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanId, bloomFilterDefinition);
    }


}
