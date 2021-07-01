package com.tangzhangss.commonpay.weixin;

import org.springframework.beans.factory.annotation.Value;

public enum  WeiXinURL {
    /**
     * get
     * 获取微信的 全局唯一后台接口调用凭据（access_token）_网页授权的
     */
    ACCESS_TOKEN("https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code"),

    /**
        get
        获取微信的 全局唯一后台接口调用凭据（access_token）_普通
     */
    API_ACCESS_TOKEN("https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code"),

    /**
     * post 微信统一下单
     */
    UNIFIED_ORDER("https://api.mch.weixin.qq.com/pay/unifiedorder"),

    /**
     * 退款地址
     */
    REFUND("https://api.mch.weixin.qq.com/secapi/pay/refund"),

    /**
     * 微信支付异步通知地址
     */
    PAY_NOTIFY("/weixin/pay_notify")
    ;

    public String url;


    WeiXinURL(String url){
        this.url=url;
    }
}
