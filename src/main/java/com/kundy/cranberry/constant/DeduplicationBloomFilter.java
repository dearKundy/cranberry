package com.kundy.cranberry.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 去重器全局变量表
 *
 * @author kundy
 * @date 2019/8/25 5:27 PM
 */
public class DeduplicationBloomFilter {

    public static Map<String, Boolean> INIT_STATUS = new HashMap<>();
    public static final String BEAN_SUBFFIX = "BloomFilter";

}
