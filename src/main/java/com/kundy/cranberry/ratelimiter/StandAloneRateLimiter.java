package com.kundy.cranberry.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 单机限流 - 计数器算法实现
 * <p>
 * 1. 需要保证 StandAloneRateLimiter 单例，交给 Spring 管理就好。
 * 2. 每个限流器通过 raterName 区分。
 * 3. 新建一个限流器，调用 addRater 即可。
 * 4. 调用 tryAcquire 获取许可。
 *
 * @author kundy
 * @date 2019/8/19 10:21 PM
 */
public class StandAloneRateLimiter {

    private static final Integer ONE_SECOND = 1000;
    private Integer perSecondRate = 100;

    private ConcurrentHashMap<String, Long> timeMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, AtomicLong> counterMap = new ConcurrentHashMap<>();


    public StandAloneRateLimiter() {

    }

    public void addRater(String raterName, Integer perSecondRate) {
        this.perSecondRate = perSecondRate;
        Long startTime = System.currentTimeMillis();
        timeMap.put(raterName, startTime);
        counterMap.put(raterName, new AtomicLong(0));
    }

    /**
     * 获取许可
     */
    public boolean tryAcquire(String raterName) {
        if (!timeMap.containsKey(raterName)) {
            throw new RuntimeException("rater not exist");
        }
        // 超过 1 秒，重置
        Long startTime = timeMap.get(raterName);
        if (System.currentTimeMillis() - startTime > ONE_SECOND) {
            timeMap.put(raterName, System.currentTimeMillis());
            counterMap.put(raterName, new AtomicLong(0));
        }
        AtomicLong atomicLong = counterMap.get(raterName);
        Long counter = atomicLong.get();
        if (counter < perSecondRate) {
            atomicLong.incrementAndGet();
            return true;
        }
        return false;
    }

}
