package com.tangzhangss.commonpay.weixin;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tangzhangss.commonpay.orderinfo.OrderInfoEntity;
import com.tangzhangss.commonpay.orderinfo.OrderInfoService;
import com.tangzhangss.commonpay.orderinfo.PayType;
import com.tangzhangss.commonpay.weixin.merchant.WeiXinMerchantEntity;
import com.tangzhangss.commonpay.weixin.merchant.WeiXinMerchantService;
import com.tangzhangss.commonutils.annotation.SysLog;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.resultdata.ResultCode;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/weixin")
public class WeiXinApi {
    @Autowired
    WeiXinService weiXinService;
    @Autowired
    WeiXinMerchantService weiXinMerchantService;
    @Autowired
    OrderInfoService orderInfoService;

    /**
     * orderNo:"订单号"
     * 金额 money double
     * "openid","o4s6w1JJf0H9zGzrLzUr6W4xzziM"
     * 备注 "remark","12233" //选择
     * callbackApi:"支付成功回调地址"
     */
    @SysLog
    @PostMapping("/unifiedOrder_jsapi")
    @Transactional(rollbackFor = Exception.class)
    public Result unifiedOrderJsapi(@RequestBody JSONObject payData) throws Exception {
        RequestUtil.checkNullParam(payData,"openid","money","orderNo");

        HashMap<String,String> otherInfo = new HashMap<>();
        otherInfo.put("openid",payData.getStr("openid"));

        return unifiedOrder(payData,WeiXinService.TraderType.NATIVE,otherInfo);
    }


    /**
     * orderNo:"订单号" 外部系统保证唯一
     * 金额 money double
     * "productId","产品id"
     * 备注 "remark","12233" //选择
     * callbackApi:"支付成功回调地址" post请求
     * mchid "支付商户ID" //如果有多个商户需要选择 如果不传则随机选择一个
     */
    @SysLog
    @PostMapping("/unifiedOrder_native")
    @Transactional(rollbackFor = Exception.class)
    public Result unifiedOrderNative(@RequestBody JSONObject payData) throws Exception {

        RequestUtil.checkNullParam(payData,"productId","money","orderNo");
        HashMap<String,String> otherInfo = new HashMap<>();
        otherInfo.put("product_id",payData.getStr("productId"));

       return unifiedOrder(payData,WeiXinService.TraderType.NATIVE,otherInfo);
    }

    /**
     * 一个订单号未支付的话
     * 可以下多个订单（如，修改金额之后重新下单）
     * 外部系统自己控制
     */
    private Result unifiedOrder(JSONObject payData,WeiXinService.TraderType traderType,HashMap<String,String> otherInfo) throws Exception {

        String mchid=payData.getStr("mchid");
        String paramUrl="";
        if(StringUtils.isNotBlank(mchid))paramUrl="mchid@EQ="+mchid;
        WeiXinMerchantEntity weiXinMerchant = weiXinMerchantService.getOneWithMapString(paramUrl);
        if(weiXinMerchant==null)weiXinMerchant= WeiXinMerchantEntity.defaultConfig;

        String orderNo= payData.getStr("orderNo");

        //处理订单号 每个企业加上标识
        String outTradeNo= SysContext.getClientId()+payData.getStr("orderNo");

        String money= payData.getStr("money");

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
        orderInfo.setPayType(PayType.WeiXin.type);
        orderInfo.setTraderType(traderType.type);
        orderInfo.setRemark(payData.getStr("remark"));
        orderInfoService.save(orderInfo);
        //将id加入附加信息 回调的时候拉取订单信息
        otherInfo.put("attach",orderInfo.getId().toString());

        Map<String, String> map = weiXinService.unifiedOrder(traderType,weiXinMerchant,outTradeNo,money, otherInfo);

        if(!map.get("return_code").equals("SUCCESS")|| !map.get("result_code").equals("SUCCESS")){
            ExceptionUtil.throwException(map.get("err_code_des"));
        }
        orderInfo.setPayCode(map.get("code_url"));//支付二维码

        //重新保存一次
        orderInfoService.save(orderInfo);

        return Result.ok().data(map);
    }
    @RequestMapping("/pay_notify")
    @SysLog
    @Transactional(rollbackFor = Exception.class)
    public void payNotify(HttpServletRequest request, HttpServletResponse response){
        try(InputStream inputStream = request.getInputStream()){
            // BufferedReader是包装设计模式，性能更高
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            Map<String, String> callBackMap = WeixinAssistUtil.doXMLParse(stringBuffer.toString());
            // 更新订单状态
            if ("SUCCESS".equals(callBackMap.get("return_code"))) {
                String outTradeNo = callBackMap.get("out_trade_no"); // 订单
                String attach = callBackMap.get("attach"); // 附加信息这里是ID
                //不需要token查询
                OrderInfoEntity orderInfoEntity = orderInfoService.get(Long.parseLong(attach));
                if(orderInfoEntity==null){
                    orderInfoEntity=new OrderInfoEntity();//如果找不到就重新建一个
                }

                //支付成功
                Double totalFee = Double.valueOf(callBackMap.get("total_fee"))/100; // 支付金额   单位为分 这里要转成元
                String payNo = callBackMap.get("transaction_id");// 微信支付订单号
                orderInfoEntity.setPayTime(Convert.toLocalDateTime(callBackMap.get("time_end")));// 支付时间
                orderInfoEntity.setPayMoney(String.valueOf(totalFee));
                orderInfoEntity.setTransactionId(payNo);
                orderInfoEntity.setOutTradeNo(outTradeNo);//重新设置一遍 如果没有拉取到订单记录还知道交易订单号
                orderInfoEntity.setBankType(callBackMap.get("bank_type"));

                if("SUCCESS".equals(callBackMap.get("result_code"))){
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
                    orderInfoEntity.setErrorMessage(callBackMap.get("err_code_des"));
                }
                orderInfoService.save(orderInfoEntity);

                response.setContentType("text/xml");
                String data = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                response.getWriter().write(data);
            } else {
                // 未成功，就都处理为失败订单
                response.setContentType("text/html");
                response.getWriter().write("fail");
            }
        } catch (Exception e) {
            System.out.println("支付回调出错:"+e.getMessage());
            e.printStackTrace();
        }
    }


    @PostMapping("/test_callback/no_auth")
    private Result testCallBack(@RequestBody JSONObject obj){

        return Result.ok();
    }
}
