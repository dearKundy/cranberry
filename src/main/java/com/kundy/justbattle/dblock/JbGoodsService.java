package com.kundy.justbattle.dblock;

import com.kundy.justbattle.model.po.JbGoodsPo;

import java.util.List;

/**
 * @author kundy
 * @date 2019/8/18 10:59 PM
 */
public interface JbGoodsService {

    JbGoodsPo list(Integer id);

    JbGoodsPo listForUpdate(Integer id);

    boolean update(JbGoodsPo goodsPo);

    boolean updateStockById(Integer id);

    boolean updateStockByIdAndVersion(Integer id, Integer version);

}
