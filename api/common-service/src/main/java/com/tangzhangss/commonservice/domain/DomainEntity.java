package com.tangzhangss.commonservice.domain;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
   外部地址设置，用于外部菜单选择
 */
@Entity
@Table(name = "tbl_common_service_domain")
@Data
public class DomainEntity extends SysBaseEntity {
    private String name;//域名服务名
    private String proAddress="";//生产环境全地址
    private String devAddress="";//开发环境地址
}
