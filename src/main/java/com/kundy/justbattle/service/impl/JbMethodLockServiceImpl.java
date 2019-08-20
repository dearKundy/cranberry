package com.kundy.justbattle.service.impl;

import com.kundy.justbattle.mapper.JbMethodLockMapper;
import com.kundy.justbattle.service.JbMethodLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kundy
 * @date 2019/8/19 9:52 PM
 */
@Service
public class JbMethodLockServiceImpl implements JbMethodLockService {

    @Autowired
    private JbMethodLockMapper mapper;

    @Override
    public boolean addMethod(String methodName) {
        return this.mapper.addMethod(methodName);
    }

    @Override
    public boolean deleteMethod(String methodName) {
        return this.mapper.deleteMethod(methodName);
    }
}
