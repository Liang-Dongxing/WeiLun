package com.weilun.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author LiangYong
 * 2019/1/5
 * 21:24
 **/
@SpringBootApplication
@EnableEurekaServer
public class ApplicationEureka {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEureka.class, args);
    }
}
