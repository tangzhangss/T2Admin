package com.tangzhangss.commonutils.utils.runtime;

import cn.hutool.core.util.CharsetUtil;
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
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 * Runtime工具类
 */
public class RuntimeUtil {
    private RuntimeUtil(){};
    private final static String EXCEPTION_STR="Type of an unsupported operating system";
    /**
     * 获取ps aux 命令结果
     * @param res 行状态 （需要保证这是一行结果）
     * @return json对象
     */
    public static JSONArray parsePsAuxCmdRes(String res){
        if(!OSInfo.isLinux())throw new RuntimeException(EXCEPTION_STR);

        JSONArray array = new JSONArray();
        JSONConfig config = new JSONConfig();
        config.setOrder(true);
        if(StringUtils.isBlank(res))return array;
        String[] lines = res.split(OSInfo.getLineBreak());
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
        if(!OSInfo.isLinux())throw new RuntimeException(EXCEPTION_STR);
        JSONArray array = new JSONArray();
        JSONConfig config = new JSONConfig();
        config.setOrder(true);
        if(org.apache.commons.lang3.StringUtils.isBlank(res))return array;
        String[] lines = res.split(OSInfo.getLineBreak());
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
     * 获取kafka的kafka-consumer-groups.sh结果
     */
    public static JSONArray parseKafkaConsumerGroupsCmdRes(String res) {
        if(!OSInfo.isLinux())throw new RuntimeException(EXCEPTION_STR);
        JSONArray array = new JSONArray();
        JSONConfig config = new JSONConfig();
        config.setOrder(true);
        if (org.apache.commons.lang3.StringUtils.isBlank(res)) return array;
        String[] lines = res.split(OSInfo.getLineBreak());
        for (int i = 0; i < lines.length; i++) {
            String[] resArr = lines[i].split("\\s+");
            if (resArr.length == 10){//2.12-3.1.0
                array.add(
                        new JSONObject(config).set("group", resArr[0])
                                .set("topic", resArr[1])
                                .set("partition", resArr[2])
                                .set("currentOffset", resArr[3])
                                .set("logEndOffset", resArr[4])
                                .set("lag", resArr[5])
                                .set("consumerId", resArr[6]+" "+resArr[7])
                                .set("host", resArr[8])
                                .set("clientId", resArr[9])
                );
            }else if (resArr.length == 8){ //2.11-1.0.0
                array.add(
                        new JSONObject(config)
                                .set("topic", resArr[0])
                                .set("partition", resArr[1])
                                .set("currentOffset", resArr[2])
                                .set("logEndOffset", resArr[3])
                                .set("lag", resArr[4])
                                .set("consumerId", resArr[5])
                                .set("host", resArr[6])
                                .set("clientId", resArr[7])
                );
            }else{
                throw new RuntimeException("Parsing failed. Format error");
            }

        }

        return array;
    }


    public static String[] getExecCommand(String command){
        String[]  cmdArr;
        if(OSInfo.isLinux()) {
            cmdArr=new String[]{"/bin/sh", "-c", command};
        }else if(OSInfo.isWindows()) {
            cmdArr=new String[]{"cmd", "/c", command};
        }else{
            throw new RuntimeException(EXCEPTION_STR);
        }
        return cmdArr;
    }

    public static String executeRuntimeCommandDaemon(String command) throws IOException, InterruptedException {
        return executeRuntimeCommandDaemon(command,"temp.out");
    }
    public static String executeRuntimeCommandDaemon(String command,String outFile) throws IOException, InterruptedException {
        if(OSInfo.isLinux()) {
            command = "nohup "+command+" >"+outFile+"  2>&1 &";
        }else if(OSInfo.isWindows()) {
            command = "start /B "+command+" >"+outFile;
        }else{
            throw new RuntimeException(EXCEPTION_STR);
        }
        return executeRuntimeCommand(command);
    }
    /**
     * 执行linux command命令
     * @param command 命令
     */
    public static String executeRuntimeCommand(String command) throws IOException, InterruptedException {
        return executeRuntimeCommand(command,CharsetUtil.systemCharset());
    }
    public static String executeRuntimeCommand(String command,Charset charset) throws IOException, InterruptedException {
        StringBuffer res = new StringBuffer();
        String[]  cmdArr = getExecCommand(command);
        StaticLog.info("执行命令 {}", StringUtils.join(cmdArr," "));
        Process process = Runtime.getRuntime().exec(cmdArr);
        process.getOutputStream().close();
        try(
                BufferedReader successBuffer = new BufferedReader(new InputStreamReader(process.getInputStream(),charset));
                BufferedReader errorBuffer = new BufferedReader(new InputStreamReader(process.getErrorStream(),charset));
        ){
            /**
             * 正确信息
             */
            String line;
            //如果是阻塞命令，这里也会阻塞
            while ((line = successBuffer.readLine()) != null) {
                if(line.length()==0)continue;
                res.append(line.trim()).append(OSInfo.getLineBreak());
            }
            /**
             * 错误信息--先读取inputStream-读取了inputStream才知道是否成功
             */
            if(process.waitFor()!=0){
                String errorLine;
                StringBuffer errorStr=new StringBuffer();
                while ((errorLine = errorBuffer.readLine()) != null) {
                    if(line.length()==0)continue;
                    errorStr.append(errorLine.trim());
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

    /**
     * 同上 实时写入[队列]resBuffer中一行一行的
     * 视情况使用
     */
    public static void executeRuntimeCommand(String command, LinkedList<String> resBuffer){
        executeRuntimeCommand(command,resBuffer,CharsetUtil.systemCharset());
    }
    public static void executeRuntimeCommand(String command, LinkedList<String> resBuffer,Charset charset){
        new Thread(()->{
            try{
                String[]  cmdArr = getExecCommand(command);
                StaticLog.info("执行命令 {}", StringUtils.join(cmdArr," "));
                Process process = Runtime.getRuntime().exec(cmdArr);
                process.getOutputStream().close();
                try(
                        BufferedReader successBuffer = new BufferedReader(new InputStreamReader(process.getInputStream(),charset));
                        BufferedReader errorBuffer = new BufferedReader(new InputStreamReader(process.getErrorStream(),charset));
                ){
                    /**
                     * 正确信息
                     */
                    String line;
                    //如果是阻塞命令，这里也会阻塞
                    while ((line = successBuffer.readLine()) != null) {
                        resBuffer.push(line);
                    }
                    /**
                     * 错误信息--先读取inputStream-读取了inputStream才知道是否成功
                     */
                    if(process.waitFor()!=0){
                        String errorLine;
                        while ((errorLine = errorBuffer.readLine()) != null) {
                            resBuffer.push(errorLine);
                        }
                    }
                }finally {
                    process.destroy();
                }
            }catch(Exception e){
                resBuffer.push(e.getMessage());
            }
            //结束符号
            resBuffer.push("EOF");
        }).start();
    }
}
