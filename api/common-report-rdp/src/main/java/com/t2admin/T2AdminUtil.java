package com.t2admin;

import io.report.common.utils.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Component
public class T2AdminUtil {

    @Autowired
    HttpServletRequest request;

    public static String getDataPath(boolean relativePath, String datapath) {
        String path = "";
        String clientId= SysContext.getClientId();
        if (relativePath) {
            path = ServerUtil.getServerPath() + File.separator +datapath;
        } else {
            path = datapath;
        }
        path = path+ File.separator +clientId+File.separator;
        //创建目录
        File filePath=new File(path);
        if (!filePath.exists()){ filePath.mkdirs(); }

        return path;
    }
    public static String getDataPath(boolean relativePath, String datapath, String dirPath) {
        String path = "";
        String clientId= SysContext.getClientId();
        if (relativePath) {
            path = ServerUtil.getServerPath() + datapath;
        } else {
            path = datapath;
        }
        if (!"".equals(path) && !path.endsWith("/")) {
            path = path + "/";
        }
        path = path + dirPath;
        path = path + File.separator +clientId+File.separator;
        //创建目录
        File filePath=new File(path);
        if (!filePath.exists()){ filePath.mkdirs(); }

        return path;
    }
    public static String handleRealPath(String realPath) {
        String clientId= SysContext.getClientId();
        //如果是空的就只能报错
        return realPath+File.separator+clientId+File.separator;
    }
    public static String getClientId() {
        String clientId= SysContext.getClientId();
        //如果是空的就取缓存就只能报错
        return clientId;
    }

}
