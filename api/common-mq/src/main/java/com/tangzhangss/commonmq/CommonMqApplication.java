package com.tangzhangss.commonmq;

import com.tangzhangss.commonutils.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tangzhangss.commonmq", "com.tangzhangss.commonutils"})
public class CommonMqApplication {

    public static void main(String[] args) {
        AuthFilter.addUnAuthUrl("^/message/.*");
        SpringApplication.run(CommonMqApplication.class, args);
    }

}
