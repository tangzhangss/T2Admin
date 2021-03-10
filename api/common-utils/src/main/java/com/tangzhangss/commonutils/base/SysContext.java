package com.tangzhangss.commonutils.base;

import com.alibaba.fastjson.JSONObject;
import com.tangzhangss.commonutils.utils.ExceptionUtil;

public class SysContext {

    private SysContext(){}

    private static final ThreadLocal<JSONObject> userContext = new ThreadLocal<>();

    public static void setUser(JSONObject user){
        userContext.set(user);
    }

    public static JSONObject getUser() {
        return userContext.get();
    }

    public static void removeUserContext() {
        userContext.remove();
    }

    /*
    用户ID
     */
    public static Long getUserId() {
        if(userContext.get()==null){
            return null;
        }
        return userContext.get().getLong("id");
    }
    /*
    客户ID
     */
    public static String getClientId(){
        if(userContext.get()==null){
            return null;
        }
        return userContext.get().getString("clientId");
    }
}
