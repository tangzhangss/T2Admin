package com.tangzhangss.commonutils.utils;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.config.Attribute;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseUtil {
    /**
     * 获取日志对象，构造函数传入当前类，查找日志方便定位
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

    /**
     * 获取本机ip
     */
    public static String getLocalIP() throws SocketException {
        String ip = null;
        Enumeration allNetInterfaces;
        allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
                    .nextElement();
            List<InterfaceAddress> InterfaceAddress = netInterface
                    .getInterfaceAddresses();
            for (InterfaceAddress add : InterfaceAddress) {
                InetAddress Ip = add.getAddress();
                if (Ip != null && Ip instanceof Inet4Address) {
                    ip = Ip.getHostAddress();
                }
            }
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
     * @param path 路径 classpath 路径
     */
    public static String readFileContent(String path,String charset) throws IOException {
        InputStream inputStream = new ClassPathResource(path).getInputStream();
        return  readInputStream(inputStream,charset);
    }
    public static String readFileContent(String path) throws IOException {
        InputStream inputStream = new ClassPathResource(path).getInputStream();
        return  readInputStream(inputStream,null);
    }

    /**
     * 反射获取属性值得到属性值 浅 fieldName 一级
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
     * 反射获取属性值得到属性值 深度 fieldName 多级: A.B.C
     * @param obj
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
            ExceptionUtil.throwException("属性#{0}不存在，请检查!",fieldName);
        }
        //打开私有访问
        field.setAccessible(true);
        field.set(obj,value);
    }


    public static String string(String ...args){
        return StringUtils.join(args,"");
    }
    public static void main(String[] args) {

    }

    /**
     * 获取类中的字段Field对象(不含继承)
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

    /**
     * 获取实体的值 转map
     * value只能转成String，用于处理sql(其他类型的请使用JSON对象转换)
     * @Param isUnderlineCase 是否转下划线命名
     */
    public static List<Map<String,String>> getSqlEntityValue(List dataList,boolean isUnderlineCase){
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
                    e.printStackTrace();
                    ExceptionUtil.throwException("getEntityValue=>error:",e.getMessage());
                }
                String key = field.getName();
                //将驼峰转成下划线
                if(isUnderlineCase){
                    key = StrUtil.toUnderlineCase(key);
                }
                if(v!=null){
                    String vStr = BaseUtil.sqlHandle(v,field.getGenericType().toString());
                    params.putIfAbsent(key,vStr);
                }else{
                    params.putIfAbsent(key, null);//key null
                }
            }
            paramsList.add(params);
        }

        return paramsList;
    }

}
