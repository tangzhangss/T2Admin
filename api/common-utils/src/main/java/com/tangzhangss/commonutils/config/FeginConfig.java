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
//                Result result = JSONUtil.toBean(json, Result.class);
//                System.out.println("fegin调用发生错误:"+json);
            } catch (IOException ex) {
                ex.printStackTrace();
                exception=ex;
            }
            return exception;
        }
    }

    public static Result apply(FeginRemoteCall feginRemoteCall,String serverName,boolean isHandle){
        return applyExec(feginRemoteCall,serverName,isHandle);
    }
    public static Result apply(FeginRemoteCall feginRemoteCall,String serverName){
        return applyExec(feginRemoteCall,serverName,false);
    }

    /**
     * 执行调用处理
     * @param feginRemoteCall 调用方法
     * @param serverName 调用外部服务的 spring.application.name （服务名称）
     * @param isHandle
     *
     * 将外部服务的结构原样返回，成功的结果类似,如：
     *
     *     {
     *         "code": 404,
     *         "message": "Not Found",
     *         "data": "访问的页面不存在"
     *     }
     *
     * 如果apply的第二个参数为true,将再次包裹Result直接抛出异常全局捕获事务回滚处理等，异常信息如下:
     *
     *     {
     *         "code": 602,
     *         "message": "远程调用失败",
     *         "data": "{\"result\":{\"code\":404,\"data\":\"访问的页面不存在\",\"message\":\"Not Found\"},\"service\":\"COMMON-DATA\"}"
     *     }
     *
     * @return result 正常结果返回
     */
    private static Result applyExec(FeginRemoteCall feginRemoteCall,String serverName, boolean isHandle){
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
            JSONObject jsonRes = new JSONObject().set("service",serverName).set("result", result);
            ExceptionUtil.throwException(JSONUtil.toJsonStr(jsonRes),ResultCode.BUSINESS_REMOTE_CALL_FAILED);
        }
        return result;
    }

}
