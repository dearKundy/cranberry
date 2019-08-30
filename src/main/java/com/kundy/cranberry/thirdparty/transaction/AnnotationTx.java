package com.kundy.cranberry.thirdparty.transaction;

import com.kundy.cranberry.model.po.CbUserPo;
import com.kundy.cranberry.service.CbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 声明式事务 - 基于注解
 *
 * @author kundy
 * @date 2019/8/16 7:39 PM
 */
@Slf4j
@Service
public class AnnotationTx {

    @Autowired
    private CbUserService service;

    @Transactional(
            propagation = Propagation.REQUIRED // 指定事务的传播行为
            , isolation = Isolation.READ_COMMITTED // 指定事务的隔离级别
            , rollbackFor = Exception.class // 对哪些异常进行回滚，默认【运行时异常】
            , readOnly = false // 是否只读
            , timeout = 1 // 超过指定时间，强制回滚
    )
    public boolean go(CbUserPo userPo) {
        boolean result = service.save(userPo);
        int i = 1 / 0;
        return result;
    }

}
