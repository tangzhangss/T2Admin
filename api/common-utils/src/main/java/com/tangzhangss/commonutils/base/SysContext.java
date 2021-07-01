package com.tangzhangss.commonutils.base;


import cn.hutool.json.JSONObject;

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
    用户名
     */
    public static String getUserName() {
        if(userContext.get()==null){
            return null;
        }
        return userContext.get().getStr("name");
    }

    /*
    客户ID
     */
    public static String getClientId(){
        if(userContext.get()==null){
            return null;
        }
        return userContext.get().getStr("clientId");
    }
}
