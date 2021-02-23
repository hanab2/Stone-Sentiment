package com.stone.sentiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ProviderNews {
    public static void main(String[] args) {
        SpringApplication.run(ProviderNews.class,args);
    }
}
