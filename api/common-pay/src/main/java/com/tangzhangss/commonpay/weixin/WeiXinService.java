package com.tangzhangss.commonpay.weixin;

import cn.hutool.http.HttpRequest;
import com.alibaba.nacos.client.utils.IPUtil;
import com.tangzhangss.commonpay.weixin.merchant.WeiXinMerchantEntity;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.IpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Component
public class WeiXinService {
    @Autowired
    HttpServletRequest request;

    @Value("${server.apiUrl}")
    public String serverAddress;

    public static enum TraderType{
        JSAPI("JSAPI"),
        NATIVE("NATIVE");

        public String type;

        TraderType(String type){
            this.type=type;
        }
    }

    /**
     *微信统一下单
     * @param traderType 支付类型
     * @param merchant 商户信息
     * @param money 金额 单位是元
     * @param outTradeNo 商户订单号
     * @param otherInfo hashmap 其他信息
     *          openid:"", //jsapi必须
     *          product_id:"" //native必须 	String(32)
     *          attach:"" //附加信息 String(127)
     * @return
     */
    public Map<String, String> unifiedOrder(TraderType traderType, WeiXinMerchantEntity merchant, String outTradeNo, String money, HashMap<String,String> otherInfo) throws Exception {

        //创建hashmap(用户获得
        // 签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();

        //设置body变量 (支付成功显示在微信支付 商品详情中)
        String body =  merchant.getBody();
        //设置随机字符串
        String nonceStr = RandomStringUtils.randomAlphanumeric(32);

        //设置请求参数(ID)
        paraMap.put("appid",merchant.getAppid());
        //设置请求参数(商户号)
        paraMap.put("mch_id", merchant.getMchid());
        //设置请求参数(随机字符串)
        paraMap.put("nonce_str", nonceStr);
        //设置请求参数(商品描述)
        paraMap.put("body", body);
        //设置请求参数(商户订单号)
        paraMap.put("out_trade_no", outTradeNo);
        //设置请求参数(总金额)
        paraMap.put("total_fee", String.valueOf((int)(Double.valueOf(money)*100)));
        //设置请求参数(终端IP)
        paraMap.put("spbill_create_ip", IpUtil.getHostIp());
        //设置请求参数(通知地址)
        paraMap.put("notify_url",serverAddress+WeiXinURL.PAY_NOTIFY.url);
        //设置请求参数(交易类型)
        paraMap.put("trade_type",traderType.type);
        paraMap.put("attach",otherInfo.get("attach"));


        //设置请求参数(openid)(在接口文档中 该参数 是否必填项 但是一定要注意 如果交易类型设置成'JSAPI'则必须传入openid)
        if(traderType == TraderType.JSAPI){
            paraMap.put("openid", otherInfo.get("openid"));
        }

        if(traderType == TraderType.NATIVE){
            paraMap.put("product_id", otherInfo.get("product_id"));
        }


        //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        String stringA = WeixinAssistUtil.formatUrlMap(paraMap, false, false);
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
        String sign = DigestUtils.md5Hex(stringA+"&key="+merchant.getMchkey()).toUpperCase();

        paraMap.put("sign",sign);

        String res = HttpRequest.post("https://api.mch.weixin.qq.com/pay/unifiedorder").body(WeixinAssistUtil.map2xml(paraMap)).execute().body();

        return WeixinAssistUtil.doXMLParse(res);
    }

}
