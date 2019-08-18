package com.kundy.justbattle.dblock;

import com.kundy.justbattle.model.po.JbGoodsPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 悲观锁：利用数据库本身的锁机制 【FOR UPDATE 排它锁】
 * <p>
 * 实现功能：
 * 判断库存是否大于零，如果是，则减库存。
 *
 * @author kundy
 * @date 2019/8/18 10:34 PM
 */
@Service
public class PessimisticLock {

    @Autowired
    private JbGoodsService service;

    /**
     * bug 版本
     */
    public boolean saleWithBugOne(Integer id) {
        Integer stock = this.service.list(id).getStock();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * 明显bug，假设有5个线程同时进入到此位置，他们的 stock 值都是相等的，然后用 stock = 1 更新数据库 5 次（假设这时候 stock =1 ）
         * 我们不能再程序中将 stock- 1 再传入 SQL，而是要在 SQL 中进行 stock - 1.
         */
        if (stock > 0) {
            JbGoodsPo goods = new JbGoodsPo().setId(id).setStock(stock - 1);
            return this.service.update(goods);
        }
        return false;
    }

    /**
     * 这里的bug是什么呢？查询 stock 与 更新 stock 不是一个原子操作，同一瞬间会有很多的线程进入临界区，
     * 我们在这里使用悲观锁解决这个问题，可以使用 Java 锁【注意：分布式情况还是有问题】，也可以使用数据库事务 + 排它锁
     */
    public boolean saleWithBugTwo(Integer id) {
        Integer stock = this.service.list(id).getStock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (stock > 0) {
            return this.service.updateStockById(id);
        }
        return false;
    }

    /**
     * 通过 数据库事务 + 排它锁【悲观锁】 解决超售问题
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean sale(Integer id) {
        Integer stock = this.service.listForUpdate(id).getStock();

        // 程序会被阻塞在这，直到前面的线程将排它锁释放

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (stock > 0) {
            boolean flag = this.service.updateStockById(id);
            if (flag) {
                System.out.println("成功售出商品一件！");
            }
            return flag;
        }
        return false;
    }

}
