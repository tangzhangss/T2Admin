package com.tangzhangss.commonservice;

import cn.hutool.json.JSONObject;
import com.tangzhangss.commonservice.menu.MenuService;
import com.tangzhangss.commonutils.base.SysContext;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuTest {
    @Autowired
    MenuService menuService;


    @Test
    public void FetchModeTest(){
        SysContext.setUser(new JSONObject().set("clientId",10000));
        menuService.get(null,null);
    }
}
