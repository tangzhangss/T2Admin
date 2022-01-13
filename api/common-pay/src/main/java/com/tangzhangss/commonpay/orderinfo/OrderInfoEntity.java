package com.tangzhangss.commonpay.orderinfo;

import cn.hutool.core.date.DateTime;
import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_common_pay_order_info")
public class OrderInfoEntity extends SysBaseEntity {
    private String orderNo;//订单号-处理之后的
    private String outTradeNo;//交易订单号-订单号加以处理之后的订单号
    private LocalDateTime orderTime = LocalDateTime.now();//订单时间
    private String money;//订单金额-单位元
    private String payMoney;//订单付款金额-单位元
    private String bankType;//付款银行
    private String refundMoney; // 已退款金额
    private Integer status=0;//0 未支付 1=已支付 -1支付失败
    private String payType="WeiXin";//支付类型 WeiXin Alipay 支付宝
    private String traderType="NATIVE";//微信 交易类型 JSAPI NATIVE
    private String callbackApi;//回调API  post请求 需要返回 code!=200 表示回调失败 body请求体里面是本对象json串
    private Boolean isCallBackSuccess=false;//是否成功调用回调api
    @Column(columnDefinition = "TEXT")
    private String callBackResult;// 回调api返回的值
    private LocalDateTime payTime;  // 支付时间
    private String transactionId;//(微信，支付宝)支付订单号
    private String errorMessage;//(微信，支付宝)支付失败信息
    private String payCode;//支付二维码、图片
}
