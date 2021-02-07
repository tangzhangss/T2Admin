package com.tangzhangss.commonutils.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CrosConfigFilter{
    @Bean
    public FilterRegistrationBean crosFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration cros = new CorsConfiguration();

        cros.setAllowCredentials(false);

        //允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源
        cros.addAllowedOrigin("*");

        cros.addAllowedHeader("*");

        cros.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", cros);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        bean.setOrder(0);

        return bean;
    }
}
