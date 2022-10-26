package com.tangzhangss.commonutils.base;

import com.tangzhangss.commonutils.config.Attribute;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
//@SysDynamic
public class SysBaseEntity {
    @Id
    @Column(nullable = false)
    protected Long id;

    @Column(nullable = false)
    protected LocalDateTime createTime;

    @Column(nullable = false)
    protected Long creatorId=SysContext.getUserId();//创建用户Id

    @Column(nullable = false)
    protected String creatorName=SysContext.getUserName();//创建用户名字（不联动更新）


    @Column(nullable = false)
    protected String clientId=SysContext.getClientId();//客户ID(一个客户对应多个关联用户)


    protected LocalDateTime updateTime;

    @Column(nullable = false)
    protected boolean usable = true;//是否可用_默认删除就是置该值为false(假删)

    protected String remark="";//备注

    protected boolean systemic=false;//是否是系统初始化-做一些其他操作,比如:系统提供的不可删除和修改甚至查看
}
