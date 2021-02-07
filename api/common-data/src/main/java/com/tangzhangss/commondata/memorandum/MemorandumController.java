package com.tangzhangss.commondata.memorandum;


import com.tangzhangss.commonutils.base.SysBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memorandum")
public class MemorandumController extends SysBaseController<MemorandumEntity, MemorandumService> {

}
