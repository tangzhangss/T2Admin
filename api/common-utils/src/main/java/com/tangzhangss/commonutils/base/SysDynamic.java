package com.tangzhangss.commonutils.base;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.lang.annotation.*;


@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@DynamicInsert
@DynamicUpdate
@Target(ElementType.TYPE)
public @interface SysDynamic {}
