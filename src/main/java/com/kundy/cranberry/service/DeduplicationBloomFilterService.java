package com.kundy.cranberry.service;

import java.util.List;
import java.util.Map;

/**
 * @author kundy
 * @date 2019/8/23 2:49 PM
 */
public interface DeduplicationBloomFilterService {

    boolean isTableExist(String tableName);

    boolean createTable(String tableName);

    List<String> listIdentifier(String tableName, Integer offset, Integer limit);

    Integer selectCount(String tableName);

    boolean batchSave(String tableName, List<String> identifiers);

}
