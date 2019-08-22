package com.kundy.cranberry.service.impl;

import com.kundy.cranberry.constant.RedisKey;
import com.kundy.cranberry.mapper.CbGoodsMapper;
import com.kundy.cranberry.model.po.CbGoodsPo;
import com.kundy.cranberry.service.CbGoodsService;
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
public class CbGoodsServiceImpl implements CbGoodsService {

    @Autowired
    private CbGoodsMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public CbGoodsPo list(Integer id) {
        return this.mapper.list(id);
    }

    @Override
    public CbGoodsPo listInCache(Integer id) {
        
        String redisKey = RedisKey.JB_GOODS + id;
        ValueOperations valueOperations = this.redisTemplate.opsForValue();

        if (this.redisTemplate.hasKey(redisKey)) {
            CbGoodsPo goodsPo = (CbGoodsPo) valueOperations.get(redisKey);
            log.info("缓存命中 key = {} , value = {}", redisKey, goodsPo);
            return goodsPo;
        }

        log.info("缓存未命中，从数据库中取出");
        CbGoodsPo goodsPo = this.list(id);
        if (goodsPo != null) {
            valueOperations.set(redisKey, goodsPo);
        }

        return goodsPo;
    }

    @Override
    public CbGoodsPo listForUpdate(Integer id) {
        return this.mapper.listForUpdate(id);
    }

    @Override
    public boolean update(CbGoodsPo goodsPo) {
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
