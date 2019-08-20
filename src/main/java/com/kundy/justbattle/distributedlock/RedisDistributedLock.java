package com.kundy.justbattle.distributedlock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁 - 基于 Redis 实现
 * <p>
 * 思路：
 * 通过 setNx 实现。
 * <p>
 * 优点：
 * 性能好，实现起来方便。
 * <p>
 * 缺点：
 * 通过超时时间来控制锁的失效时间并不是十分的靠谱。
 *
 * @author kundy
 * @date 2019/8/20 2:14 PM
 */
@Service
public class RedisDistributedLock {

    // TODO requestId 需要设计成程序自己生成，这个关键的参数由用户自己传入还是不太适合的。
    // TODO 设计成可重入，同样是把机器和线程信息保存下来，获取锁之前判断一下即可。

    private static final String LOCK_KEY_PREFIX = "DistributedLock:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean tryLock(String key, String requestId) {
        return this.tryLock(key, requestId, 5, TimeUnit.SECONDS);
    }

    /**
     * 尝试获得锁[非阻塞]
     *
     * @param key       锁定的资源名称
     * @param requestId 当前线程的唯一标识 【建议使用 UUID + threadId】
     * @param timeOut   经过 timeout 时间，会自动释放锁
     * @param timeUnit  timeOut 的时间单位
     * @return 尝试获得锁是否成功
     */
    public boolean tryLock(String key, String requestId, Integer timeOut, TimeUnit timeUnit) {

        // 设值并设置过期时间为 timeOut
        return stringRedisTemplate.opsForValue().setIfAbsent(LOCK_KEY_PREFIX + key, requestId, timeOut, timeUnit);

    }

    /**
     * 阻塞版
     */
    public boolean lockWithBlock(String key, String requestId, Integer timeOut, TimeUnit timeUnit) {
        Boolean flag = false;
        while (!flag) {
            flag = stringRedisTemplate.opsForValue().setIfAbsent(LOCK_KEY_PREFIX + key, requestId, timeOut, timeUnit);
        }
        return true;
    }

    /**
     * @param key       锁定的资源名称
     * @param requestId 当前线程的唯一标识 【需要保证与 tryLock 中传入的 requestId 一致】
     */
    public boolean unLock(String key, String requestId) {
        key = LOCK_KEY_PREFIX + key;
        if (StringUtils.isEmpty(requestId)) {
            throw new RuntimeException("requestId 不能为空");
        }
        String curRequestId = stringRedisTemplate.opsForValue().get(key);
        // 只能释放自己的锁
        if (requestId.equals(curRequestId)) {
            return stringRedisTemplate.delete(key);
        }
        return false;
    }

}
