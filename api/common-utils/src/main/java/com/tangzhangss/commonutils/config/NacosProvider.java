package com.tangzhangss.commonutils.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 之前用的springboot的 需要自己注册，换成spring cloud的就不用了
 */
//@Component
public class NacosProvider implements CommandLineRunner {
    @NacosInjected
    private NamingService namingService;

    @Value("${spring.application.name:none}")
    private String applicationName;
    @Value("${spring.application.ip:none}")
    private String applicationIp;
    @Value("${server.port}")
    private Integer serverPort;

    @Override
    public void run(String... args) throws Exception {
        // 通过Naming服务注册实例到注册中心
        if(!applicationName.equals("none")&&!applicationIp.equals("none")){
            namingService.registerInstance(applicationName, applicationIp, serverPort);
        }
    }
}
