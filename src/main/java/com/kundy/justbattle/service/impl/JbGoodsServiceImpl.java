package com.kundy.justbattle.service.impl;

import com.kundy.justbattle.constant.RedisKey;
import com.kundy.justbattle.mapper.JbGoodsMapper;
import com.kundy.justbattle.model.po.JbGoodsPo;
import com.kundy.justbattle.service.JbGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author kundy
 * @date 2019/8/18 10:59 PM
 */
@Slf4j
@Service
public class JbGoodsServiceImpl implements JbGoodsService {

    @Autowired
    private JbGoodsMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public JbGoodsPo list(Integer id) {
        return this.mapper.list(id);
    }

    @Override
    public JbGoodsPo listInCache(Integer id) {
        
        String redisKey = RedisKey.JB_GOODS + id;
        ValueOperations valueOperations = this.redisTemplate.opsForValue();

        if (this.redisTemplate.hasKey(redisKey)) {
            JbGoodsPo goodsPo = (JbGoodsPo) valueOperations.get(redisKey);
            log.info("缓存命中 key = {} , value = {}", redisKey, goodsPo);
            return goodsPo;
        }

        log.info("缓存未命中，从数据库中取出");
        JbGoodsPo goodsPo = this.list(id);
        if (goodsPo != null) {
            valueOperations.set(redisKey, goodsPo);
        }

        return goodsPo;
    }

    @Override
    public JbGoodsPo listForUpdate(Integer id) {
        return this.mapper.listForUpdate(id);
    }

    @Override
    public boolean update(JbGoodsPo goodsPo) {
        return this.mapper.update(goodsPo);
    }

    @Override
    public boolean updateStockById(Integer id) {
        return this.mapper.updateStockById(id);
    }

    @Override
    public boolean updateStockByIdAndVersion(Integer id, Integer version) {
        return this.mapper.updateStockByIdAndVersion(id, version);
    }

    @Override
    public boolean updateStockByIdWithSmallGranularityLock(Integer id) {
        return this.mapper.updateStockByIdWithSmallGranularityLock(id);
    }
}
