package com.tangzhangss.commonutils.test;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.tangzhangss.commonutils.base.SysBaseController;
import com.tangzhangss.commonutils.querydsl.QueryDslUtil;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController extends SysBaseController<TestEntity,TestService> {
    @Autowired
    QueryDslUtil queryDslUtil;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/___")
    public Result ___(){
        System.out.println(1/0);
        myService.test();
        return Result.ok;
    }

    @NacosInjected
    private NamingService namingService;

    @GetMapping("/nacos_get_service/no_auth")
    @ResponseBody
    public Result get(@RequestParam String serviceName) throws NacosException {
        return Result.ok.data(namingService.getAllInstances(serviceName));
    }

    @GetMapping("/querydsl/no_auth")
    public Result querydsl(){
        return Result.ok;
    }
    @GetMapping("/no_auth")
    public Result no_auth(){
        return Result.ok;
    }

    @GetMapping("/group_by")
    public Result groupBy(HttpServletRequest request) {
        //分组部分
        String []groupKey=new String[]{"clientId"};

        //聚合部分
        Map<String,String> groupMap=new HashMap<>();
        groupMap.put("clientId","max");
        groupMap.put("remark","concat");

        //where条件
        Map<String,String> whereMao=new HashMap<>();

        return Result.ok.data(myService.queryByGroup(whereMao,groupKey,groupMap,request));
    }
}
