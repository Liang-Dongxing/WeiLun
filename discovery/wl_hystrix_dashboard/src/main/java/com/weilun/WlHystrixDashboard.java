package com.weilun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author LiangYong
 * 2019/1/13
 * 15:15
 **/
@SpringBootApplication
@EnableHystrixDashboard
public class WlHystrixDashboard {
    public static void main(String[] args) {
        SpringApplication.run(WlHystrixDashboard.class, args);
    }
}
