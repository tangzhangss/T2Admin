package com.tangzhangss.commonflowable.config;

import lombok.Data;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class ProcessEngineConfig {
    private String url;
    private String driverClassName;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bean
    public ProcessEngine initProcessEngine(){
        ProcessEngineConfiguration cfg = null;
        //流程引擎配置
        cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcDriver(driverClassName)
                .setJdbcUrl(url)
                .setJdbcPassword(password)
                .setJdbcUsername(username)
                //初始化基础表
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
                //邮箱设置
                .setMailServerHost("smtp.163.com")
                .setMailServerPassword("ZNKTPCJGHFLVGLBB")
                .setMailServerDefaultFrom("it_tangzhang@163.com")
                //流程图乱码
                .setActivityFontName("宋体")
                .setLabelFontName("宋体")
                .setAnnotationFontName("宋体");

        return cfg.buildProcessEngine();
    }
}
