package com.tangzhangss.commonutils.utils;

import cn.hutool.crypto.SecureUtil;
import com.tangzhangss.commonutils.config.Attribute;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
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
        bos.close();
        byte[] getData =  bos.toByteArray();
        inputStream.read(getData);

        if(StringUtils.isBlank(charset)){
            return new String(getData);
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
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {//遍历
            //得到属性
            Field field = fields[i];
            //打开私有访问
            field.setAccessible(true);
            if (!field.getName().equals(fieldName)) continue;
            return field.get(obj);
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
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {//遍历
            //得到属性
            Field field = fields[i];
            //打开私有访问
            field.setAccessible(true);
            if (!field.getName().equals(fieldName)) continue;
            field.set(obj,value);
        }
    }

    public static void main(String[] args) {
//        try {
//            System.out.println(getIPV4());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println(twiceMd5Salt("188632"));
    }
}
