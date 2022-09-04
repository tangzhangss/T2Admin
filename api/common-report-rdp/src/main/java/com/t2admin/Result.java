package com.t2admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * author:tangzhangss
 */
@ApiModel(description = "通用结果返回实体")
public class Result<T> implements Serializable {
    @ApiModelProperty("状态码，200表示成功,4XX请求错误，5XX服务器端错误，6XX业务异常，1XXX用户端错误")
    private  Integer code;

    @ApiModelProperty("调用结果提示信息，如: OK")
    private String message;

    @ApiModelProperty("返回的数据")
    private T data;

    public Result(HttpStatus httpStatus, T data){
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
    }
    public Result(ResultCode resultCode, T data){
        this.code = resultCode.getCode();
        this.message = resultCode.getTranslatorMessage();
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
