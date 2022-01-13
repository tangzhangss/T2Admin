package com.tangzhangss.commonpay.alipay;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.tangzhangss.commonpay.alipay.merchant.AlipayMerchantEntity;
import com.tangzhangss.commonpay.alipay.merchant.AlipayMerchantService;
import com.tangzhangss.commonpay.orderinfo.OrderInfoEntity;
import com.tangzhangss.commonpay.orderinfo.OrderInfoService;
import com.tangzhangss.commonpay.orderinfo.PayType;
import com.tangzhangss.commonpay.weixin.WeiXinService;
import com.tangzhangss.commonpay.weixin.WeixinAssistUtil;
import com.tangzhangss.commonutils.aspect.syslog.SysLog;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.HashMapUtil;
import com.tangzhangss.commonutils.utils.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AliayApi {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AlipayService alipayService;
    @Autowired
    AlipayMerchantService alipayMerchantService;
    @Autowired
    OrderInfoService orderInfoService;
    /**
     *
     * @param payData
     * {
     *    callbackApi:"支付成功回调地址" post请求 【可选】
     *    remark:"备注" 【可选】
     *   "appid":"应用id"【可选】
     *   "orderNo":"订单号-外部系统保证唯一"【必选】
     *   "money":"订单总金额，单位为元，精确到小数点后两位，取值范围为 [0.01,100000000]。金额不能为0"【必选】
         "subject（256）":"	订单标题。注意：不可使用特殊字符，如 /，=，& 等。"【必选】
         "storeId":"商户门店编号。指商户创建门店时输入的门店编号。"【可选】
         "extendParams":"业务扩展参数	"【可选】
         "timeoutExpress":"该笔订单允许的最晚付款时间，逾期将关闭交易，从生成二维码开始计时。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。"【可选】
      }
     * @return
     * @throws Exception
     */
    @SysLog
    @PostMapping("/unified_order/no_auth")
    @Transactional(rollbackFor = Exception.class)
    public Result unifiedOrder(@RequestBody JSONObject payData) throws AlipayApiException {
        RequestUtil.checkNullParam(payData,"orderNo","money","orderNo","subject","body");

        String appid = payData.getStr("appid");
        String money = payData.getStr("money");
        String orderNo = payData.getStr("orderNo");

        AlipayMerchantEntity alipayMerchantEntity=appid!=null? alipayMerchantService.getOneWithMapString("appid@EQ="+appid):AlipayMerchantEntity.defaultConfig;
        if(alipayMerchantEntity==null) ExceptionUtil.throwException("merchant appid #{0} is not found！",appid);

        String outTradeNo = SysContext.getClientId()+orderNo;

        //判断订单是否已支付
        List<OrderInfoEntity> orderInfoEntityList = orderInfoService.getWithMapString("orderNo@EQ=" + orderNo);

        orderInfoEntityList.forEach(order->{
            if(order.getStatus()==1) ExceptionUtil.throwException("orderNo #{0} paid，please check!",orderNo);
        });
        //每次未支付或支付失败的订单都需要保留---
        if(orderInfoEntityList.size()!=0)outTradeNo += "_"+orderInfoEntityList.size();

        //创建订单
        OrderInfoEntity orderInfo = new OrderInfoEntity();
        orderInfo.setCallbackApi(payData.getStr("callbackApi"));
        orderInfo.setMoney(money);
        orderInfo.setOutTradeNo(outTradeNo);
        orderInfo.setOrderNo(orderNo);
        orderInfo.setPayType(PayType.Alipay.type);
        orderInfo.setRemark(payData.getStr("remark"));
        orderInfoService.save(orderInfo);

        payData.set("body",orderInfo.getId());//将id放在body里面回调时获取

        AlipayTradePrecreateResponse response = alipayService.unifiedOrder(alipayMerchantEntity,outTradeNo,money,payData);

        if (response.isSuccess()) {
            orderInfo.setPayCode(response.getQrCode());//支付二维码
            //重新保存一次
            orderInfoService.save(orderInfo);
        } else {
            String errorMsg = "Call failed for：" + response.getMsg() + "，" + response.getSubMsg();
            ExceptionUtil.throwException(errorMsg);
        }

        return Result.ok().data(new HashMapUtil().put("qrCode",response.getQrCode())
                .put("outTradeNo",response.getOutTradeNo()).get());
    }

    /**
     * 支付回调
     */
    @RequestMapping("/pay_notify")
    @SysLog
    @Transactional
    public void payNotify(){
        Map<String, String[]> parameterMap = request.getParameterMap();
        String body = parameterMap.get("body")[0];
        String outTradeNo = parameterMap.get("out_trade_no")[0];
        String tradeNo = parameterMap.get("trade_no")[0];
        String notifyTime = parameterMap.get("notify_time")[0];
        String receiptAmount = parameterMap.get("receipt_amount")[0];
        String tradeStatus = parameterMap.get("trade_status")[0];
        //不需要token查询
        OrderInfoEntity orderInfoEntity = orderInfoService.get(Long.parseLong(body));
        if(orderInfoEntity==null)orderInfoEntity=new OrderInfoEntity();//如果找不到就重新建一个
        orderInfoEntity.setOutTradeNo(outTradeNo);
        orderInfoEntity.setTransactionId(tradeNo);
        orderInfoEntity.setPayTime(Convert.toLocalDateTime(notifyTime));// 支付时间
        orderInfoEntity.setPayMoney(receiptAmount);
        if("TRADE_SUCCESS".equals(tradeStatus)){
            orderInfoEntity.setStatus(1);
            //支付成功调用回调
            if(StringUtils.isNotBlank(orderInfoEntity.getCallbackApi())){
                try{
                    String res = HttpRequest.post(orderInfoEntity.getCallbackApi()).body(JSONUtil.toJsonStr(orderInfoEntity)).execute().body();
                    JSONObject resObj = JSONUtil.parseObj(res);
                    if(resObj.getStr("code")==null||resObj.getStr("code").equals("200")){
                        //回调成功
                        orderInfoEntity.setIsCallBackSuccess(true);
                    }else{
                        orderInfoEntity.setIsCallBackSuccess(false);
                    }
                    orderInfoEntity.setCallBackResult(res);
                }catch(Exception e){
                    orderInfoEntity.setIsCallBackSuccess(false);
                    orderInfoEntity.setCallBackResult(e.getMessage());
                }
            }
        }else{
            orderInfoEntity.setStatus(-1);
            orderInfoEntity.setErrorMessage(tradeStatus);
        }
        orderInfoService.save(orderInfoEntity);
    }

    @PostMapping("/test_callback/no_auth")
    private Result testCallBack(@RequestBody JSONObject obj){

        return Result.ok();
    }
}
