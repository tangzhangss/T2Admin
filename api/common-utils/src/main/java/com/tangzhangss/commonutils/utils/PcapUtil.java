package com.tangzhangss.commonutils.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.pkts.Pcap;
import io.pkts.packet.IPv4Packet;
import io.pkts.packet.TCPPacket;
import io.pkts.packet.impl.MACPacketImpl;
import io.pkts.protocol.Protocol;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pcap包工具类
 */
public class PcapUtil {
    //pcap文件头
    private final static String PCAP_HEADER = "d4c3b2a10200040000000000000000000000040001000000";
    ///usr/local/soft/wireshark/bin/tshark--使用wireshark命令转
    public static String WIRESHARK_BIN_PATH = "";//==/usr/local/wireshark/bin/
    /**
     * pcap转json
     */
    public static String pcapToJson(File pcapFile) throws IOException, InterruptedException {
        return pcapToJson(WIRESHARK_BIN_PATH,pcapFile);
    }
    public static String pcapToJson(String wireSharkBinPath,File pcapFile) throws IOException, InterruptedException {
        if(!(wireSharkBinPath.endsWith("/")||wireSharkBinPath.endsWith("\\"))){
            wireSharkBinPath+="/";
        }
        if (pcapFile.exists()) {
            String pcapToJsonCmd = wireSharkBinPath+"tshark -r $pcap -T json";
            String cmd = pcapToJsonCmd.replace("$pcap", pcapFile.getAbsolutePath());
            return RuntimeUtil.executeRuntimeCommand(cmd);
        }
        return null;
    }

    /**
     * 获取pcapToJson返回的JSON数据里面的layersjson数据
     *
     /**
     * 获取pcapToJson返回的JSON数据里面的layersjson数据
     *
     * @return 有序的JSONObject对象 数组
     */
    public static JSONArray getPcapLayersJsonObj(String pcapJson) throws JsonProcessingException {
        JSONConfig config = new JSONConfig();
        config.setOrder(true);

        ArrayList arrayList = JacksonUtil.toBean(pcapJson, ArrayList.class);

        JSONArray arr = new JSONArray(config);

        arrayList.forEach(one-> arr.add(JSONUtil.parseObj(one,config).getJSONObject("_source").getJSONObject("layers")));

        return arr;
    }

    /**
     *
     * @param layersObj pcap包layersObj（getPcapLayersJsonObj方法缓存）
     * @return 解析之后的数据
     */
    public static JSONObject  parsePcapLayersData(JSONObject layersObj) throws ParseException {
        JSONObject parseData= new JSONObject(new JSONConfig().setOrder(true));

        String protocol="";
        for (Map.Entry<String, Object> entry : layersObj.entrySet()){
            String key = entry.getKey();
            if(key.equals("frame")){
                JSONObject frameObj = layersObj.getJSONObject("frame");
                String time = frameObj.getStr("frame.time_epoch").replace(".","");
                //包时间微妙值
                parseData.set("time_ms", Long.parseLong(time)/1000);
                Long time_ms = parseData.getLong("time_ms");
                Long time_ms_e = time_ms%1000;
                parseData.set("time_str", DateFormatUtils.format(new Date(time_ms/1000),"yyyy-MM-dd HH:mm:ss.SSS"));
                parseData.set("time_ms_str", DateFormatUtils.format(new Date(time_ms/1000),"yyyy-MM-dd HH:mm:ss.SSS")+"."+time_ms_e);
                //长度
                parseData.set("length",frameObj.getStr("frame.len"));
                //所有协议
                parseData.set("protocols",frameObj.getStr("frame.protocols"));
            }
            //数据链路层
            if(key.equals("eth")){
                JSONObject ethObj = layersObj.getJSONObject("eth");

                //源mac地址
                parseData.set("src_mac",ethObj.getStr("eth.src"));
                //目的mac地址
                parseData.set("dst_mac",ethObj.getStr("eth.dst"));
            }
            //网络层
            if(key.equals("ip")){
                JSONObject ipObj = layersObj.getJSONObject("ip");

                //源Ip地址
                parseData.set("src_ip",ipObj.getStr("ip.src"));
                //目的Ip地址
                parseData.set("dst_ip",ipObj.getStr("ip.dst"));
            }
            if(!key.equals("data")) {
                protocol = entry.getKey();
            }
        }
        //最上层协议--取pcap解析json layers的最后一个对象(非DATA)
        parseData.set("protocol",protocol);

        return parseData;
    }
    /**
     * 获取pcap包中packet数量
     */
    public static int getPacketNum(File pcapFile){
        AtomicInteger packetNum = new AtomicInteger();//packet数量
        final Pcap pcap;
        try {
            pcap = Pcap.openStream(pcapFile);
            pcap.loop((packet)->{
                packetNum.getAndIncrement();
                return true;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return packetNum.get();
    }
    /**
     * 指定pcap包File文件，读取指定索引数据
     *
     * @param pcapFile pcap包File对象
     * @param indexArr index数组 0开始 需要从大到小排序
     * @return String字符串
     */
    public static String readToHex(File pcapFile,Integer ...indexArr) {
        if(indexArr.length==0){
            //如果没有索引就是全部
            int packetNum = getPacketNum(pcapFile);
            indexArr = new Integer[packetNum];
            for (int i = 0; i < packetNum; i++) {
                indexArr[i]=i;
            }
        }
        StringBuilder allHex = new StringBuilder();
        try (InputStream in = new FileInputStream(pcapFile)) {
            byte[] buffer_4 = new byte[4];
            byte[] buffer_24 = new byte[24];
            // pcap hearder
            int m = in.read(buffer_24);
            if (m != 24) {
                return null;
            }
            int index = 0;
            int findIndex = 0;
            int neededIndex = indexArr[findIndex];
            while (m > 0) {
                // packet_header 时间戳 精确到秒
                m = in.read(buffer_4);
                if (m < 0 || index > neededIndex) {
                    break;
                }
                if (index == neededIndex) {
                    allHex.append(bytesToHex(buffer_4));
                    // packet_header 时间戳 精确到微秒
                    m = in.read(buffer_4);
                    allHex.append(bytesToHex(buffer_4));

                    // packet_header 当前数据区的长度
                    m = in.read(buffer_4);
                    allHex.append(bytesToHex(buffer_4));
                    reverseByteArray(buffer_4);
                    int pLength = byteArrayToInt(buffer_4, 0);

                    // packet_header 离线数据长度 一般小于等于当前数据长度
                    m = in.read(buffer_4);
                    allHex.append(bytesToHex(buffer_4));

                    // packet body
                    byte[] data = new byte[pLength];
                    m = in.read(data);
                    allHex.append(bytesToHex(data));
                    findIndex += 1;
                    if (findIndex < indexArr.length) {
                        neededIndex = indexArr[findIndex];
                    }
                } else {
                    m = in.read(buffer_4);
                    m = in.read(buffer_4);
                    reverseByteArray(buffer_4);
                    int pLength = byteArrayToInt(buffer_4, 0);
                    m = in.read(buffer_4);
                    byte[] data = new byte[pLength];
                    in.read(data);
                }
                index++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return allHex.toString();
    }

    /**
     * 16进制字符串转pcap文件
     * @param hex 16进制 数据
     * @return 文件字节
     */
    public static byte[] hexToPcap(String hex) {
        if(StringUtils.isBlank(hex))return null;
        StringBuilder sb = new StringBuilder();
        sb.append(PCAP_HEADER);
        sb.append(hex);
        // 转字节
        byte[] bytes = hex2Bytes(sb.toString());

        return bytes;
    }

    /**
     * 拆分pcap包
     * @param pcapFile 文件
     * @param indexArr 索引--从小到大排序
     */
    public static byte[] splitPcap(File pcapFile, Integer...indexArr){
        return hexToPcap(readToHex(pcapFile,indexArr));
    }

    private static String bytesToHex(byte[] byteArray) {
        final StringBuilder hexString = new StringBuilder("");
        if (byteArray == null || byteArray.length <= 0)
            return null;
        for (int i = 0; i < byteArray.length; i++) {
            int v = byteArray[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                hexString.append(0);
            }
            hexString.append(hv);
        }
        return hexString.toString().toLowerCase();
    }

    /**
     * 反转数组
     */
    private static void reverseByteArray(byte[] arr) {
        byte temp;
        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            temp = arr[i];
            arr[i] = arr[n - 1 - i];
            arr[n - 1 - i] = temp;
        }
    }
    private static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }
    private static byte[] hex2Bytes(String hex) {

        if ((hex == null) || (hex.equals(""))) {
            return null;
        } else if (hex.length() % 2 != 0) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int p = 2 * i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
            }
            return b;
        }
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
