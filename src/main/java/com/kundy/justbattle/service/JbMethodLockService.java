package com.kundy.justbattle.service;

import com.kundy.justbattle.model.po.JbMethodLockPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author kundy
 * @date 2019/8/19 9:52 PM
 */
public interface JbMethodLockService {

    boolean addMethod(String methodName);

    boolean deleteMethod(String methodName);

}
