package com.kundy.justbattle.transaction;

import com.kundy.justbattle.model.po.JbUserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 编程式事务 - 基于事务管理模板【要在配置类中配置 TransactionTemplate 】
 *
 * @author kundy
 * @date 2019/8/16 6:37 PM
 */
@Slf4j
@Service
public class TemplateTx {

    @Autowired
    private JbUserService service;

    @Autowired
    private TransactionTemplate txTemplate;

    public boolean go(JbUserPo userPo) {
        return (boolean) txTemplate.execute(new TransactionCallback<Boolean>() {

            boolean result = false;

            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {
                    result = service.save(userPo);
                    int i = 1 / 0;
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    log.warn("事务管理模板 - 保存用户失败");
                    return false;
                }
                return result;
            }

        });
    }

}
