package com.kundy.justbattle.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author kundy
 * @date 2019/8/16 5:42 PM
 */
@Configuration
public class DatabaseConfig {

    @Value("${mysql.datasource.url}")
    private String mysqlUrl;
    @Value("${mysql.datasource.username}")
    private String mysqlUser;
    @Value("${mysql.datasource.password}")
    private String mysqlPassword;
    @Value("${mysql.datasource.driver-class-name}")
    private String mysqlDriverClass;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(mysqlUrl);
        dataSource.setUsername(mysqlUser);
        dataSource.setPassword(mysqlPassword);
        dataSource.setDriverClassName(mysqlDriverClass);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(DataSourceTransactionManager txManager) {
        return new TransactionTemplate(txManager);
    }

}
