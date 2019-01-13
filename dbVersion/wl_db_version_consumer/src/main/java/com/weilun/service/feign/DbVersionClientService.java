package com.weilun.service.feign;

import com.weilun.api.entity.DbVersion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author LiangYong
 * 2019/1/12
 * 18:42
 **/
@FeignClient(value = "DB-VERSION", fallbackFactory = DbVersionClientServiceFallbackFactory.class)
public interface DbVersionClientService {

    @GetMapping(value = "/db/getOne")
    DbVersion getDbVersion();
}
