package com.kundy.cranberry.service.impl;

import com.kundy.cranberry.mapper.CbUserMapper;
import com.kundy.cranberry.model.po.CbUserPo;
import com.kundy.cranberry.service.CbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Spring 事务测试
 * 1.
 * 2. 编程式事务-基于 TransactionTemplate。
 * 3. 声明式事务
 *
 * @author kundy
 * @date 2019/8/16 5:21 PM
 */
@Slf4j
@Service
public class CbUserServiceImpl implements CbUserService {

    @Autowired
    private CbUserMapper mapper;

    @Override
    public List<Integer> listBlackUserId() {
        return this.mapper.listBlackUserId();
    }

    @Override
    public boolean save(CbUserPo userPo) {
        return this.mapper.save(userPo);
    }

}
