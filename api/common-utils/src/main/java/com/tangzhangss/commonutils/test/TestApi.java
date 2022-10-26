package com.tangzhangss.commonutils.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.tangzhangss.commonutils.annotation.RepeatSubmit;
import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.datasource.service.DatasourceService;
import com.tangzhangss.commonutils.datasource.provider.DatasourceProvider;
import com.tangzhangss.commonutils.datasource.provider.ProviderFactory;
import com.tangzhangss.commonutils.datasource.request.DatasourceRequest;
import com.tangzhangss.commonutils.querydsl.QueryDslUtil;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.syscode.SysCodeService;
import com.tangzhangss.commonutils.utils.IpUtil;
import com.tangzhangss.commonutils.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestApi extends SysBaseApi<TestEntity,TestService> {
    @Autowired
    QueryDslUtil queryDslUtil;
    @Autowired
    HttpServletRequest request;
    @Autowired
    SysCodeService sysCodeService;
    @Autowired
    DatasourceService datasourceService;


    @PostMapping("/datasource/no_auth")
    public Result datasource(@RequestBody DatasourceRequest datasourceRequest) throws Exception {

        if(datasourceRequest.getDatasourceEntity()==null){
            datasourceRequest.setDatasourceEntity(datasourceService.get(datasourceRequest.getDatasourceId()));
        }

        DatasourceProvider provider = ProviderFactory.getProvider(datasourceRequest.getDatasourceEntity().getType());


        return Result.ok().data(cn.hutool.core.collection.ListUtil.of(
                provider.checkStatus(datasourceRequest.getDatasourceEntity())
        ));
    }


//    @RepeatSubmit
    @GetMapping("/___")
    public Result ___() throws InterruptedException {
        Thread.sleep(12000);
        return Result.ok();
    }


    @GetMapping("/no_auth")
    public Result getNoAuth() throws NacosException {
        SysContext.setUser(new JSONObject().set("clientId",10000));
        return Result.ok().data(myService.get(request,null));
    }
    @PutMapping("/no_auth")
    public Result saveNoAuth(@RequestBody TestEntity testEntity) throws NacosException {
        Annotation[] annotations1 = this.getClass().getAnnotations();
        Annotation[] annotations = testEntity.getClass().getAnnotations();
        return Result.ok().data(myService.save(testEntity));
    }
    @NacosInjected
    private NamingService namingService;

    @GetMapping("/nacos_get_service/no_auth")
    @ResponseBody
    public Result get(@RequestParam String serviceName) throws NacosException {
        return Result.ok().data(namingService.getAllInstances(serviceName));
    }

    @GetMapping("/get_code/no_auth")
    @ResponseBody
    public Result getCode(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("1"+sysCodeService.getDeclareSerialNo("test",new JSONObject()));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("2"+sysCodeService.getDeclareSerialNo("1",null));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("3"+sysCodeService.getDeclareSerialNo("1",new JSONObject()));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("4"+sysCodeService.getDeclareSerialNo("1",new JSONObject()));
                }
            }
        }).start();
        return Result.ok();
    }
    @GetMapping("/get_code/no_auth/{code}")
    @ResponseBody
    public Result getCode2(@PathVariable("code")String code) throws NacosException {
        return Result.ok().data(sysCodeService.getDeclareSerialNo(code,null));
    }
    @GetMapping("/querydsl")
    public Result querydsl(){
        QTestEntity qTestEntity =QTestEntity.testEntity;
        SysContext.setUser(new JSONObject().set("clientId",10000));

        queryDslUtil.setJPAQuery(new ListUtil().add(qTestEntity).get(),qTestEntity);

        Object res = queryDslUtil.getQueryFetchResults(request,null,null);

        return Result.ok().data(res);
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

        return Result.ok().data(myService.queryByGroup(whereMao,groupKey,groupMap,request));
    }

    @PutMapping("/update/no_auth")
    @Transactional
    public Result update(@RequestBody List<TestEntity> testEntityList){
        testEntityList.forEach(testEntity -> myService.update(testEntity));
        return Result.ok();
    }
    @PutMapping("/update")
    @Transactional
    public Result updateCustom(@RequestBody List<TestEntity> testEntityList){
        testEntityList.forEach(testEntity -> myService.updateCustom(testEntity));
        return Result.ok();
    }

    @PutMapping("/insert/no_auth")
    @Transactional
    public Result insert(@RequestBody List<TestEntity> testEntityList){
        List<TestEntity> withMap = myService.getWithMap(null);
        List<TestEntity> saveAll = new ArrayList<>();
        withMap.forEach(one->{
            TestEntity e = BeanUtil.copyProperties(one,TestEntity.class);
            e.setId(one.getId()*2);

            saveAll.add(e);
        });
        myService.insert(saveAll);
//        testEntityList.forEach(testEntity -> myService.insert(testEntity));
        return Result.ok();
    }
    @PutMapping("/delete/no_auth")
    @Transactional
    public Result delete(@RequestBody List<TestEntity> testEntityList){
        myService.delete(testEntityList);
//        testEntityList.forEach(testEntity -> myService.insert(testEntity));
        return Result.ok();
    }
}
