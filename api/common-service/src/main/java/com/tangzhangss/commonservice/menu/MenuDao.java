package com.tangzhangss.commonservice.menu;

import com.tangzhangss.commonutils.base.SysBaseDao;

import java.util.List;

public interface MenuDao extends SysBaseDao<MenuEntity, String> {
    List<MenuEntity> findAllBySystemicAndUsable(boolean systemic, boolean usable);

    List<MenuEntity> findAllByClientIdAndUsable(String clientId, boolean usable);

    List<MenuEntity> findAllByUsable(boolean b);
}
