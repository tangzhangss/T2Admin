package com.tangzhangss.commonservice.user;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户表
 */
@Entity
@Table(name = "tbl_common_service_user")
@Data
public class UserEntity extends SysBaseEntity {

    private String name = "";//用户名
    private String avatars = "";//头像
    private String phone = "";//联系方式
    private String email = "";//邮箱

    private String username = "";//登录账号
    private String password = "";//登录密码MD5加密

    private String roleIds = "";//用户角色id集合用,分割

    @Transient
    private String token;

}
