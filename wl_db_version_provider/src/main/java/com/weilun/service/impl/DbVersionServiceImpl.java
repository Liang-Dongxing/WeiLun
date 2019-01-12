package com.weilun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weilun.api.entity.DbVersion;
import com.weilun.dao.DbVersionDao;
import com.weilun.service.DbVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiangYong
 * 2019/1/11
 * 22:47
 **/
@Service
public class DbVersionServiceImpl implements DbVersionService {

    private final Logger logger = LoggerFactory.getLogger(DbVersionServiceImpl.class);

    private final DbVersionDao dbVersionDao;

    @Autowired
    public DbVersionServiceImpl(DbVersionDao dbVersionDao) {
        this.dbVersionDao = dbVersionDao;
    }


    public DbVersion getDbVersion() {
        QueryWrapper<DbVersion> dbVersionQueryWrapper = new QueryWrapper<DbVersion>();
        DbVersion dbVersion = dbVersionDao.selectOne(dbVersionQueryWrapper);
        logger.info(dbVersion.toString());
        return dbVersion;
    }
}
