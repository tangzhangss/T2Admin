package com.tangzhangss.commonservice.aspect.preauthorize;

import com.tangzhangss.commonutils.base.SysBaseApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pre_authorize")
public class PreAuthorizeApi extends SysBaseApi<PreAuthorizeEntity,PreAuthorizeService> {
}
