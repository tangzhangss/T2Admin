package com.tangzhangss.commonservice.test;


import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.config.FeginConfig;
import com.tangzhangss.commonutils.config.FeginRemoteCall;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/service_test")
public class ServiceTestApi extends SysBaseApi<ServiceTestEntity, ServiceTestService> {
    @Autowired
    ServiceTestDao testDao;
    @Autowired
    ServiceTestFeginInterface serviceTestFeginInterface;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/no_auth")
    public Result getAll() {
        JSONObject object = new JSONObject();
        object.set("clientId","tzcc_ren");
        SysContext.setUser(object);
        return Result.ok.data(myService.get(request,null));
    }
    @GetMapping("/feign/no_auth")
    public Result getFeignTest() {
        FeginRemoteCall call = ()->serviceTestFeginInterface.getData("a","b",null);
        Result result = FeginConfig.apply(call);
        return result;
    }

    @PostMapping("/no_auth")
    public Result post(@RequestBody ServiceTestEntity serviceTestEntity) throws Exception {
        JSONObject object = new JSONObject();
        object.set("clientId","tzcc_ren");
        SysContext.setUser(object);
        return Result.ok.data(myService.save(serviceTestEntity));
    }
}
