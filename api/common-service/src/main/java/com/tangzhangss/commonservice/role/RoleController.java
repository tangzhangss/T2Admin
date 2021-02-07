package com.tangzhangss.commonservice.role;

import com.tangzhangss.commonutils.base.SysBaseController;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/role")
public class RoleController extends SysBaseController<RoleEntity, RoleService> {
    @Autowired
    RoleDao roleDao;

    @GetMapping("/get_all")
    public Result getAll() {
        return Result.ok.data(myService.get(null, new HashMap<>()));
    }
}
