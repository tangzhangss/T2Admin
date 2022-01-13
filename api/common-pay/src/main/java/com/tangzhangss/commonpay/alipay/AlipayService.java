package com.tangzhangss.commonpay.alipay;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.tangzhangss.commonpay.alipay.merchant.AlipayMerchantEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlipayService {
    @Value("${custom.debug}")
    private boolean debug;
    @Value("${server.apiUrl}")
    String apiUrl;

    /**
     * 当面付二维码
     *
     * @param alipayMerchantEntity 商户信息
     * @param outTradeNo "订单号 唯一"【必选】
     * @param money  订单总金额，单位为元，精确到小数点后两位，取值范围为 [0.01,100000000]。金额不能为0。
     * @param otherInfo 其他信息
     *                  {
     *                      "subject（256）":"	订单标题。注意：不可使用特殊字符，如 /，=，& 等。"【必选】
     *                      "body（256）":"订单附加信息。
     * 如果请求时传递了该参数，将在异步通知、对账单中原样返回，同时会在商户和用户的pc账单详情中作为交易描述展示"【可选】
     *                      "storeId":"商户门店编号。指商户创建门店时输入的门店编号。"【可选】
     *                      "extendParams":"业务扩展参数	"【可选】
     *                      "timeoutExpress":"该笔订单允许的最晚付款时间，逾期将关闭交易，从生成二维码开始计时。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。"【可选】
"     *                  }
     * @return 支付二维码内容qrCode
     */
    @Transactional
    public AlipayTradePrecreateResponse unifiedOrder(AlipayMerchantEntity alipayMerchantEntity, String outTradeNo, String money, JSONObject otherInfo) throws AlipayApiException {
        //付款金额，必填
        String totalAmount = money;

        AlipayClient alipayClient = new DefaultAlipayClient(debug?AliPayConfig.GATEWAY_URL_DEV:AliPayConfig.GATEWAY_URL_PRD,
                alipayMerchantEntity.getAppid(),
                alipayMerchantEntity.getMerchantPrivateKey(),
                AliPayConfig.FORMAT,
                AliPayConfig.CHARSET,
                alipayMerchantEntity.getAlipayPublicKey(),
                AliPayConfig.SIGN_TYPE);

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(totalAmount);
        model.setSubject(otherInfo.getStr("subject"));
        model.setBody(otherInfo.getStr("body"));
        //如果没有店铺号可不设置
		model.setStoreId(otherInfo.getStr("storeId"));
        model.setQrCodeTimeoutExpress(otherInfo.getStr("timeoutExpress"));
        model.setBusinessParams(otherInfo.getStr("extendParams"));
        request.setBizModel(model);

        //支付宝异步通知地址
        request.setNotifyUrl(apiUrl+AliPayConfig.NOTIFY_URL);  //设置同步回调通知

        //下单
        return alipayClient.execute(request);
    }


    /**
     *  easy-sdk 跑不起来
     *
     *   Config config = new Config();
     *         config.protocol = AliPayConfig.PROTOCOL;
     *         config.gatewayHost = debug?AliPayConfig.GATEWAY_URL_DEV:AliPayConfig.GATEWAY_URL_PRD;
     *         config.signType = AliPayConfig.SIGN_TYPE;
     *
     *         config.appId =  alipayMerchantEntity.getAppid();
     *
     *         config.merchantPrivateKey =  alipayMerchantEntity.getMerchantPrivateKey();
     *         config.alipayPublicKey=alipayMerchantEntity.getAlipayPublicKey();
     *
     *         //可设置异步通知接收服务地址（可选）
     *         config.notifyUrl = AliPayConfig.NOTIFY_URL;
     *
     *         Factory.setOptions(config);
     *         try {
     *             // 2. 发起API调用（创建当面付收款二维码）
     *             AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().batchOptional(otherInfo)
     *                     .preCreate(otherInfo.getStr("subject"), outTradeNo, totalAmount);
     */
}
