package com.tangzhangss.commonutils.config;

import com.sun.xml.bind.v2.model.core.ID;

/**
 * 全局属性定义
 */
public class Attribute {
    public static final long SUPER_ADMIN_ID=10000;//超级管理员的用户ID
    public static final String SUPER_ADMIN_CLIENT_ID="tzcc_ren";//超级管理员的客户ID
    public static final String MD5_SALT="TZCC-REN ADMIN";//md5加密的盐

    public static final String NO_AUTH_CLIENT_ID="-1";//没有登录的用户的clientId
    public static final long NO_AUTH_USER_ID=-1;//没有登录的用户id
    public static final String NO_AUTH_USER_NAME="匿名";//没有登录的用户名字

}
