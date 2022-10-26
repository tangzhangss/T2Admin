package com.tangzhangss.commonutils.utils;

import com.tangzhangss.commonutils.exception.ServiceException;
import com.tangzhangss.commonutils.i18n.Translator;
import com.tangzhangss.commonutils.resultdata.ResultCode;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理异常工具类
 */
public class ExceptionUtil {


    /**
     * 抛出业务异常信息，国际化
     */
    public static void throwException(String str,String ...args){
        throw  new ServiceException(Translator.get(str, args));
    }
    public static void throwException(String str,ResultCode resultCode,String ...args){
        throw  new ServiceException(Translator.get(str, args),resultCode);
    }

    /*
      //占位符构建#0,#1,#2 对应后面的参数,不参与翻译
    */
    public static void throwException(String str,ResultCode resultCode,PlaceholderStr.Strategy strategy,String ...args){
        throw  new ServiceException(PlaceholderStr.parseStr(str,strategy,args),resultCode);
    }
    public static void throwException(String str,PlaceholderStr.Strategy strategy,String ...args){
        throw  new ServiceException(PlaceholderStr.parseStr(str,strategy,args));
    }

    public static void main(String[] args) {
//        throwException("你好，我是#{0},我喜欢#{1},我计划#{2}表白,你支持我吗？#{3}#{4}，好的呀!#{5}",
//                "唐糖","zjb","2020-12-10","我也喜欢你!","真的喲!","哈哈哈!");

        throwException("你好，我是#{0},我喜欢#{1},我计划#{2}表白,你支持我吗？#{3}#{4}，好的呀!#{5}",
                PlaceholderStr.Strategy.SPACE_COMBINE,
                "唐糖","zjb","2020-12-10","我也喜欢你!","真的喲!","哈哈哈!");

//        throwException("你好，我是我喜欢我计划表白,你支持我吗？好的呀!");
    }


}
