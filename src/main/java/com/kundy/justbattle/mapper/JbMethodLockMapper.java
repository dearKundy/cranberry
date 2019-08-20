package com.kundy.justbattle.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author kundy
 * @date 2019/8/19 9:47 PM
 */
public interface JbMethodLockMapper {

    boolean addMethod(@Param("methodName") String methodName);

    boolean deleteMethod(@Param("methodName") String methodName);

}
