package com.tangzhangss.commonservice.test;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tangzhangss.commonservice.menu.MenuDao;
import com.tangzhangss.commonservice.menu.MenuEntity;
import com.tangzhangss.commonservice.menu.MenuService;
import com.tangzhangss.commonutils.annotation.RepeatSubmit;
import com.tangzhangss.commonutils.annotation.SysLog;
import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.config.FeginConfig;
import com.tangzhangss.commonutils.config.FeginRemoteCall;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/service_test")
public class ServiceTestApi extends SysBaseApi<ServiceTestEntity, ServiceTestService> {
    @Autowired
    ServiceTestDao testDao;
    @Autowired
    ServiceTestFeginInterface serviceTestFeginInterface;
    @Autowired
    HttpServletRequest request;

    @Autowired
    MenuDao menuDao;


    @GetMapping("/FetchModeTest")
    public Object FetchModeTest(){
        SysContext.setUser(new JSONObject().set("clientId","tzcc_ren"));

        return myService.fetchModeTest();
    }


    @RepeatSubmit
    @GetMapping("/no_auth")
    @SysLog(value = "获取测试数据")
    public Result getAll() throws InterruptedException {
        Thread.sleep(10000);
        return Result.ok().data(myService.get(request,null));
    }
    @GetMapping("/feign/no_auth")
    public Result getFeignTest() {
        FeginRemoteCall call = ()->serviceTestFeginInterface.getData("a","b",null);
        Result result = FeginConfig.apply(call,"---");
        return result;
    }

    @PostMapping("/no_auth")
    public Result post(@RequestBody ServiceTestEntity serviceTestEntity) throws Exception {
        JSONObject object = new JSONObject();
        object.set("clientId","tzcc_ren");
        SysContext.setUser(object);
        return Result.ok().data(myService.save(serviceTestEntity));
    }

    @PostMapping("/test_i18n")
    public void testI18n(@RequestBody JSONObject obj){
        RequestUtil.checkNullParam(obj,"id","name");
    }
}
