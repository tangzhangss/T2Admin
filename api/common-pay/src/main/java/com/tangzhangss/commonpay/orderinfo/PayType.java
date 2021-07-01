package com.tangzhangss.commonpay.orderinfo;

public enum PayType {

    WeiXin("WeiXin"),
    Alipay("Alipay");

    public String type;

    PayType(String type){
        this.type=type;
    }
}
