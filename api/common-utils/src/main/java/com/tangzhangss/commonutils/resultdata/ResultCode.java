package com.tangzhangss.commonutils.resultdata;

import lombok.Data;

public enum ResultCode {
    //业务处理失败600-699
    BUSINESS_PROCESSING_FAILED(601,"处理失败"),
    BUSINESS_REMOTE_CALL_FAILED(602,"远程调用失败"),

    //用户输入出错1000-1999
    USER_LOGIN_FAILED(1001,"登录失败"),
    ;


    int code;
    String message;

    ResultCode(int code,String message){
        this.code=code;
        this.message=message;
    }
}
