package com.kundy.cranberry.ratelimiter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 分布式限流 - 基于 redis 实现
 *
 * @author kundy
 * @date 2019/8/19 10:26 PM
 */
@Service
public class RedisRateLimiter {

    // TODO 使用 AOP + 注解 对使用方式进行优化

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean tryAcquire(String key, Integer perSecondRate) {
        String counter = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isEmpty(counter)) {
            stringRedisTemplate.boundValueOps(key).increment(1);
            stringRedisTemplate.boundValueOps(key).expire(1, TimeUnit.SECONDS);
            return true;
        }
        if (Long.valueOf(counter) >= perSecondRate) {
            return false;
        }
        stringRedisTemplate.boundValueOps(key).increment(1);
        return true;
    }

}
