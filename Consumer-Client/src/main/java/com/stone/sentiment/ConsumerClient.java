package com.stone.sentiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConsumerClient {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerClient.class,args);
    }
}
