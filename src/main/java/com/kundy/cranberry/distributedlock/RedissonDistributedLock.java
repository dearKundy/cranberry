package com.kundy.cranberry.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redisson 是 Redis 的一个工具类，里面提供了很多使用工具，其中就包括了分布式锁
 * <p>
 * RedLock：Redlock 是 redis 官方提出的实现分布式锁管理器的算法。
 * <p>
 * Redisson 的分布式锁就是 RedLock 算法的一种实现
 * <p>
 * 下面展示一下 Redisson 分布式锁的简单用法【将 redisson-spring-boot-starter 导入，开箱即用，方便的很】
 *
 * @author kundy
 * @date 2019/8/20 7:41 PM
 */
@Slf4j
@Service
public class RedissonDistributedLock {

    @Autowired
    private RedissonClient redissonClient;

    public void testRedisson() {
        RLock lock = redissonClient.getLock("anyLock");
        try {
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (res) {
                log.info("获取锁成功");
                lock.unlock();
            } else {
                log.info("获取锁失败");
            }
        } catch (Exception e) {
            lock.unlock();
        }

    }

}
