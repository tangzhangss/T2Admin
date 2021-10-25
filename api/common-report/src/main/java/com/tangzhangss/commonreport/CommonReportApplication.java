package com.tangzhangss.commonreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.tangzhangss.commonreport", "com.tangzhangss.commonutils"})
public class CommonReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonReportApplication.class, args);
    }

}
