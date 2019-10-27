package com.kundy.cranberry;

import com.kundy.cranberry.thirdparty.xsd.ApplicationConfig;
import com.kundy.cranberry.thirdparty.xsd.ServiceBean;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@MapperScan("com.kundy.cranberry.mapper")
@SpringBootApplication
public class CranberryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CranberryApplication.class, args);
    }

}
