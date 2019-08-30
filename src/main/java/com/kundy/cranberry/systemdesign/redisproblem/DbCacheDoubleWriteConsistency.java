package com.kundy.cranberry.systemdesign.redisproblem;

import com.kundy.cranberry.constant.RedisKey;
import com.kundy.cranberry.model.po.CbGoodsPo;
import com.kundy.cranberry.service.CbGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 数据库与缓存双写一致性问题
 *
 * @author kundy
 * @date 2019/8/21 9:37 PM
 */
@Slf4j
@Service
public class DbCacheDoubleWriteConsistency {

    @Autowired
    private CbGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 方案一：先更新数据库，在更新缓存
     * <p>
     * bug：缓存中的为过期数据。
     * 1. 线程A 更新了数据库
     * 2. 线程B 更新了数据库
     * 3. 线程B 更新了缓存
     * 4. 线程A 更新了缓存
     */
    public boolean updateWithBugVersionOne(CbGoodsPo goodsPo, Integer sleepTime) {
        if (!this.goodsService.update(goodsPo)) {
            log.warn("更新数据库失败");
            return false;
        }

        // 在这里睡两秒，线程安全问题暴露
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!this.redisTemplate.opsForValue().setIfPresent(RedisKey.JB_GOODS + goodsPo.getId(), goodsPo)) {
            log.warn("更新缓存失败");
            return false;
        }
        return true;
    }

    /**
     * 方案二：先删除缓存，在更新数据库
     * <p>
     * bug：如果不采用给缓存设置过期时间策略，该数据永远都是脏数据。
     * 1. 线程A 进行写操作，删除缓存
     * 2. 线程B 查询发现缓存不存在
     * 3. 线程B 去数据库查询得到旧值
     * 4. 线程B 将旧值写入缓存
     * 5. 线程A 将新值写入数据库
     */
    public boolean updateWithBugVersionTwo(CbGoodsPo goodsPo, Integer sleepTime) {
        if (!this.redisTemplate.delete(RedisKey.JB_GOODS + goodsPo.getId())) {
            log.warn("删除缓存失败");
            return false;
        }

        // 在这里睡两秒，线程安全问题暴露
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!this.goodsService.update(goodsPo)) {
            log.warn("更新数据库失败");
            return false;
        }

        return true;
    }

    /**
     * 方案三：先更新数据库，再删除缓存
     * <p>
     * 硬要抬杠的话，这也是会有bug的，但是出现的概率极低，有时候为了解决概率极低的问题，性价比不高。
     * 【按道理讲，先更新数据库，因为缓存还没删除，所以查询肯定全部走缓存的，但是恰巧这时候缓存失效，需要走数据库（走数据自然也要写入缓存），就有可能出现问题】
     * 1. 缓存刚好失效
     * 2. 请求A查询数据库，得一个旧值
     * 3. 请求B将新值写入数据库
     * 4. 请求B删除缓存
     * 5. 请求A将查到的旧值写入缓存
     */
    public boolean updateWayThree(CbGoodsPo goodsPo) {

        if (!this.goodsService.update(goodsPo)) {
            log.warn("更新数据库失败");
            return false;
        }

        if (!this.redisTemplate.delete(RedisKey.JB_GOODS + goodsPo.getId())) {
            log.warn("删除缓存失败");
            return false;
        }

        return true;
    }

}
