package com.tangzhangss.commondata.test;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data_test")
public class DataTestApi {
    @GetMapping("get_data/no_auth")
    Result getData(String a,String b) throws InterruptedException {
        Thread.sleep(8000);
        return Result.ok.data(new JSONObject().set("a",a+"___").set("b",b+"+++"));
    }
}
