package com.tangzhangss.commonpay.alipay.merchant;

import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.utils.HashMapUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlipayMerchantService extends SysBaseService<AlipayMerchantEntity,AlipayMerchantDao> {
    @Override
    protected Map<String, String> getCheckFields() {
        return HashMapUtil.createHashMap().put("appid","应用appid").get();
    }
}
