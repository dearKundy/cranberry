package com.kundy.cranberry.mapper;

import com.kundy.cranberry.model.po.CbGoodsPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author kundy
 * @date 2019/8/18 10:54 PM
 */
public interface CbGoodsMapper {

    CbGoodsPo list(Integer id);

    CbGoodsPo listForUpdate(Integer id);

    boolean update(CbGoodsPo goodsPo);

    boolean updateStockById(Integer id);

    boolean updateStockByIdAndVersion(@Param("id") Integer id, @Param("version") Integer version);

    boolean updateStockByIdWithSmallGranularityLock(Integer id);

}
