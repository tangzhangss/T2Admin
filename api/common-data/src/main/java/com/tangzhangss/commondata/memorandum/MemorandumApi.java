package com.tangzhangss.commondata.memorandum;


import com.tangzhangss.commonutils.base.SysBaseApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memorandum")
public class MemorandumApi extends SysBaseApi<MemorandumEntity, MemorandumService> {


}
