package com.tangzhangss.commonutils.aspect.syslog;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.utils.BaseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 系统日志，切面处理类
 * +服务层
 */
@Aspect
@Service
public class SysLogService extends SysBaseService<SysLogEntity, SysLogDao> {

    @Pointcut("@annotation(com.tangzhangss.commonutils.aspect.syslog.SysLog)")
    public void logPointCut() {}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveSysLog(point, time, JSONUtil.toJsonPrettyStr(result));
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time, String result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogEntity sysLogEntity = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLogEntity.setOperation(syslog.value());
        }
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setMethod(className + "." + methodName + "()");
        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONUtil.toJsonPrettyStr(args);
            sysLogEntity.setParams(params);
        } catch (Exception e) {
        }
        //获取request
        // 开始打印请求日志
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //设置IP地址
        sysLogEntity.setIp(BaseUtil.getIpAddress(request));
        sysLogEntity.setTime(time);
        sysLogEntity.setId(uidGeneratorService.getuid());
        //设置返回结果
        sysLogEntity.setResult(result);

        //保存系统日志
        myDao.save(sysLogEntity);
    }
}
