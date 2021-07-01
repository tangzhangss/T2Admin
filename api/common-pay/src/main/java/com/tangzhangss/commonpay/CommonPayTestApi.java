package com.tangzhangss.commonpay;

import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.aspect.syslog.SysLog;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/test")
@RestController
public class CommonPayTestApi {

    @PostMapping("/one/no_auth")
    @SysLog("测试")
    Result one(@RequestBody JSONObject param2,String param){
        return Result.ok();
    }
}
