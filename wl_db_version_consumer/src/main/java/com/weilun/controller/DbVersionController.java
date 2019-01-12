package com.weilun.controller;

import com.weilun.api.entity.DbVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author LiangYong
 * 2019/1/12
 * 16:18
 **/
@RestController
@RequestMapping("/consumer")
public class DbVersionController {

    private static final String REST_URL_PREFIX = "http://WL-DB-VERSION-PROVIDER";

    private final RestTemplate restTemplate;

    @Autowired
    public DbVersionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/db/getOne")
    public DbVersion getDbVersion() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/db/getOne", DbVersion.class);
    }
}
