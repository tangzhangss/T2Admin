package com.tangzhangss.commonutils.exception;

import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSONObject;
import com.tangzhangss.commonutils.i18n.Translator;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.resultdata.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
@PropertySource("application.properties")
public class GlobalException {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Result notFoundHandler(HttpServletRequest request, Exception e) {
        return new Result(HttpStatus.NOT_FOUND, Translator.get("not_found"));
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
     * 自定义状态码6xx
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
        StaticLog.error(e);
        JSONObject json = new JSONObject();
        json.put("method",request.getMethod());
        json.put("path",request.getServletPath());
        json.put("message",e.getMessage());
        json.put("class",e.getClass().toString());
        json.put("trace",e.getStackTrace());
        return new Result<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR,json);
    }

    /**
     * 校验失败异常
     * 注解里面的message请用 Translator.get("xxx")
     * @param exception 异常类
     * @return Result
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handle(MethodArgumentNotValidException exception){
        Result result = new Result(ResultCode.BAD_REQUEST,exception.getBindingResult().getFieldError().getDefaultMessage());
        return  result;
    }

}
