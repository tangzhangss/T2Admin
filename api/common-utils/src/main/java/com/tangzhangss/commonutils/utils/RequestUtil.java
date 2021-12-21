package com.tangzhangss.commonutils.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.resultdata.ResultCode;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RequestUtil {
    public static void checkNullParam(JSONObject object,String ...keys){
        List<String> paramArr = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            if(StringUtils.isBlank(object.getStr(keys[i])))
                paramArr.add(keys[i]);
        }
        if(paramArr.size()!=0){
            ExceptionUtil.throwException("check_param_null", ResultCode.BAD_REQUEST, StringUtils.join(paramArr.iterator(),","));
        }

    }
}
