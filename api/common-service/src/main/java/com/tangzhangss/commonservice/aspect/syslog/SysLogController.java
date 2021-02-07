package com.tangzhangss.commonservice.aspect.syslog;

import com.tangzhangss.commonutils.base.SysBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys_log")
public class SysLogController extends SysBaseController<SysLogEntity, SysLogService> {
}
