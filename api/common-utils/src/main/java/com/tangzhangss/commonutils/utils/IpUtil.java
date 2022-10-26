package com.tangzhangss.commonutils.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;

/**
 * 获取IP方法
 */
public class IpUtil {
    // IP地址查询 腾讯位置服务平台 https://lbs.qq.com/service/webService/webServiceGuide/webServiceIp
    public static final String IP_URL = "https://apis.map.qq.com/ws/location/v1/ip?key=QN7BZ-VQQC6-NG2SO-EXGBF-W3NOZ-6TBAH";

    // 未知地址
    public static final String UNKNOWN = "unknown";


    public static void main(String[] args) {
        System.out.println(getRealAddressByIP("182.61.200.6"));
        System.out.println(getRealAddressByIP("127.0.0.1"));
        System.out.println(getIPV4());
    }

    /**
     * 获取本机正在使用的IPV4地址 （不包含虚拟机）
     * @return ipv4地址
     */
    public static String getIPV4(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                //非回送接口 非虚拟的 正在使用的
                if(netInterface.isLoopback()||netInterface.isVirtual()||!netInterface.isUp()){
                    continue;
                }
                //排除虚拟机 上面的virtual貌似没用
                if(netInterface.getDisplayName().toUpperCase().contains("Virtual".toUpperCase())){
                    continue;
                }
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){StaticLog.error("获取ipv4失败,",e.getMessage());}
        return UNKNOWN;
    }

    /**
     * 获取ip的地理位置信息 国家省城市区域
     */
    public static AddressInfo getRealAddressByIP(String ip) {
        AddressInfo  address = new IpUtil.AddressInfo();
        // 内网不查询
        if (IpUtil.internalIp(ip)) {
            address.failResult="内网IP";
            return address;
        }
        try {
            String rspStr = HttpRequest.get(IP_URL+"&ip=" + ip).execute().body();
            if (StringUtils.isEmpty(rspStr)) {
                StaticLog.error("获取地理位置异常 {}", ip);
                address.failResult=UNKNOWN;
                return address;
            }
            JSONObject resJson = JSONUtil.parseObj(rspStr);
            JSONObject localInfo = resJson.getJSONObject("result").getJSONObject("location");
            address.lat=localInfo.getStr("lat");
            address.lng=localInfo.getStr("lng");

            JSONObject adInfo = resJson.getJSONObject("result").getJSONObject("ad_info");
            address.city = adInfo.getStr("city");
            address.province = adInfo.getStr("province");
            address.nation = adInfo.getStr("nation");
            address.district = adInfo.getStr("district");
            address.adCode = adInfo.getStr("adcode");

            return address;
        } catch (Exception e) {
            StaticLog.error("获取地理位置异常 {}", ip);
        }

        return null;
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        if (addr==null || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }


    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return "127.0.0.1";
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知";
    }



    public static class AddressInfo{
        public String nation;//国家
        public String province;//省
        public String city;//城市
        public String district;//区
        public String adCode;//行政区代码
        public String lat;//维度
        public String lng;//经度
        public String failResult;//获取失败时结果

        @Override
        public String toString() {
            return "AddressInfo{" +
                    "nation='" + nation + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", adCode='" + adCode + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", failResult='" + failResult + '\'' +
                    '}';
        }
    }
}
