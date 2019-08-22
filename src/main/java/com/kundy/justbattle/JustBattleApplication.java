package com.kundy.justbattle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.kundy.justbattle.mapper")
@SpringBootApplication
public class JustBattleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JustBattleApplication.class, args);
    }

}
