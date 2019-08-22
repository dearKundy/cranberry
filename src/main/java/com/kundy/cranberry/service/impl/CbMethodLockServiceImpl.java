package com.kundy.cranberry.service.impl;

import com.kundy.cranberry.service.CbMethodLockService;
import com.kundy.cranberry.mapper.CbMethodLockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kundy
 * @date 2019/8/19 9:52 PM
 */
@Service
public class CbMethodLockServiceImpl implements CbMethodLockService {

    @Autowired
    private CbMethodLockMapper mapper;

    @Override
    public boolean addMethod(String methodName) {
        return this.mapper.addMethod(methodName);
    }

    @Override
    public boolean deleteMethod(String methodName) {
        return this.mapper.deleteMethod(methodName);
    }
}
