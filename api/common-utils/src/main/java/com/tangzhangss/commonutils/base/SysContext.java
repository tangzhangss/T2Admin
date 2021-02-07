package com.tangzhangss.commonutils.base;

import com.alibaba.fastjson.JSONObject;

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
        try {
            return userContext.get().getLong("id");
        }catch (Exception e) {
            return null;
        }
    }
    /*
    客户ID
     */
    public static String getClientId(){
        try {
            return userContext.get().getString("clientId");
        }catch (Exception e) {
            return null;
        }
    }
}
