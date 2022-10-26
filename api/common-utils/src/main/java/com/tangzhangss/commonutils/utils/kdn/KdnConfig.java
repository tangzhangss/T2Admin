package com.tangzhangss.commonutils.utils.kdn;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Configuration
@ConfigurationProperties(prefix="kdn.config")
public class KdnConfig {
    //用户ID，快递鸟提供
    private String businessId;
    //API key，快递鸟提供
    private String apiKey;
    //请求url, 正式环境地址
    private String reqURL;
    private boolean isProEnv;//是否正式环境
    public KdnConfig() {
    }
    public KdnConfig(String businessId, String apiKey, boolean isProEnv) {
        this.setBusinessId(businessId);
        this.setApiKey(apiKey);
        this.setProEnv(isProEnv);
    }

    @PostConstruct
    public void initReqUrl(){
        this.setReqURL(this.isProEnv?"https://api.kdniao.com/api/EOrderService":
                "http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json");
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getReqURL() {
        return reqURL;
    }

    public void setReqURL(String reqURL) {
        this.reqURL = reqURL;
    }

    public boolean isProEnv() {
        return isProEnv;
    }

    public void setProEnv(boolean proEnv) {
        isProEnv = proEnv;
        this.initReqUrl();
    }

    @Override
    public String toString() {
        return "KdnConfig{" +
                "businessId='" + businessId + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", reqURL='" + reqURL + '\'' +
                ", isProEnv=" + isProEnv +
                '}';
    }
}
