package com.tangzhangss.commonutils.exception;

import com.tangzhangss.commonutils.resultdata.ResultCode;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ServiceException extends  RuntimeException{

    private ResultCode resultCode;
    private String message;

    public ServiceException() {
        //...
    }

    public ServiceException(String message) {
        this.message=message;
        this.resultCode=ResultCode.BUSINESS_PROCESSING_FAILED;
    }
    public ServiceException(String message,ResultCode resultCode) {
        this.message=message;
        this.resultCode=resultCode;
    }
}
