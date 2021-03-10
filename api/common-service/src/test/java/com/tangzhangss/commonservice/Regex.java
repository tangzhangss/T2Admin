package com.tangzhangss.commonservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^https?://.*?\\..*?/(.*)_custom$");
        Matcher matcher =
          pattern.matcher("https://my-admin-v2.oss-cn-chengdu.aliyuncs.com" +
                  "/861136242/picture/27117392470016/1614511951639_2020LOGO.png_custom");

        matcher.matches();
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));
        System.out.println(matcher.group(3));

    }
}
