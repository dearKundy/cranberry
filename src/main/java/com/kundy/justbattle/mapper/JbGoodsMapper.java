package com.kundy.justbattle.mapper;

import com.kundy.justbattle.model.po.JbGoodsPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author kundy
 * @date 2019/8/18 10:54 PM
 */
public interface JbGoodsMapper {

    JbGoodsPo list(Integer id);

    JbGoodsPo listForUpdate(Integer id);

    boolean update(JbGoodsPo goodsPo);

    boolean updateStockById(Integer id);

    boolean updateStockByIdAndVersion(@Param("id") Integer id, @Param("version") Integer version);

}
