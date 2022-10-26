package com.tangzhangss.commonutils.base;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;

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
    租户ID
     */
    public static String getClientId(){
        if(userContext.get()==null){
            return null;
        }
        return userContext.get().getStr("clientId");
    }
    /**
     * 获取用户权限--都好分割的字符窜
     */
    public static String[] getAuthorizeSet(){
        if(userContext.get()==null){
            return null;
        }
        JSONArray authorizeSet = userContext.get().getJSONArray("authorizeSet");
        if(authorizeSet==null)return new String[0];

        return authorizeSet.toArray(new String[authorizeSet.size()]);
    }
}
