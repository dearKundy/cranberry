package com.kundy.cranberry.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 去重布隆过滤器
 *
 * @author kundy
 * @date 2019/8/23 1:53 PM
 */
public interface DeduplicationBloomFilterMapper {

    String isTableExist(String tableName);

    void createTable(@Param("tableName") String tableName);

    List<String> listIdentifier(@Param("tableName") String tableName, @Param("offset") Integer offset, @Param("limit") Integer limit);

    Integer selectCount(@Param("tableName") String tableName);

    boolean batchSave(Map<String, Object> map);

}
