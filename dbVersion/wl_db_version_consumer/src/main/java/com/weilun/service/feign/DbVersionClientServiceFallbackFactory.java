package com.weilun.service.feign;

import com.weilun.api.entity.DbVersion;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author LiangYong
 * 2019/1/13
 * 13:32
 **/
@Component
public class DbVersionClientServiceFallbackFactory implements FallbackFactory<DbVersionClientService> {
    public DbVersionClientService create(Throwable cause) {
        return new DbVersionClientService() {
            public DbVersion getDbVersion() {
                DbVersion dbVersion = new DbVersion();
                dbVersion.setId(0);
                dbVersion.setVersion(0);
                return dbVersion;
            }
        };
    }
}
