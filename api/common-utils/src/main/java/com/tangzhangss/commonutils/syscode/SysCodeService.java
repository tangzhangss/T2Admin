package com.tangzhangss.commonutils.syscode;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.HashMapUtil;
import lombok.val;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成系统编码服务
 */
@Service
public class SysCodeService extends SysBaseService<SysCodeEntity,SysCodeDao> {
    @Override
    public Map<String, String> getCheckFields() {
        return HashMapUtil.createLinkedHashMap().put("noCode","编码代码").get();
    }
    /**
     * 获取定义的流水号
     * PO[YEAR][MONTH][DAY][arg]{4}
     * @param noCode 编号Code
     * @param args 一些额外参数 args.get("arg")的值替换
     *
     * 一次只能一个线程调用
     */
    @Transactional(rollbackFor = Exception.class)
    public String[] getSerialNo(String noCode, JSONObject args, int num){
        // 获取定义的规则
        SysCodeEntity sysCode = getOneCustomWithMapString("noCode@EQ="+noCode);
        if (sysCode==null) {
            ExceptionUtil.throwException("未定义编号规则,请联系管理员");
        }
        String sFormula = sysCode.getFormula();
        if (StringUtils.isBlank(sFormula))ExceptionUtil.throwException("未定义编号规则,请联系管理员");

        // 处理定义的规则,格式: PO[YEAR][MONTH][DAY]{4}
        //解析年月日和参数
        String str = matchCaptionKey(sFormula, args);
        // 匹配位数
        Pattern r = Pattern.compile("\\{(.*?)}");
        Matcher m = r.matcher(str);
        // 存下站位符
        String s = "";
        // 默认6位
        int iS = 6;
        if (m.find()) {
            s = m.group(0);
            iS = Integer.parseInt(m.group(1));
        }
        str = str.replace(s,"");
        // 前面补0
        String iSs = "%0"+iS+"d";
        // 当前序列号
        int iSerialNo = sysCode.getCurrentNo();
        int startSerialNo=iSerialNo;
        iSerialNo+=num;
        sysCode.setCurrentNo(iSerialNo);
        //保存
        myDao.save(sysCode);

        // 前面补0
        String[] iSerialNos= new String[num];
        for (int i = 0; i < num; i++){
            // 前面补0
            iSerialNos[i]=str+String.format(iSs, startSerialNo+i+1);
        }
        return iSerialNos;
    }

    public  String getDeclareSerialNo(String uNoCode, JSONObject args){
        //对这个code加锁 同一个类型的code只能有一个线程去生成编码
        //因为这个是String相当于值一样的都只会是一个对象
        synchronized(uNoCode){
            return getSerialNo(uNoCode,args,1)[0];
        }
    }

    //获取多个流水号
    public String[] getDeclareSerialMultipleNo(String uNoCode, JSONObject args,int num){
        return getSerialNo(uNoCode,args,num);
    }
    /**
     * 匹配格式  解析年月日和参数
     * PO[YEAR][MONTH][DAY][arg1]{4}
     */
    private static String matchCaptionKey(String key,JSONObject args) {
        Pattern r = Pattern.compile("\\[(.*?)]");
        Matcher m = r.matcher(key);
        while (m.find()) {
            String itemKey = m.group(1);
            String val = null;
            // 我们定义的，日期，年，月
            switch (itemKey.toUpperCase()){
                case "YEAR":
                case "YEAR4":{//4位
                    val= Convert.toStr(LocalDate.now().getYear());
                    break;
                }
                case "YEAR2":{//2位
                    val= Convert.toStr(LocalDate.now().getYear()).substring(2);
                    break;
                }
                case "MONTH":{//2位
                    val= String.format("%02d", LocalDate.now().getMonthValue());
                    break;
                }
                case "DAY":{//2位
                    val= String.format("%02d", LocalDate.now().getDayOfMonth());
                    break;
                }
                default:{
                    val = getManyLevelKey(args, itemKey);
                }
            }
            // 替换原来的
            key = key.replace(m.group(0), val==null?"": val);
        }
        return key;
    }
    /**
     * 获取jsonObj里的值
     * @param obj
     * @param key
     * @return
     */
    private static String getManyLevelKey(JSONObject obj, String key) {
        if (obj==null||key==null) return null;
        return obj.getStr(key);
    }

    public static void main(String[] args) {
        System.out.println(matchCaptionKey("PO[YEAR4][MONTH][DAY]{4}",null));
        System.out.println(matchCaptionKey("PO[YEAR][MONTH][DAY]{9}",null));
    }
}
