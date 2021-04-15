package com.tangzhangss.commonutils.config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.resultdata.ResultCode;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import feign.hystrix.FallbackFactory;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
public class FeginConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }
    /**
     * 自定义错误--将fegin的返回结果全部不变的返回
     */
    public class UserErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                // 获取原始的返回内容
                String json = Util.toString(response.body().asReader(Charset.forName("utf-8")));
                //直接将结果返回去
                exception = new HystrixBadRequestException(json);
                // 将返回内容反序列化为Result，这里应根据自身项目作修改
                Result result = JSONUtil.toBean(json, Result.class);
                System.out.println("fegin调用发生错误:"+json);
            } catch (IOException ex) {
                ex.printStackTrace();
                exception=ex;
            }
            return exception;
        }
    }

    public static Result apply(FeginRemoteCall feginRemoteCall,boolean isHandle){
        return applyExec(feginRemoteCall,true);
    }
    public static Result apply(FeginRemoteCall feginRemoteCall){
        return applyExec(feginRemoteCall,false);
    }

    /**
     * 执行调用处理
     * @param feginRemoteCall 调用方法
     * @param isHandle 是否抛出异常
     * @return result 正常结果返回
     */
    private static Result applyExec(FeginRemoteCall feginRemoteCall, boolean isHandle){
        Result result=null;
        try{
            result = feginRemoteCall.call();
        }catch (Exception e){
            if(e instanceof HystrixBadRequestException){
                result = JSONUtil.toBean(e.getMessage(),Result.class);
            }else{
                e.printStackTrace();
                result = new Result(ResultCode.BUSINESS_REMOTE_CALL_FAILED,e.getMessage());
            }
        }
        if(result.getCode()!=200 && isHandle){
            JSONObject jsonRes = new JSONObject().set("service", "COMMON-DATA").set("result", result);
            ExceptionUtil.throwException(JSONUtil.toJsonStr(jsonRes),ResultCode.BUSINESS_REMOTE_CALL_FAILED);
        }
        return result;
    }

}
