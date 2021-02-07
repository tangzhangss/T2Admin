package com.tangzhangss.commonservice.role;

import com.tangzhangss.commonservice.user.UserEntity;
import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_common_service_role")
@Data
public class RoleEntity extends SysBaseEntity {
    private String name = "";//角色名

    @Column(columnDefinition = "text")
    private String menuIds = "";//该角色拥有的菜单列表

    @OneToOne
    @JoinColumn(name = "creatorId", referencedColumnName = "id", insertable = false, updatable = false)
    UserEntity creator;
}
