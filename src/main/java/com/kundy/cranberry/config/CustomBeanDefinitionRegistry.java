package com.kundy.cranberry.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.kundy.cranberry.deduplication.DeduplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author kundy
 * @date 2019/8/23 9:39 PM
 */
@Slf4j
@Component
public class CustomBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        log.info("postProcessBeanFactory......");
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BloomFilter.class, () -> {
                    return BloomFilter.create(Funnels.integerFunnel(), 10, 0.01);
                }
        );
        BeanDefinition bloomFilterDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        ((DefaultListableBeanFactory) configurableListableBeanFactory).registerBeanDefinition("testBloomFilter", bloomFilterDefinition);
    }
}
