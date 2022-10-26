package com.tangzhangss.commonutils.aspect.preauthorize;

import com.tangzhangss.commonutils.annotation.PreAuthorize;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.config.Attribute;
import com.tangzhangss.commonutils.resultdata.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Aspect
@Service
public class PreAuthorizeService extends SysBaseService<PreAuthorizeEntity,PreAuthorizeDao> {

    @Pointcut("@annotation(com.tangzhangss.commonutils.annotation.PreAuthorize)")
    public void preAuthorizePointCut() {}

    @Around("preAuthorizePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //超级管理员不需要校验
        if(!SysContext.getClientId().equals(Attribute.SUPER_ADMIN_CLIENT_ID)){
            //校验权限
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
            if(preAuthorize!=null){
                String value = preAuthorize.value();
                //检查用户是否有这个权限
                String[] authorizeSet = SysContext.getAuthorizeSet();
                //循环用户权限校验是否有改接口的权限
                boolean forbidden=true;
                for (String authorize : authorizeSet) {
                    //拥有权限
                    String[] a = authorize.split(":");
                    //需要权限
                    String[] b = value.split(":");
                    if(a.length>b.length){//a:b:c 不能访问 a:b  *:*:* 只能访问位数大于等于的路径
                        continue;
                    }
                    //可通过判断  相同位数不是*也不同 == 不匹配
                    for (int i = 0; i < b.length; i++) {
                        if(a[i].equals("*")){
                            forbidden=false;//a:* 使用 a:xx:xxx
                        }else if (!a[i].equals(b[i])){ // a:a1 不适用 a:b1
                             //相同位数不是*也不同 == 不匹配
                             break;
                        }
                    }
                    if(!forbidden)break;
                }
                if(forbidden){
                    return new Result(HttpStatus.UNAUTHORIZED,"no interface permission");
                }
            }
        }
        //执行方法
        Object result = point.proceed();
        return result;
    }
}
