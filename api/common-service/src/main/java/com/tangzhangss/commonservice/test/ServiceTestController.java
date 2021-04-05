package com.tangzhangss.commonservice.test;


import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.base.SysBaseController;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/service_test")
public class ServiceTestController extends SysBaseController<ServiceTestEntity, ServiceTestService> {
    @Autowired
    ServiceTestDao roleDao;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/no_auth")
    public Result getAll() {
        JSONObject object = new JSONObject();
        object.set("clientId","tzcc_ren");
        SysContext.setUser(object);
        return Result.ok.data(myService.get(request,null));
    }

    @PostMapping("/no_auth")
    public Result post(@RequestBody ServiceTestEntity serviceTestEntity) throws Exception {
        JSONObject object = new JSONObject();
        object.set("clientId","tzcc_ren");
        SysContext.setUser(object);
        return Result.ok.data(myService.save(serviceTestEntity));
    }
}
