package com.tangzhangss.commonutils.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.SecureUtil;
import com.tangzhangss.commonutils.config.Attribute;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseUtil {
    /**
     * 获取本机的IPV4地址
     *
     */
    public static String getIPV4(){
        String ip = "";
        String chinaz = "http://ip.chinaz.com";
        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection=null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (Exception e) { e.printStackTrace(); }

        try (BufferedReader in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));){
            while((read=in.readLine())!=null){
                inputLine.append(read+"\r\n");
            }
        } catch (Exception e) { e.printStackTrace();}

        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if(m.find()){
            String ipstr = m.group(1);
            ip = ipstr;
        }
        return ip;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * 用户真实IP为： 192.168.1.110
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

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
     * @param path 路径
     */
    public static String readFileContent(String path,String charset) throws IOException {
        InputStream inputStream = BaseUtil.class.getClassLoader().getResourceAsStream(path);
        return  readInputStream(inputStream,charset);
    }
    public static String readFileContent(String path) throws IOException {
        InputStream inputStream = BaseUtil.class.getClassLoader().getResourceAsStream(path);
        return  readInputStream(inputStream,null);
    }

    /**
     * 反射获取属性值得到属性值
     * @param obj
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
     * 反射更新属性值
     * @param obj
     */
    public static void setAttributeValue(Object obj, String fieldName,String value) throws IllegalAccessException {
        //得到class
        Class cls = obj.getClass();
        Field field = getField(fieldName,cls);
        if(field==null){
            ExceptionUtil.throwException("属性#{0}不存在，请检查!",fieldName);
        }
        //打开私有访问
        field.setAccessible(true);
        field.set(obj,value);
    }


    public static void log(String ...args){
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
        }
        System.out.println();
    }
    public static void main(String[] args) {
        BaseUtil.log("x","as","asas","sss");
        BaseUtil.log("11x","11as","asas","sss");
    }

    /**
     * 获取类中的字段Field对象(含继承)
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
}
