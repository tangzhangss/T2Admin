package com.tangzhangss.commonutils.service;

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
        List<Map<String,Object>> list;
        list=jdbcTemplate.queryForList(sql);
        return list;
    }


    public void execInsertSql(String sql){
        jdbcTemplate.execute(sql);
    }



    public int execUpdateSql(String sql){
        return jdbcTemplate.update(sql);
    }

}
