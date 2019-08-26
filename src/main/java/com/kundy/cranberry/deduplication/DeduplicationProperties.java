package com.kundy.cranberry.deduplication;

import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.*;

/**
 * 去重器 properties
 *
 * @author kundy
 * @date 2019/8/25 3:13 PM
 */
@Slf4j
public class DeduplicationProperties {

    private static final String PROPERTIES_FILE_NAME = "deduplication.properties";
    private static final String KEY_BLOOM_LIST = "bloomList";
    private static final String KEY_TABLE_INFO = "tableInfo";
    private static final String KEY_EXPECTED_INSERTIONS = "expectedInsertions";

    /**
     * 获取布隆过滤器 【针对一个去重业务需要配置一个布隆过滤器】
     */
    public static List<String> getBloomList() {
        String bloomList = getProperty(KEY_BLOOM_LIST);
        if (bloomList == null) {
            return null;
        }
        return Arrays.asList(bloomList.split(","));
    }

    /**
     * 获取 布隆过滤器 -> 数据库表名 对应关系【一个布隆过滤器对应一个数据库表，该数据库用来保存已经出现过的 标识】
     */
    public static Map<String, Object> getTableInfo() {
        return parseMap(KEY_TABLE_INFO);
    }

    /**
     * 获取布隆过滤器预估元素数量，该值对结果影响较大
     */
    public static Map<String, Object> getExpectedInsertions() {
        return parseMap(KEY_EXPECTED_INSERTIONS);
    }


    private static Map<String, Object> parseMap(String key) {
        String property = getProperty(key);
        if (property == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        String[] tableInfos = property.split(",");
        for (String info : tableInfos) {
            String[] infos = info.split(":");
            checkInfos(infos);
            map.put(infos[0], infos[1]);
        }
        return map;
    }

    private static void checkInfos(String[] infos) {
        if (infos[0] == null || infos[1] == null) {
            throw new RuntimeException("tableInfo must follow the format { tableInfo = bloomName1:tableName1,bloomName2:tableName2,bloomName2:tableName2 }");
        }
    }

    private static String getProperty(String key) {
        Properties properties;
        try {
            properties = PropertiesLoaderUtils.loadAllProperties(PROPERTIES_FILE_NAME);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("read deduplication.properties error!");
        }
        return null;
    }
}
