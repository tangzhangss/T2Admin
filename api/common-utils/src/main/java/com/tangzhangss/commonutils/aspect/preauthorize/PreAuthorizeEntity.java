package com.tangzhangss.commonutils.aspect.preauthorize;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 权限记录表
 * ----------------------------------
 * 后期可做如下优化
 * 权限标识逗号分割，如想要方便用户操作，和更加精确化
 * 1.菜单添加权限前缀,如: menu,system:user
 * 2.权限维护界面选择菜单,  然后填写tag标识  list,list:add
 * 3.角色对应权限标识: 菜单权限前缀:tag  menu:list,system:user:list:add
 * ---------------------------------------------------------------
 */
@Data
@Entity
@Table(name = "tbl_common_service_preauthorize")
public class PreAuthorizeEntity extends SysBaseEntity {
    private String roleName;//角色Id
    private String tag;//权限标识,逗号分割 *,*:*,*:*:*,a:b,a1:b1:c1

    @Column(name = "[explain]")
    private String explain;//权限说明
}
