package com.tangzhangss.commonutils.aspect.syslog;


import com.tangzhangss.commonutils.base.SysBaseApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys_log")
public class SysLogApi extends SysBaseApi<SysLogEntity, SysLogService> {
}
