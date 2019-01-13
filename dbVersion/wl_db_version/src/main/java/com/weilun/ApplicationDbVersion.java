package com.weilun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author LiangYong
 * 2019/1/6
 * 11:41
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class ApplicationDbVersion {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationDbVersion.class, args);
    }
}
