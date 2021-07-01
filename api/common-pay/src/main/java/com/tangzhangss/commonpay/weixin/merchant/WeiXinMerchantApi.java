package com.tangzhangss.commonpay.weixin.merchant;

import com.tangzhangss.commonutils.base.SysBaseApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixin_merchant")
public class WeiXinMerchantApi extends SysBaseApi<WeiXinMerchantEntity,WeiXinMerchantService>{
}
