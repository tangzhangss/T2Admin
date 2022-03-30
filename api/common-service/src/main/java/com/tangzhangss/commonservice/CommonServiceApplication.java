package com.tangzhangss.commonservice;

import com.tangzhangss.commonutils.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.tangzhangss.commonutils","com.tangzhangss.commonservice"})
public class CommonServiceApplication {

    public static void main(String[] args) {
        AuthFilter.addUnAuthUrl("/service_test/.*");
        SpringApplication.run(CommonServiceApplication.class, args);
    }

}
