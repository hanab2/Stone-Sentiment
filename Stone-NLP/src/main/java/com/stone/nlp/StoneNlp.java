package com.stone.nlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class StoneNlp {
    public static void main(String[] args) {
        SpringApplication.run(StoneNlp.class, args);
    }
}
