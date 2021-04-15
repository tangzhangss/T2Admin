package com.tangzhangss.commonservice.test;

import com.tangzhangss.commonutils.base.SysBaseDao;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

public interface ServiceTestDao extends SysBaseDao<ServiceTestEntity, String> {

}
