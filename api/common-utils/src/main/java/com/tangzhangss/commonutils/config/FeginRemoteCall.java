package com.tangzhangss.commonutils.config;

import com.tangzhangss.commonutils.resultdata.Result;

/**
 * fegin远程调用接口封装调用处理过程
 */
@FunctionalInterface
public interface FeginRemoteCall{
    Result call();
}
