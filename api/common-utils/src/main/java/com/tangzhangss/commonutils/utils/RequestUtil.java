package com.tangzhangss.commonutils.utils;

import cn.hutool.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class RequestUtil {
    public static void checkNullParam(JSONObject object,String ...keys){
        for (int i = 0; i < keys.length; i++) {
            if(StringUtils.isBlank(object.getStr(keys[i])))
                ExceptionUtil.throwException("参数:"+keys[i]+",不能为空!");
        }
    }
}
