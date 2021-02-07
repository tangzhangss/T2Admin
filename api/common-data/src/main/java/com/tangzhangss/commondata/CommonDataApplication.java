package com.tangzhangss.commondata;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tangzhangss.commondata", "com.tangzhangss.commonutils"})
public class CommonDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonDataApplication.class, args);
    }
}
