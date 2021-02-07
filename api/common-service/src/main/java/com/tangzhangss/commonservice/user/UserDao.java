package com.tangzhangss.commonservice.user;

import com.tangzhangss.commonutils.base.SysBaseDao;

public interface UserDao extends SysBaseDao<UserEntity, String> {
    UserEntity findFirstByUsername(String username);
}
