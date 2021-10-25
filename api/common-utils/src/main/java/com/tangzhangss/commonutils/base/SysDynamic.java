package com.tangzhangss.commonutils.base;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;

/*
功能: 给实体类添加以下两个注解

@DynamicInsert
@DynamicUpdate


这个注解无用，组合注解只能针对spring的注解 解析注解的方式不一样

20211006
 改成在commandRunner,利用反射动态添加注解,也没用。。。

20211006改成编译时注解
  经搜索，自定义注解处理器比较麻烦，没有必要
 */

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface SysDynamic {
}
