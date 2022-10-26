package com.tangzhangss.commonutils.annotation;

import java.lang.annotation.*;

/**
 * 接口权限---当前用户角色没有接口的权限不能访问--
 *
 * 用作在菜单权限之下需要细分的权限操作校验
 *
 * * 通配符可占以为匹配所有路径值
 * 短路路径 可 匹配长路径
 * a:b:c 不能访问 a:b  *:*:* 只能访问位数大于等于的路径
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {
    /**
     * @return the Spring-EL expression to be evaluated before invoking the protected
     * method
     */
    String value();
}
