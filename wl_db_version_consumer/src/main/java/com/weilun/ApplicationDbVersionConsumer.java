package com.weilun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author LiangYong
 * 2019/1/12
 * 17:10
 **/
@SpringBootApplication
@EnableEurekaClient
public class ApplicationDbVersionConsumer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationDbVersionConsumer.class, args);
    }
}
