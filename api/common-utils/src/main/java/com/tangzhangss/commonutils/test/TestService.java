package com.tangzhangss.commonutils.test;

import com.tangzhangss.commonutils.base.SysBaseService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TestService extends SysBaseService<TestEntity,TestDao>{
    public void test(){
        System.out.println(myDao.toString());
    }

    public static void main(String[] args) {
        String str1 = "A.B  as   as 1";
        String str2 = "A.Bas str2";
        String str3 = "A.B asstr3";
        String str4 = "A.Basstr4";
        Pattern pattern = Pattern.compile("^(.+?)\\s+as\\s+(.+)$");
        Matcher matcher = pattern.matcher(str1);
        if (matcher.find()){
            System.out.println(matcher.group(0));
            System.out.println(matcher.groupCount());
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }

    }
}
