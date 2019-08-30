package com.kundy.cranberry.thirdparty.transaction;

import com.kundy.cranberry.model.po.CbUserPo;
import com.kundy.cranberry.service.CbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 编程式事务 - 基于 PlatformTransactionManager。
 *
 * @author kundy
 * @date 2019/8/16 6:19 PM
 */
@Slf4j
@Service
public class ProgrammingTx {

    @Autowired
    private CbUserService service;

    @Autowired
    private PlatformTransactionManager txManager;

    public boolean go(CbUserPo userPo) {
        boolean result = false;
        TransactionDefinition txDefinition = new DefaultTransactionDefinition();
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
        try {
            result = this.service.save(userPo);
            int i = 1 / 0;
            txManager.commit(txStatus);
        } catch (Exception e) {
            result = false;
            txManager.rollback(txStatus);
            log.warn("基于 PlatformTransactionManager - 保存用户失败");
        }
        return result;
    }

}
