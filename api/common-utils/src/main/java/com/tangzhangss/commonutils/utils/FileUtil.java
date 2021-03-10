package com.tangzhangss.commonutils.utils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil {


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
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 判断两个文件是否相同___主要判断不同文件
     * 注意::网络文件流需要确保下载完成
     * @param in1 需要检验的文件流
     * @param in2 数据库拿出的文件流
     * @return
     */
    public static Boolean isSameFile(InputStream in1 , InputStream in2) throws IOException {


        int len = in1.available();//返回总的字节数
        int len2 = in2.available();

        if (len!=len2) {//长度不相等
            return false;
        }

        byte buffer[] = new byte[len];
        byte buffer2[] = new byte[len2];

        in1.read(buffer);
        in2.read(buffer2);

        for (int i=0;i<len;i++) {
            if(buffer[i]!=buffer2[i]) {
                return false;
            }
        }

        return true;
    }
    /**
     * 获取文件的md5值
     * md5相同也不能保证不是同一个文件
     * @param in 文件流
     * @return 返回文件的md5值字符串
     */
    public static String getFileMD5(InputStream in) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = null;
        byte buffer[] = new byte[8192];
        int len;
        digest = MessageDigest.getInstance("MD5");
        while ((len = in.read(buffer)) != -1) {
            digest.update(buffer, 0, len);
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

}
