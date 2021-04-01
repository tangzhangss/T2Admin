package com.tangzhangss.commonservice.menu;

import com.alibaba.fastjson.JSONObject;
import com.tangzhangss.commonservice.role.RoleEntity;
import com.tangzhangss.commonservice.role.RoleService;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.config.Attribute;
import com.tangzhangss.commonutils.utils.HashMapUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MenuService extends SysBaseService<MenuEntity, MenuDao> {

    @Autowired
    RoleService roleService;

    @Override
    protected Map<String, String> getCheckFields() {
        return HashMapUtil.createHashMap().put("name","name").get();
    }

    /*
            菜单都是真实删除 usable字段用于禁用
         */
    @Override
    protected boolean bSureDelete() {
        return true;
    }

    public List<MenuEntity> getSuperAdminMenuList() {
        List<MenuEntity> userMenu = myDao.findAllBySystemicAndUsable(true, true);
        userMenu.addAll(getWithMapString("systemic@EQ=false&clientIds@EQ="));
        return userMenu;
    }

    public List<MenuEntity> getUserMenuList() {
        List<MenuEntity> userMenu = null;
        JSONObject currentUser = SysContext.getUser();

        //如果是超级管理员返回所有启用菜单----超级管理员不区分员工账号-权限一样
        if (SysContext.getClientId().equals(Attribute.SUPER_ADMIN_CLIENT_ID)) {
            return getSuperAdminMenuList();
        }

        //如果是企业管理员
        if (currentUser.getString("clientId").equals(currentUser.getString("username"))) {
            //直接拿企业的菜单
            userMenu = getClientMenuList();
        } else if (StringUtils.isNotBlank(currentUser.getString("roleIds"))) {
            //企业员工
            //有角色-查询这些角色拥有的菜单
            List<RoleEntity> roles = roleService.getWithMapString("name@IN=" + currentUser.getString("roleIds"));
            Set<String> menuList = new HashSet<>();
            roles.forEach(role -> {
                menuList.addAll(Arrays.asList(StringUtils.split(role.getMenuIds(), ",")));
            });
            //查询出所有的菜单--菜单由超级管理员创建
            String ids = StringUtils.join(menuList, ",");
            if (StringUtils.isBlank(ids)) {
                return new ArrayList<>(0);
            }
            userMenu = getCustomWithMapString("id@IN=" + ids);
        } else {
            //企业员工且没有角色 =》 没有菜单
            userMenu = new ArrayList<>(0);
        }

        return userMenu;
    }

    public List<MenuEntity> getClientMenuList() {
        //如果是超级管理员返回所有启用菜单
        if (SysContext.getClientId().equals(Attribute.SUPER_ADMIN_CLIENT_ID)) {
            return getSuperAdminMenuList();
        }

        List<MenuEntity> clientMenu = new ArrayList<>();
        //获取客户菜单_所有系统菜单的创建者必然是超级管理员
        if (!SysContext.getClientId().equals(Attribute.SUPER_ADMIN_CLIENT_ID)) {
            clientMenu = getCustomWithMapString("clientIds@LIKE=" + SysContext.getClientId());
        }
        //客户可以获取到系统菜单+自己的菜单
        List<MenuEntity> systemMenu = myDao.findAllBySystemicAndUsable(true, true);
        clientMenu.addAll(systemMenu);

        return clientMenu;
    }

}
