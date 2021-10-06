package com.tangzhangss.commonutils.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DBService {
    //日志打印
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> execSqlForList(String sql){
        return jdbcTemplate.queryForList(sql);
    }


    public void executeSql(String sql){
        logger.info("执行sql:"+sql);
        jdbcTemplate.execute(sql);
    }

}
