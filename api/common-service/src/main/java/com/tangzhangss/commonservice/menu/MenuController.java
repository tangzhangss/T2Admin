package com.tangzhangss.commonservice.menu;


import com.tangzhangss.commonservice.user.UserService;
import com.tangzhangss.commonutils.base.SysBaseController;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController extends SysBaseController<MenuEntity, MenuService> {

    @Autowired
    UserService userService;

    /*
     用户菜单
     */
    @GetMapping("/get_user_menu")
    public Result getUserMenu() {
        return Result.ok.data(myService.getUserMenuList());
    }

    /*
       企业客户菜单
     */
    @GetMapping("get_client_menu")
    public Result getClientMenu() {
        return Result.ok.data(myService.getClientMenuList());
    }
}
