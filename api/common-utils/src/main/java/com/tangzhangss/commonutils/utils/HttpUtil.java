package com.tangzhangss.commonutils.utils;

import cn.hutool.log.StaticLog;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class HttpUtil {
    public static String getBodyStr(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try (InputStream inputStream = request.getInputStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            StaticLog.error(e.getMessage());
        }
        return sb.toString();
    }
}
