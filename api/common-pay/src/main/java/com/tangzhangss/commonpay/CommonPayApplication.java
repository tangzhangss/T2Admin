package com.tangzhangss.commonpay;

import com.tangzhangss.commonpay.alipay.AliPayConfig;
import com.tangzhangss.commonpay.weixin.WeiXinURL;
import com.tangzhangss.commonutils.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tangzhangss.commonpay", "com.tangzhangss.commonutils"})
public class CommonPayApplication {

    public static void main(String[] args) {
        AuthFilter.addUnAuthUrl(WeiXinURL.PAY_NOTIFY.url);
        AuthFilter.addUnAuthUrl(AliPayConfig.NOTIFY_URL);
        SpringApplication.run(CommonPayApplication.class, args);
    }

}
