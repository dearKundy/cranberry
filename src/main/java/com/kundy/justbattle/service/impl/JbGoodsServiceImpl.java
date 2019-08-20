package com.kundy.justbattle.service.impl;

import com.kundy.justbattle.mapper.JbGoodsMapper;
import com.kundy.justbattle.model.po.JbGoodsPo;
import com.kundy.justbattle.service.JbGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kundy
 * @date 2019/8/18 10:59 PM
 */
@Service
public class JbGoodsServiceImpl implements JbGoodsService {

    @Autowired
    private JbGoodsMapper mapper;

    @Override
    public JbGoodsPo list(Integer id) {
        return this.mapper.list(id);
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
