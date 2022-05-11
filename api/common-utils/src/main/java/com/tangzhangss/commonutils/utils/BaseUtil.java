package com.tangzhangss.commonutils.utils;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSONObject;
import com.tangzhangss.commonutils.config.Attribute;
import org.apache.commons.lang.StringUtils;

import org.springframework.core.io.ClassPathResource;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseUtil {

    private BaseUtil(){}

    public static String twiceMd5Salt(String str){
        return SecureUtil.md5(SecureUtil.md5(str+ Attribute.MD5_SALT)+StringUtils.reverse(Attribute.MD5_SALT));
    }


    public static String readInputStream(InputStream inputStream,String charset) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        byte[] getData =  bos.toByteArray();
        inputStream.read(getData);
        //关闭流
        bos.close();
        inputStream.close();
        if(StringUtils.isBlank(charset)){
            //默认UTF-8
            return new String(getData,"UTF-8");
        }
        return new String(getData,charset);
    }
    /**
     * 根据路径获取文件内容
     * @param path 路径 classpath 路径
     * @param charset 编码
     * @return 文本内容
     * @throws IOException
     */
    public static String readFileContent(String path,String charset) throws IOException {
        InputStream inputStream = new ClassPathResource(path).getInputStream();
        return  readInputStream(inputStream,charset);
    }
    /**
     * 根据路径获取文件内容
     * @param path 路径 classpath 路径
     * @return 文本内容
     * @throws IOException
     */
    public static String readFileContent(String path) throws IOException {
        InputStream inputStream = new ClassPathResource(path).getInputStream();
        return  readInputStream(inputStream,null);
    }

    /**
     * 反射获取属性值得到属性值 浅 fieldName 一级
     * @param obj 对象
     * @param fieldName 对象值
     * @return 属性值
     * @throws IllegalAccessException
     */
    public static Object readAttributeValue(Object obj, String fieldName) throws  IllegalAccessException {
        //得到class
        Class cls = obj.getClass();
        Field field = getField(fieldName,cls);
        if(field==null){
            return  null;
        }
        //打开私有访问
        field.setAccessible(true);
        return field.get(obj);
    }
    /**
     * 反射获取属性值得到属性值 深度 fieldName 多级: A.B.C
     * @param obj 对象
     */
    public static Object readAttributeValueDeep(Object obj, String fieldName) throws IllegalAccessException {
        String [] fileNameArr = fieldName.split("\\.");
        Object o = obj;
        Field field;
        for (int i = 0; i < fileNameArr.length; i++) {
            field = getField(fileNameArr[i],o.getClass());
            //打开私有访问
            field.setAccessible(true);
            if(field.getName().equals(fileNameArr[i])) {
                if (i == fileNameArr.length - 1) {
                    return field.get(o);
                }
                o = field.get(o);
            }
        }
        return null;
    }

    /**
     * 反射更新属性值
     * @param obj
     */
    public static void setAttributeValue(Object obj, String fieldName,String value) throws IllegalAccessException {
        //得到class
        Class cls = obj.getClass();
        Field field = getField(fieldName,cls);
        if(field==null){
            ExceptionUtil.throwException("variable_does_not_exist",fieldName);
        }
        //打开私有访问
        field.setAccessible(true);
        field.set(obj,value);
    }


    public static String string(Object ...args){
        return StringUtils.join(args,"");
    }

    /**
     * 获取类中的字段Field对象
     * @param fieldName 字段属性名
     * @param clazz 类
     */
    public static Field getField(String fieldName,Class clazz){
        do{  // 遍历所有父类字节码对象
            try {
                Field declaredField = clazz.getDeclaredField(fieldName);  // 获取字节码对象的属性对象数组
                return declaredField;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();  // 获得父类的字节码对象
            }
        }while (clazz != null);
        return null;
    }

    /**
     * 获取当前类所有的字段 含父类
     */
    public static List<Field> getAllField(Class clazz){
        List fieldList = new ArrayList();
        do{
            // 遍历所有父类字节码对象
            Field[] declaredField = clazz.getDeclaredFields();  // 获取字节码对象的属性对象数组
            fieldList.addAll(ListUtil.of(declaredField));
            clazz = clazz.getSuperclass();  // 获得父类的字节码对象
        }while (clazz != null);

        return fieldList;
    }
    /**
     * 转换值为对象中属性的类型
     * @param clazz
     * @param key ex:a.b
     * @param value ex:true
     * @return
     */
    public static Object convertObject(Class clazz,String key, Object value) {
        //获取当前实体类的class对象
        String fieldType="";
        String []keys=key.split("\\.");
        Field field=null;
        if(keys.length>1){//多级
            for(int i=0;i<keys.length;i++){
                field = getField(keys[i],clazz);
                if (null==field)break;
                clazz=field.getType();
                if(i==(keys.length-1)){fieldType = field.getGenericType().toString();}
            }
        }else{//一级
            field = getField(key,clazz);
            if (null!=field)fieldType=field.getGenericType().toString();
        }

        return convertObject(fieldType,value);
    }
    public static Object convertObject(String fieldType,Object value){
        switch (fieldType){
            case "class java.lang.String":
                //Srting直接跳过
                break;
            case "class java.lang.Integer":
            case "int":
                //当都查询Int类型的不会有问题，当时需要使用in范围查询需要进行类型转换
                value=Convert.convert(Integer.class, value);
                break;
            case "class java.lang.Long":
            case "long":
                //当都查询Int类型的不会有问题，当时需要使用in范围查询需要进行类型转换
                value=Convert.convert(Long.class, value);
                break;
            case "class java.time.LocalDate":
                //格式 yyyy-MM-dd 前面必须四位
                value = Convert.convert(LocalDate.class, value);
                break;
            case "class java.time.LocalDateTime":
                //格式 精度只能高于---如有需要自行更改
                value = Convert.convert(LocalDateTime.class, value);
                break;
            case "class java.util.Date":
                //格式 精度只能高于---如有需要自行更改
                value = Convert.convert(Date.class, value);
                break;
            case "class java.lang.Boolean":
            case "boolean":
                //格式 精度只能高于---如有需要自行更改
                value = Convert.convert(Boolean.class, value);
                break;
        }
        return value;
    }



    /**
     * 下载（克隆）inputStream

     */
    public static InputStream cloneInputStream(InputStream input) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return new ByteArrayInputStream(baos.toByteArray());
        }catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }
}
