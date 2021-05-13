package com.tangzhangss.commonservice.menu;


import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.tangzhangss.commonservice.user.UserService;
import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.querydsl.QueryDslUtil;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.utils.HashMapUtil;
import com.tangzhangss.commonutils.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuApi extends SysBaseApi<MenuEntity, MenuService> {

    @Autowired
    HttpServletRequest request;
    @Autowired
    QueryDslUtil queryDslUtil;
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

    @GetMapping("/querydsl_test")
    public Result querydslTest(){
        QueryResults queryResults = queryDslUtil.getJPAQuery(
                (List<Expression<?>>) ListUtil.createLinkedList().add(QMenuEntity.menuEntity.clientId).get()
                ,QMenuEntity.menuEntity)
                .getQueryResults(request, HashMapUtil.createHashMap().put("url@LIKE", "").get(),null);
        return Result.ok.data(queryResults);
    }
}
