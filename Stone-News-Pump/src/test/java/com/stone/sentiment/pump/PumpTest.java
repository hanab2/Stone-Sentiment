package com.stone.sentiment.pump;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class PumpTest {

    @Resource Pump pump;

    @Test
    void testPump(){
        pump.pump();
    }
}
