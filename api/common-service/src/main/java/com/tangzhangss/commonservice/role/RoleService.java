package com.tangzhangss.commonservice.role;

import com.tangzhangss.commonutils.base.SysBaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends SysBaseService<RoleEntity, RoleDao> {
    @Override
    public Map<String, String> getCheckFields() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "角色名");
        return map;
    }
}
