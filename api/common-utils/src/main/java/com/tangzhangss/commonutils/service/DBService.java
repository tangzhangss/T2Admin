package com.tangzhangss.commonutils.service;

import cn.hutool.log.StaticLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DBService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> execSqlForList(String sql){
        return jdbcTemplate.queryForList(sql);
    }


    public void executeSql(String sql){
        StaticLog.info("执行sql:"+sql);
        jdbcTemplate.execute(sql);
    }

}
