package com.kundy.justbattle.service.impl;

import com.kundy.justbattle.mapper.JbUserMapper;
import com.kundy.justbattle.model.po.JbUserPo;
import com.kundy.justbattle.service.JbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class JbUserServiceImpl implements JbUserService {

    @Autowired
    private JbUserMapper mapper;

    @Override
    public boolean save(JbUserPo userPo) {
        return this.mapper.save(userPo);
    }

}
