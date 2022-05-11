package com.tangzhangss.commonservice;

import com.tangzhangss.commonutils.filter.AuthFilter;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.tangzhangss.commonutils","com.tangzhangss.commonservice"})
public class CommonServiceApplication {

    public static void main(String[] args) {
        AuthFilter.addUnAuthUrl("/service_test/.*");
        //放开websocket的连接
        AuthFilter.addUnAuthUrl("/websocket/stomp");
        AuthFilter.addUnAuthUrl("/websocket");
        SpringApplication.run(CommonServiceApplication.class, args);
    }


    @Value("${http.port}")
    private Integer port;
    @Value("${server.port}")
    private Integer sslPort;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(port);
        connector.setSecure(false);
        connector.setRedirectPort(sslPort);
        return connector;
    }
    /**
     * 创建wss协议接口
     */
    @Bean
    public TomcatContextCustomizer tomcatContextCustomizer() { return context -> context.addServletContainerInitializer(new WsSci(), null);
    }
}
