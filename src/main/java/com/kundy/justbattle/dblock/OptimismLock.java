package com.kundy.justbattle.dblock;

import com.kundy.justbattle.model.po.JbGoodsPo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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

    /**
     * 高并发下，就只有一个线程可以修改成功 【经常被修改的记录，不适宜使用乐观锁】
     */
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

    /**
     * 降低乐观锁粒度
     */
    public boolean saleWithSmallGranularityLock(Integer id) {
        Integer stock = this.service.list(id).getStock();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (stock > 0) {
            boolean flag = this.service.updateStockByIdWithSmallGranularityLock(id);
            if (flag) {
                System.out.println("成功售出商品一件！");
                return true;
            }
            System.out.println("获取乐观锁失败。。。");
            return false;
        }
        System.out.println("库存不足");
        return false;
    }

}
