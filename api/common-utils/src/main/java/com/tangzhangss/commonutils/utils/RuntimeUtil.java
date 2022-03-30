package com.tangzhangss.commonutils.utils;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.log.StaticLog;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Runtime工具类
 */
public class RuntimeUtil {
    /**
     * 获取ps aux 命令结果
     * @param res 行状态 （需要保证这是一行结果）
     * @return json对象
     */
    public static JSONArray parsePsAuxCmdRes(String res){
        JSONArray array = new JSONArray();
        JSONConfig config = new JSONConfig();
        config.setOrder(true);
        if(StringUtils.isBlank(res))return array;
        String[] lines = res.split("\r\n");
        for (int i = 0; i < lines.length; i++) {
            String[] resArr = lines[i].split("\\s+");
            if(resArr.length<11)throw new RuntimeException("Parsing failed. Format error");

            array.add(
                    new JSONObject(config).set("user",resArr[0])
                            .set("pid",resArr[1])
                            .set("%cpu",resArr[2])
                            .set("%mem",resArr[3])
                            .set("vsz",resArr[4])
                            .set("rss",resArr[5])
                            .set("tty",resArr[6])
                            .set("stat",resArr[7])
                            .set("start",resArr[8])
                            .set("time",resArr[9])
                            .set("command",StringUtils.join(ArrayUtils.subarray(resArr,10,resArr.length)))
            );
        }

        return array;
    }

    /**
     * 获取 netstat 命令结果
     */
    public static JSONArray parseNetstatCmdRes(String res){
        JSONArray array = new JSONArray();
        JSONConfig config = new JSONConfig();
        config.setOrder(true);
        if(org.apache.commons.lang3.StringUtils.isBlank(res))return array;
        String[] lines = res.split("\r\n");
        for (int i = 0; i < lines.length; i++) {
            String[] resArr = lines[i].split("\\s+");
            if(resArr.length<6)throw new RuntimeException("Parsing failed. Format error");
            String[] local = resArr[3].split(":");
            String[] foreign = resArr[4].split(":");
            array.add(
                    new JSONObject(config).set("proto",resArr[0])
                            .set("recvQ",resArr[1])
                            .set("sendQ",resArr[2])
                            .set("localAddr",resArr[3])
                            .set("localIp",local[0])
                            .set("localPort",local[1])
                            .set("foreignAddr",resArr[4])
                            .set("foreignIp",foreign[0])
                            .set("foreignPort",foreign[1])
                            .set("state",resArr[5])
            );
        }

        return array;
    }


    /**
     * 执行linux command命令
     * @param command 命令
     */
    public static String executeRuntimeCommand(String command) throws IOException, InterruptedException {
        StringBuffer res = new StringBuffer();
        String osName = System.getProperty("os.name").toUpperCase();
        String[]  cmdArr = null;
        if(osName.contains("LINUX"))cmdArr=new String[]{"/bin/sh", "-c", command};
        else if(osName.contains("WINDOWS"))cmdArr=new String[]{"cmd", "/c", command};

        StaticLog.info("执行命令 {}", StringUtils.join(cmdArr," "));
        Process process = Runtime.getRuntime().exec(cmdArr);
        process.getOutputStream().close();
        try(
                BufferedReader successBuffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorBuffer = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        ){
            /**
             * 正确信息
             */
            String line;
            //如果是阻塞命令，这里也会阻塞
            while ((line = successBuffer.readLine()) != null) {
                res.append(line).append("\r\n");
            }
            /**
             * 错误信息--先读取inputStream-读取了inputStream才知道是否成功
             */
            if(process.waitFor()!=0){
                String errorLine;
                StringBuffer errorStr=new StringBuffer();
                while ((errorLine = errorBuffer.readLine()) != null) {
                    errorStr.append(errorLine);
                }
                String errorMsg = errorStr.toString();
                if(StringUtils.isNotBlank(errorMsg)){
                    throw new RuntimeException(errorMsg);
                }
            }
        }finally {
            process.destroy();
        }

        return res.toString();
    }




}
