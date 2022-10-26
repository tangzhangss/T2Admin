package com.tangzhangss.commonutils;

import com.tangzhangss.commonutils.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonUtilsApplication {

    public static void main(String[] args) {
        AuthFilter.addUnAuthUrl("^/test.*");

        SpringApplication.run(CommonUtilsApplication.class);
    }
}
