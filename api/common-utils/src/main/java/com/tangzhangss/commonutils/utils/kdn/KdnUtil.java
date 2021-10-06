package com.tangzhangss.commonutils.utils.kdn;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.HtmlUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 快递鸟接口工具
 */
public class KdnUtil {
    public static void main(String[] args) throws Exception {
        // 组装应用级参数
        String requestData= "{"+
                "'OrderCode': '012657018199',"+
                "'ShipperCode': 'SF',"+
                "'CustomerName': '客户编码',"+
                "'CustomerPwd': '',"+
                "'MonthCode': '密钥',"+
                "'SendSite': '',"+
                "'PayType': 1,"+
                "'MonthCode': '1234567890',"+
                "'ExpType': 1,"+
                "'Cost': 1.0,"+
                "'OtherCost': 1.0,"+
                "'Sender': {"+
                "'Company': 'LV',"+
                "'Name': 'Taylor',"+
                "'Mobile': '15018442396',"+
                "'ProvinceName': '上海',"+
                "'CityName': '上海市',"+
                "'ExpAreaName': '青浦区',"+
                "'Address': '明珠路'"+
                "},"+
                "'Receiver': {"+
                "'Company': 'GCCUI',"+
                "'Name': 'Yann',"+
                "'Mobile': '15018442396',"+
                "'ProvinceName': '北京',"+
                "'CityName': '北京市',"+
                "'ExpAreaName': '朝阳区',"+
                "'Address': '三里屯街道'"+
                "},"+
                "'Commodity': ["+
                "{"+
                "'GoodsName': '鞋子',"+
                "'Goodsquantity': 1,"+
                "'GoodsWeight': 1.0"+
                "},"+
                "{"+
                "'GoodsName': '衣服',"+
                "'Goodsquantity': 1,"+
                "'GoodsWeight': 1.0"+
                "}"+
                "],"+
                "'AddService': ["+
                "{"+
                "'Name': 'INSURE',"+
                "'Value': '1000'"+
                "},"+
                "{"+
                "'Name': 'COD',"+
                "'Value': '1020',"+
                "'CustomerID ': '1234567890'"+
                "}"+
                "],"+
                "'Weight': 1.0,"+
                "'Quantity': 1,"+
                "'Volume': 0.0,"+
                "'IsReturnPrintTemplate':1,"+
                "'Remark': '小心轻放'"+
                "}";

        KdnConfig config = new KdnConfig(
                "test1708227","ed7cfa4b-6f20-4c20-8f19-a4f201586860",false);

        System.out.println(getOrderOnlineJpg(requestData,config));

    }


    public static String getOrderOnlineJpg(String requestData, KdnConfig config) throws Exception {
        return  getOrderOnlineJpgExec(requestData,config,null);
    }
    public static String getOrderOnlineJpg(String requestData, KdnConfig config,File imageFile) throws Exception {
        return  getOrderOnlineJpgExec(requestData,config,imageFile);
    }
    public static String getOrderOnlineJpgExec(String requestData, KdnConfig config, File imageFile) throws Exception {
        JSONObject jsonObject = getOrderOnline(requestData,config);
        String htmlTemplate = jsonObject.getStr("PrintTemplate");

        //快递类型
        String shipperCode = jsonObject.getJSONObject("Order").getStr("ShipperCode");

        HtmlUtil.ImageConfig imageConfig = handleHtmlTemplate(shipperCode,htmlTemplate);
        imageConfig.suffix="jpg";
        return  HtmlUtil.htmlToImage(imageConfig,imageFile);
    }


    private static HtmlUtil.ImageConfig handleHtmlTemplate(String shipperCode, String htmlTemplate) {
        /*
            去除\r\n
         */
        String htmlStr = htmlTemplate.replaceAll("\r|\n*","");
        /*
        特殊字符转换
         */
        htmlStr = htmlStr.replaceAll("&nbsp;","&#160;");
        /*
           快递返回的html 自关闭标签没有结束符号 java2DRenderer解析不了
        */
        htmlStr = addElementCloseTag(htmlStr).toString();

        int width = 377;
        int height = 680;
        switch (shipperCode.toUpperCase()){
            case "SF":{//顺丰
                height=547;
                 /*
                  单独处理-如果正则匹配不到会卡死

                   清除td标签的position:absolute属性-不能解析-

                   将其换成div包裹

                   <td style="vertical-align: middle; text-align: right; position: absolute;">
                   td 里面的不能解析 position: absolute
                 */
                htmlStr = handlePositionAttrAtTdTag(htmlStr).toString();
                break;
            }
            case "ZTO":{//中通快递
                height=580;
                break;
            }
            case "YTO":{//圆通快递
                break;
            }
            default:
        }

        return new HtmlUtil.ImageConfig(htmlStr,width,height);
    }
    /**
     *  td 里面的不能解析 position: absolute 一些浏览器不支持td标签position
     *
     *  清除td标签的position:absolute属性-不能解析-
     *
     *  暂时换成 relative
     *
     */
    public static StringBuffer handlePositionAttrAtTdTag(String htmlStr) {
        StringBuffer res = new StringBuffer();
        Matcher matcher = Pattern.compile("(.*?)(<td [^>]*?style=[^>]*?position:\\s*absolute;.*?>)").matcher(htmlStr);
        int end = 0;
        while (matcher.find()){
            res.append(matcher.group(1));
            String s = matcher.group(2);
            int i  = s.indexOf("position");
            if(i>0){
                s = StrUtil.sub(s,0,54)+"\">";
            }
            res.append(s);
            end = matcher.end();
        }
        res.append(htmlStr.substring(end));
        return res;
    }
    /**
     * 给html的自闭合标签添加结束标签
     * 需要在之前去掉\r\n
     */
    public static StringBuffer addElementCloseTag(String htmlTemplate){
        StringBuffer htmlStr = new StringBuffer();
        String[] tags = "meta,link,base,br,hr,input,img".split(",");
        String[] tagArr= new String[tags.length];
        for (int i = 0; i < tags.length; i++) {
            tagArr[i]="(?:"+tags[i]+")";
        }
        String tagsRegex = StringUtils.join(tagArr,"|");
        Matcher matcher = Pattern.compile("(.*?)(<(?:"+tagsRegex+").*?>)").matcher(htmlTemplate);
        int end = 0;
        while (matcher.find()){
            htmlStr.append(matcher.group(1));
            if(!matcher.group(2).endsWith("/>")){
                htmlStr.append(matcher.group(2).replace(">","/>"));
            }else{
                htmlStr.append(matcher.group(2));
            }
            end = matcher.end();
        }
        htmlStr.append(htmlTemplate.substring(end));
        return htmlStr;
    }

    /**
     * 获取电子面单数据
     * @param requestData 快递鸟用户数据
     * @param config 用户配置信息
     */
    public static JSONObject getOrderOnline(String requestData, KdnConfig config) throws Exception{
        // 组装系统级参数
        Map params = new HashMap<String, String>();
        params.put("RequestData", URLEncoder.encode(requestData, "UTF-8"));
        params.put("EBusinessID", config.getBusinessId());
        params.put("RequestType", "1007");
        String dataSign=encrypt(requestData, config.getApiKey(), "UTF-8");
        params.put("DataSign", URLEncoder.encode(dataSign, "UTF-8"));
        params.put("DataType", "2");
        // 以form表单形式提交post请求，post请求体中包含了应用级参数和系统级参数
        String result= HttpRequest.post(config.getReqURL()).form(params).execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if(jsonObject.getStr("Success").equals("false")){
            ExceptionUtil.throwException("电子面单获取失败,"+jsonObject.getStr("Reason"));
        }
        //根据公司业务处理返回的信息......
        return jsonObject;
    }

    /**
     * base64编码
     * str 内容
     * charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private static String base64(String str, String charset) throws UnsupportedEncodingException{
        String encoded = Base64.encode(str.getBytes(charset));
        return encoded;
    }

    /**
     * MD5加密
     * str 内容
     * charset 编码方式
     * @throws Exception
     */
    private static String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }
    /**
     * 电商Sign签名生成
     * content 内容
     * keyValue ApiKey
     * charset 编码方式
     * @return DataSign签名
     */
    private static String encrypt (String content, String keyValue, String charset) throws Exception {
        if (keyValue != null)
        {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }
}
