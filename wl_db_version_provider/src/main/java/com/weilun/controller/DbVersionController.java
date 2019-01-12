package com.weilun.controller;

import com.weilun.api.entity.DbVersion;
import com.weilun.service.DbVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiangYong
 * 2019/1/11
 * 22:54
 **/
@RestController
@RequestMapping("/db")
public class DbVersionController {

    private final DbVersionService dbVersionService;

    @Autowired
    public DbVersionController(DbVersionService dbVersionService) {
        this.dbVersionService = dbVersionService;
    }

    @GetMapping(value = "/getOne")
    public DbVersion getOneDbVersion() {
        return dbVersionService.getDbVersion();
    }
}
