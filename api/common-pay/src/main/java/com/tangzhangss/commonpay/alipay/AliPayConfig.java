package com.tangzhangss.commonpay.alipay;

/**
 * 支付宝支付URL配置
 */
public class AliPayConfig {
    public static String NOTIFY_URL="/alipay/pay_notify";

    //页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String RETURN_URL="/alipay/page_notify";

    //签名方式
    public static String SIGN_TYPE="RSA2";

    //format
    public static String FORMAT="JSON";

    //字符编码格式
    public static String CHARSET="UTF-8";

    //支付宝网关(沙盒环境)
    public static String GATEWAY_URL_DEV="https://openapi.alipaydev.com/gateway.do";

    //支付宝网关(生产环境)
    public static String GATEWAY_URL_PRD="https://openapi.alipay.com/gateway.do ";

}
