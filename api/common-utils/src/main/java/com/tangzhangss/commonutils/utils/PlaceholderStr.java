package com.tangzhangss.commonutils.utils;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderStr{
    /*
           占位符构建#{0},#{1},#{2} 对应后面的参数
           因为要取数组下标所以必须大于1
           args的长度+1是结尾标志
      */
    public static String parseStr(String str,String ...args){
        //默认构建策略-简单拼接
        return parse(str,Strategy.SIMPLE_COMBINE,args);
    }
    /*
     根据构建策略构建
     */
    public static String parseStr(String str,Strategy strategy,String ...args){
        return parse(str,strategy,args);
    }

    /*
    解析核心方法
    */
    private static String parse(String str,PlaceholderStr.Strategy strategy,String ...args){
        //-------------------
        //添加结尾标志，苦思许久不知怎么样才能捕获到 占位符后面的数据，故:采用这种方式
        str += "#{"+args.length+"}";
        //-------------------
        Pattern pattern = Pattern.compile("(.*?)#\\{(\\d+)}");
        Matcher matcher = pattern.matcher(str);
        StringBuffer result = new StringBuffer();
        while (matcher.find()){
            String frontPart = matcher.group(1);//前面部分
            //占位符里面的index，即对应的args数组的索引
            int index = Integer.parseInt(matcher.group(2));
            //校验是否是结尾符或者索引越界
            String value= index>=args.length?"":args[index];
            //根据解析策略,解析
            String parseRes = strategy.parse(frontPart,value);
            result.append(parseRes);
        }

        return  result.toString();
    }
    public enum Strategy{
        //简单拼接
        SIMPLE_COMBINE("simpleCombine"),
        //添加空格
        SPACE_COMBINE("spaceCombine"),
        ;

        private BiFunction<String,String,String> func;//解析方法
        /*
        简单拼接
         */
        private BiFunction<String,String,String> simpleCombine = (frontPart, value)->frontPart+value;
        /*
        添加空格
         */
        private BiFunction<String,String,String> spaceCombine = (frontPart, value)-> {
            if (StringUtils.isNotBlank(value))value = " "+value+" ";
            return frontPart+value;
        };

        /*
         添加新的策略需要修改这里和添加function
         */
        Strategy(String funcName){
            try {
                Field field = this.getDeclaringClass().getDeclaredField(funcName);
                field.setAccessible(true);
                this.func = (BiFunction<String, String, String>) field.get(this);
            } catch (Exception e) {
                e.printStackTrace();
                //如果获取方法不存在或则其他异常---将func设置为simpleCombine
                this.func = simpleCombine;
            }

        }

        public String parse(String frontPart, String value){
            return this.func.apply(frontPart,value);
        }
    }

}
