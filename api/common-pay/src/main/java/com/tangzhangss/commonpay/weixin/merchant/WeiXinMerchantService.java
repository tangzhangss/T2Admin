package com.tangzhangss.commonpay.weixin.merchant;

import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.utils.HashMapUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeiXinMerchantService extends SysBaseService<WeiXinMerchantEntity,WeiXinMerchantDao> {
    @Override
    protected Map<String, String> getCheckFields() {
        return HashMapUtil.createHashMap().put("mchid","商户ID").get();
    }
}
