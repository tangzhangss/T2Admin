package com.tangzhangss.commonutils.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class DateUtil {
    public final static DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER  =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 时间戳转LocalDateTime
     * @param timestamp 时间戳  秒级别的
     * @return -
     */
    public static LocalDateTime parseTimestamp(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }
}
