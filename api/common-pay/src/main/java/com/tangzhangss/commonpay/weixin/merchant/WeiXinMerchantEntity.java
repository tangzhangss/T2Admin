package com.tangzhangss.commonpay.weixin.merchant;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@Table(name = "tbl_common_pay_weixin_merchant")
public class WeiXinMerchantEntity extends SysBaseEntity {
    private String appid; //公众号ID
    private String mchid; //支付商户id
    private String mchkey;//商户密钥
    private String tradeType;//交易类型
    private String body;//商品类型String(128)
    private String sslcertPath;
    private String sslkeyPath;
    private String rootPath;

    @Transient
    public static WeiXinMerchantEntity defaultConfig = new WeiXinMerchantEntity();

    static {
        defaultConfig.mchid = "1609794550";
        defaultConfig.appid = "wx386391f22d17b232";
        defaultConfig.mchkey = "Huanfangweixinmiyao2021202120211";
        defaultConfig.tradeType = "NATIVE";
        defaultConfig.body = "TangZhangss";
    }


}
