package com.kundy.cranberry.systemdesign.deduplication;

import java.util.Map;

/**
 * @author kundy
 * @date 2019/8/25 10:21 PM
 */
public class DeduplicationTableNameUtils {

    private static final String SUFFIX = "_deduplication";

    /**
     * 根据 bean 名称获取对应的表名，默认值为 beanName+_deduplication
     */
    public static String getTableName(String bloomFilterName) {
        Map<String, Object> tableInfo = DeduplicationProperties.getTableInfo();
        if (tableInfo == null || !tableInfo.containsKey(bloomFilterName)) {
            return bloomFilterName + SUFFIX;
        }
        return (String) tableInfo.get(bloomFilterName);
    }

}
