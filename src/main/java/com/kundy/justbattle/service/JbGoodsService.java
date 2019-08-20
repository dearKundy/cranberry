package com.kundy.justbattle.service;

import com.kundy.justbattle.model.po.JbGoodsPo;

import java.util.List;

/**
 * @author kundy
 * @date 2019/8/18 10:59 PM
 */
public interface JbGoodsService {

    /**
     * 查询货物信息
     */
    JbGoodsPo list(Integer id);

    /**
     * 加排它锁查询货物信息
     */
    JbGoodsPo listForUpdate(Integer id);

    /**
     * 更新货物
     */
    boolean update(JbGoodsPo goodsPo);

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
