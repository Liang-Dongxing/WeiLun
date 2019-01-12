package com.weilun.test;

import com.weilun.api.entity.DbVersion;
import com.weilun.service.DbVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author LiangYong
 * 2019/1/11
 * 23:05
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(DaoTest.class);

    @Autowired
    private DbVersionService dbVersionService;

    @Test
    public void getDbVersion() {
        DbVersion dbVersion = dbVersionService.getDbVersion();
        LOGGER.info(dbVersion.toString());
    }
}
