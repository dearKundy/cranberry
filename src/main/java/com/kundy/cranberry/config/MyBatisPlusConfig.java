package com.kundy.cranberry.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kundy
 * @date 2019/9/14 10:52 AM
 */
@Configuration
public class MyBatisPlusConfig {

    public static ThreadLocal<String> myTableName = new ThreadLocal<>();

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParsers = new ArrayList<>();

        // 动态表名
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();

        Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<>();
        tableNameHandlerMap.put("jb_goods", new ITableNameHandler() {
            @Override
            public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
                return myTableName.get();
            }
        });

        dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
        sqlParsers.add(dynamicTableNameParser);

        paginationInterceptor.setSqlParserList(sqlParsers);
        return paginationInterceptor;
    }

    /**
     * SQL 性能分析插件
     * 只在 dev 与 test 环境开启 可以使用  -Dspring.profiles.active=dev 指定激活环境
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor();
    }

}
