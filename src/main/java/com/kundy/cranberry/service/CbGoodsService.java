package com.kundy.cranberry.service;

import com.kundy.cranberry.model.po.CbGoodsPo;

/**
 * @author kundy
 * @date 2019/8/18 10:59 PM
 */
public interface CbGoodsService {

    /**
     * 查询货物信息
     */
    CbGoodsPo list(Integer id);

    /**
     * 查询货物信息 - 缓存版
     */
    CbGoodsPo listInCache(Integer id);

    /**
     * 加排它锁查询货物信息
     */
    CbGoodsPo listForUpdate(Integer id);

    /**
     * 更新货物
     */
    boolean update(CbGoodsPo goodsPo);

    /**
     * 根据 id 更新库存
     */
    boolean updateStockById(Integer id);

    /**
     * 使用 version 字段 更新库存
     */
    boolean updateStockByIdAndVersion(Integer id, Integer version);

    /**
     * 使用更加小粒度的乐观锁更新库存
     */
    boolean updateStockByIdWithSmallGranularityLock(Integer id);


}
