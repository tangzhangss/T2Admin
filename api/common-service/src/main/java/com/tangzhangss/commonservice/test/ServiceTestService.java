package com.tangzhangss.commonservice.test;

import com.tangzhangss.commonutils.base.SysBaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceTestService extends SysBaseService<ServiceTestEntity, ServiceTestDao> {
    @Override
    protected boolean bSureDelete() {
        return true;
    }

    @Override
    protected boolean isQueryAll() {
        return true;
    }

}
