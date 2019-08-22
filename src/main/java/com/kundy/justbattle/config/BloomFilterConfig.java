package com.kundy.justbattle.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.kundy.justbattle.service.JbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @author kundy
 * @date 2019/8/22 5:29 PM
 */
@Slf4j
@Configuration
public class BloomFilterConfig {

    @Autowired
    private JbUserService userService;

    // 允许误差率
    private static final double FPP = 0.01;

    /**
     * 用户黑名单专用布隆过滤器
     */
    @Bean
    public BloomFilter<Integer> userBlackBloomFilter() {
        log.info("userBlackBloomFilter start initialization.");

        long start = System.currentTimeMillis();
        List<Integer> userIds = this.userService.listBlackUserId();
        log.info("userBlackBloomFilter initialization , Query DB cost time：{} ms.", (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), userIds.size(), FPP);
        for (Integer userId : userIds) {
            filter.put(userId);
        }
        log.info("userBlackBloomFilter initialization , Add element cost time：{} ms.", (System.currentTimeMillis() - start));

        return filter;
    }

}
