package com.tangzhangss.commonutils.test;

import com.tangzhangss.commonutils.base.SysBaseService;
import org.springframework.stereotype.Service;

@Service
public class TestService extends SysBaseService<TestEntity,TestDao>{
    public void test(){
        System.out.println(myDao.toString());
    }
}
