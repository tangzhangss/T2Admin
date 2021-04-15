package com.tangzhangss.commonservice.test;

import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "COMMON-DATA")
@Component
public interface ServiceTestFeginInterface {
    @GetMapping(value = "/data_test/get_data/no_auth")
    Result getData(@RequestParam("a")String a, @RequestParam("b")String b,@RequestHeader("X-Token") String token);

}
