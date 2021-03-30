package com.tangzhangss.commonservice.domain;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * endpoint
 * bucket
 * 属性请不要更改
 * （更改之后已经存过的图片删除会找不到文件）
 */
@Entity
@Table(name = "tbl_common_service_domain")
@Data
public class DomainEntity extends SysBaseEntity {
    private String name;//域名服务名
    private String proAddress="";//生产环境全地址
    private String devAddress="";//开发环境地址
}
