package com.tangzhangss.commonservice.test;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.tangzhangss.commonservice.menu.MenuEntity;
import com.tangzhangss.commonservice.menu.MenuService;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.base.SysContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceTestService extends SysBaseService<ServiceTestEntity, ServiceTestDao> {

    @Autowired
    MenuService menuService;
    @Override
    protected boolean bSureDelete() {
        return true;
    }

    @Override
    protected boolean isQueryAll() {
        return true;
    }


    /*
     测试(fetch = FetchType.LAZY)
     */
    @Transactional
    public Object fetchModeTest(){
        SysContext.setUser(new JSONObject().set("clientId","tzcc_ren"));
        Page<MenuEntity> menuEntities = menuService.get(null, null);
        List<MenuEntity> content = menuEntities.getContent();
        JSONArray array = new JSONArray();
        content.forEach(c->{
            array.add(new JSONObject().set("id",c.getId())
                    .set("name",c.getName()).set("domain",c.getDomain()));
        });
        return array;
    }

}
