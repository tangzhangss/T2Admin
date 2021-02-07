package com.tangzhangss.commonutils.exception;

import com.alibaba.fastjson.JSONObject;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.resultdata.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
@PropertySource("application.properties")
public class GlobalException {
    @Value("${custom.debug:false}")
    private Boolean debug;



    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Result notFoundHandler(HttpServletRequest request, Exception e) {
        return new Result(HttpStatus.NOT_FOUND,"访问的页面不存在");
    }

    /**
     * 请求类型不被支持异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public Result methodNotAllowedHandler(HttpServletRequest request, Exception e) {
        JSONObject json = new JSONObject();
        json.put("method",request.getMethod());
        json.put("path",request.getServletPath());
        json.put("message",e.getMessage());
        json.put("class",e.getClass().toString());
        return new Result(HttpStatus.METHOD_NOT_ALLOWED,json);
    }


    /**
     * 业务错误
     * 自定义状态码601
     * 注意:业务异常表示 服务器没有错误 是业务错误，所以请求状态依旧是200
     * @param e 异常实体
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result  serviceExceptionHandler(ServiceException e){
        return new Result(e.getResultCode(),e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result exceptionHandler(HttpServletRequest request,Exception e){
        if (debug) e.printStackTrace();
        JSONObject json = new JSONObject();
        json.put("method",request.getMethod());
        json.put("path",request.getServletPath());
        json.put("message",e.getMessage());
        json.put("class",e.getClass().toString());
        json.put("trace",e.getStackTrace());
        return new Result<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR,json);
    }

}
