package com.tangzhangss.commonutils.resultdata;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * author:tangzhangss
 */
public class Result<T> implements Serializable {
    private  Integer code;
    private String message;
    private T data;

    public Result(HttpStatus httpStatus, T data){
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
    }
    public Result(ResultCode resultCode, T data){
        this.code = resultCode.code;
        this.message = resultCode.message;
        this.data = data;
    }

    public Result(){
        //...
    }

    public static Result ok(){
        return new Result(HttpStatus.OK,null);
    }

    public Result data(T data){
        this.data=data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
