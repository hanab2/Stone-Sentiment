package com.stone.sentiment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.stone.sentiment.mapper")
public class ProviderUser {
    public static void main(String[] args) {
        SpringApplication.run(ProviderUser.class, args);
    }
}
