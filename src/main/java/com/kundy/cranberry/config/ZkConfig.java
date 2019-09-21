package com.kundy.cranberry.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zkClient 配置类
 *
 * @author kundy
 * @date 2019/8/21 12:36 PM
 */
@Configuration
public class ZkConfig {

    @Value("${zk.servers}")
    private String servers;

    @Value("${zk.connectionTimeout}")
    private Integer connectionTimeout;

    @Bean
    public ZkClient zkClient() {
//        return new ZkClient(servers, connectionTimeout);
        return null;
    }

}
