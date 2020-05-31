package com.kundy.cranberry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.kundy.cranberry.mapper")
@SpringBootApplication
public class CranberryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CranberryApplication.class, args);
    }

}
