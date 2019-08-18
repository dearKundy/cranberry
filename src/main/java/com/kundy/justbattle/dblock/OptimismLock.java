package com.kundy.justbattle.dblock;

import com.kundy.justbattle.model.po.JbGoodsPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 乐观锁：数据表增加 version 字段
 *
 * @author kundy
 * @date 2019/8/18 11:22 PM
 */
@Service
public class OptimismLock {

    @Autowired
    private JbGoodsService service;

    public boolean sale(Integer id) {
        JbGoodsPo goods = this.service.list(id);
        Integer stock = goods.getStock();
        Integer version = goods.getVersion();

        if (stock > 0) {
            boolean flag = this.service.updateStockByIdAndVersion(id, version);
            if (flag) {
                System.out.println("成功售出商品一件！");
                return true;
            }
            System.out.println("获取乐观锁失败。。。");
            return false;
        }
        System.out.println("库存不足。。。");
        return false;
    }

}
