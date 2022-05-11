package com.tangzhangss.commonutils.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JPAUtil {
    /**
     * 获取实体表的名字
     */
    public static String getSqlEntityTableName(Class clazz){
        if(!clazz.isAnnotationPresent(Table.class)){
            ExceptionUtil.throwException("entity_not_correctly_mapped_to_database",clazz.toString());
        }
        //获取类上的注解 表的名字
        Table annotations = (Table) clazz.getAnnotation(Table.class);
        String tableName=annotations.name();
        if(StringUtils.isBlank(tableName))ExceptionUtil.throwException("entity_not_correctly_mapped_to_database",clazz.toString());
        return tableName;
    }

    /**
     *
     * 获取实体自定义表字段名
     */
    public static String getSqlEntityColumnName(Class clazz,String key){
        Field field = BaseUtil.getField(key, clazz);
        if(field==null)ExceptionUtil.throwException("this class no such field ["+key+"]");
        //获取类上的注解 字段映射的名字
        Column annotations = field.getAnnotation(Column.class);
        if(annotations==null)return null;
        //如果存在column注解但是没有name属性 默认是""
        String name = annotations.name();
        return StringUtils.isBlank(name)?null:name;
    }

    /**
     * 获取实体的值 转map
     * value只能转成String，用于处理sql(其他类型的请使用JSON对象转换)
     * @param isUnderlineCase 是否转下划线命名
     */
    public static List<Map<String,String>> getSqlEntityValue(List dataList, boolean isUnderlineCase){
        List<Map<String,String>> paramsList = new ArrayList<>();
        if(dataList.size()==0)return paramsList;
        Object data = dataList.get(0);
        //获取实体所有的字段
        List<Field> fieldList = BaseUtil.getAllField(data.getClass());

        for (Object item : dataList) {
            //子类有的就不管父类的
            Map<String,String> params= new HashMap<>();

            for (Field field : fieldList) {
                if(field.isAnnotationPresent(Transient.class)){
                    //存在 Transient注解不需要保存数据库
                    continue;
                }
                Object v = null;
                try {
                    v = BaseUtil.readAttributeValue(item,field.getName());
                } catch (IllegalAccessException e) {
                    ExceptionUtil.throwException("getEntityValue=>error:",e.getMessage());
                }
                String key = field.getName();
                //判断是否存在自定义的字段映射名
                String colName=JPAUtil.getSqlEntityColumnName(data.getClass(),key);
                if(colName!=null){
                    key=colName;
                }else if(isUnderlineCase){//将驼峰转成下划线
                    key = StrUtil.toUnderlineCase(key);
                }
                if(v!=null){
                    String vStr = sqlHandle(v,field.getGenericType().toString());
                    params.putIfAbsent(key,vStr);
                }else{
                    params.putIfAbsent(key, null);//key null
                }
            }
            paramsList.add(params);
        }

        return paramsList;
    }


    /**
     * sql插入的后字符串等需要加''
     * 这里处理
     */
    public static String sqlHandle(Object value, String fieldType){
        String vStr = String.valueOf(value);
        switch (fieldType){
            case "class java.lang.String":
            case "class java.time.LocalDate":
            case "class java.time.LocalDateTime":
            case "class java.util.Date":
                vStr = "'"+vStr+"'";
                break;
        }
        return vStr;
    }
}
