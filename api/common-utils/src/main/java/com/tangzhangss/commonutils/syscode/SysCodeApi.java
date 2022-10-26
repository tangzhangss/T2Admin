package com.tangzhangss.commonutils.syscode;

import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sys_code")
public class SysCodeApi extends SysBaseApi<SysCodeEntity,SysCodeService> {


}
