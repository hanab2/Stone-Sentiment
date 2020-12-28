package com.stone.config;

import com.overzealous.remark.Remark;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class MarkDownCofig {

    @Bean
    Remark remark(){
        return new Remark();
    }
}
