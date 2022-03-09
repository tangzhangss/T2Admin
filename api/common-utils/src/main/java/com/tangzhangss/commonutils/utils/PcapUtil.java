package com.tangzhangss.commonutils.utils;

import cn.hutool.core.util.RandomUtil;
import io.pkts.Pcap;
import io.pkts.packet.IPv4Packet;
import io.pkts.packet.TCPPacket;
import io.pkts.packet.impl.MACPacketImpl;
import io.pkts.protocol.Protocol;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
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
     * @param pcapFile
     * @return
     */
    public static String pcapToJson(File pcapFile) {
        String jsonStr = null;
        if (pcapFile.exists()) {
            try{
                File tempFie = FileUtil.createTempFile(".json");
                String pcapToJsonCmd = WIRESHARK_BIN_PATH+"tshark -r $pcap -T json > $tempFile";
                String cmd = pcapToJsonCmd.replace("$pcap", pcapFile.getPath()).replace("$tempFile", tempFie.getAbsolutePath());
                BaseUtil.executeRuntimeCommand(cmd);
                jsonStr =  BaseUtil.readInputStream(new FileInputStream(tempFie),"utf-8");
            }catch (Exception e){
                ExceptionUtil.throwException(e.getMessage());
            }
        }
        return jsonStr;
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
            ExceptionUtil.throwException(e.getMessage());
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
            ExceptionUtil.throwException(e.getMessage());
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
