package com.weilun.controller;

import com.weilun.api.entity.DbVersion;
import com.weilun.service.feign.DbVersionClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiangYong
 * 2019/1/12
 * 16:18
 **/
@RestController
@RequestMapping("/consumer")
public class DbVersionController {

    private final DbVersionClientService dbVersionClientService;

    @Autowired
    public DbVersionController(DbVersionClientService dbVersionClientService) {
        this.dbVersionClientService = dbVersionClientService;
    }

    @RequestMapping(value = "/db/getOne")
    public DbVersion getDbVersion() {
        return dbVersionClientService.getDbVersion();
    }
}
