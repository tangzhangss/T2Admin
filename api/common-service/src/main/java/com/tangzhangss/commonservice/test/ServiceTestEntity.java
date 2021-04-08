package com.tangzhangss.commonservice.test;

import com.tangzhangss.commonservice.user.UserEntity;
import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.base.SysContext;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_common_service_test")
@Data
public class ServiceTestEntity extends SysBaseEntity {
    @Id
    protected Long id;

    protected LocalDateTime createTime = LocalDateTime.now();

    protected Long creatorId= SysContext.getUserId();//创建用户Id
    protected String clientId=SysContext.getClientId();//客户ID(一个客户对应多个关联用户)

    protected LocalDateTime updateTime;

    protected boolean usable = true;//是否可用_默认删除就是置该值为false(假删)

    private String name = "";//角色名

    private String columnOne;
    private Integer columnTwo;
    private LocalDate columnThere;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String editor;
}
