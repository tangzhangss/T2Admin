package com.tangzhangss.commonutils.datasource.builder;

import cn.hutool.log.StaticLog;
import com.tangzhangss.commonutils.service.DBService;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * sql更新
 *
 *  where条件只支持 等于
 *
 *  复杂的请先查询再调用 jpa的save
 */
public abstract class UpdateBuilder{

    protected String tableName;
    protected final HashMap<String,Object> setMap=new LinkedHashMap<>();
    protected final HashMap<String,Object> whereMap=new LinkedHashMap<>();
    public UpdateBuilder with(String tableName){
        this.tableName=tableName;
        return this;
    }

    public UpdateBuilder set(String key,Object value){
        setMap.put(key,value);
        return this;
    }
    public UpdateBuilder set(HashMap<String,Object> hashMap){
        setMap.putAll(hashMap);
        return this;
    }

    public UpdateBuilder where(String key,Object value){
        whereMap.put(key,value);
        return this;
    }
    public UpdateBuilder where(HashMap<String,Object> hashMap){
        whereMap.putAll(hashMap);
        return this;
    }
    public String toSqlStr(){
        if(StringUtils.isBlank(this.tableName)) ExceptionUtil.throwException("this tableName is null,please check");
        StringBuffer buffer = new StringBuffer();


        List<String> setList = new ArrayList<>();
        setMap.forEach((key,value)->setList.add(key+"="+ value));
        List<String> whereList = new ArrayList<>();
        whereMap.forEach((key,value)->whereList.add(key+"="+ value));

        buffer.append("update ").append(tableName).append(" set ").append(StringUtils.join(setList,","))
                .append(" where ").append(StringUtils.join(whereList," and "))
                .append(";");


        return buffer.toString();
    }


    public abstract void execute();
}
