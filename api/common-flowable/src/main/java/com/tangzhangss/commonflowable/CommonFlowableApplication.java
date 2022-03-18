package com.tangzhangss.commonflowable;

import com.tangzhangss.commonutils.filter.AuthFilter;
import org.flowable.ui.common.security.ApiHttpSecurityCustomizer;
import org.flowable.ui.common.security.DefaultApiHttpSecurityCustomizer;
import org.flowable.ui.common.security.FlowableUiSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.tangzhangss")
@SpringBootApplication(exclude = {FlowableUiSecurityAutoConfiguration.class})
public class CommonFlowableApplication {

    public static void main(String[] args) {
        AuthFilter.addUnAuthUrl("^.*");
        SpringApplication.run(CommonFlowableApplication.class, args);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "flowable.common.app.security",
            name = {"type"},
            havingValue = "idm",
            matchIfMissing = true
    )
    public ApiHttpSecurityCustomizer defaultApiHttpSecurityCustomizer() {
        return new DefaultApiHttpSecurityCustomizer();
    }

}
