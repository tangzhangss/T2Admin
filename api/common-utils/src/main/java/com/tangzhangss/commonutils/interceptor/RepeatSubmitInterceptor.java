package com.tangzhangss.commonutils.interceptor;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.tangzhangss.commonutils.annotation.RepeatSubmit;
import com.tangzhangss.commonutils.constants.RedisConstants;
import com.tangzhangss.commonutils.filter.AuthFilter;
import com.tangzhangss.commonutils.filter.RepeatedlyRequestWrapper;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.service.RedisService;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交拦截器
 * 重复提交:请求尚未完成（接口返回）再次发起 且 请求间隔时间小于  INTERVAL_TIME
 * 重复规则【参数 token url】
 */
@Component
public class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    RedisService redisService;

    public final String REPEAT_PARAMS = "params";
    public final String REPEAT_BODY = "body";
    public final String REPEAT_TIME = "repeatTime";

    /**
     * 间隔时间，单位:秒 默认10秒
     * <p>
     * 两次相同url和参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     *
     */
    public static  long INTERVAL_TIME = 10;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {

                if (this.isRepeatSubmit(request)) {
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().print(JSONUtil.toJsonStr(new Result(HttpStatus.TOO_MANY_REQUESTS,null)));
                    response.getWriter().close();
                    return false;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private String getCacheRepeatKey(HttpServletRequest request){
        String params = JSONUtil.toJsonStr(request.getParameterMap());

        String data = params;

        if (request instanceof RepeatedlyRequestWrapper) {
            data += HttpUtil.getBodyStr(request);
        }

        StringBuffer key = new StringBuffer()
                .append(request.getRequestURI()).append(request.getHeader(AuthFilter.AUTH))
                .append(data);

        return RedisConstants.REPEAT_SUBMIT_KEY+SecureUtil.md5(key.toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                   @Nullable Exception ex){
        // 唯一标识（指定key + 消息头）
        String cacheRepeatKey = getCacheRepeatKey(request);
        redisService.remove(cacheRepeatKey);
    }
    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     */
    public boolean isRepeatSubmit(HttpServletRequest request){

        //获取redis key
        String cacheRepeatKey = getCacheRepeatKey(request);
        //存在就是重复提交，否则就不是
        return !redisService.setIfAbsent(cacheRepeatKey,"LOCK", INTERVAL_TIME);
    }


    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }
    /**
     * 判断body是否相同
     */
    private boolean compareBody(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowBody = (String) nowMap.get(REPEAT_BODY);
        String preBody = (String) preMap.get(REPEAT_BODY);
        return nowBody.equals(preBody);
    }
    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        if ((time1 - time2) < (INTERVAL_TIME * 1000)) {
            return true;
        }
        return false;
    }
}

