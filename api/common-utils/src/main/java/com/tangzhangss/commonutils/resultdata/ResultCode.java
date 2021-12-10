package com.tangzhangss.commonutils.resultdata;

import com.tangzhangss.commonutils.i18n.Translator;
import lombok.Data;

public enum ResultCode {
    //请求错误
    BAD_REQUEST(400,"bad_request"),
    
    //业务处理失败600-699
    BUSINESS_PROCESSING_FAILED(601,"processing_fail"),
    BUSINESS_REMOTE_CALL_FAILED(602,"远程调用失败"),

    //用户输入出错1000-1999
    USER_LOGIN_FAILED(1001, "login_fail"),
    ;


    private int code;
    private String message;

    ResultCode(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode(){
        return code;
    }
    public String getTranslatorMessage() {
        return Translator.get(message);
    }
}
