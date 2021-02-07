package com.tangzhangss.commonutils.base;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.lang.annotation.*;

@DynamicInsert
@DynamicUpdate
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SysDynamic{}
