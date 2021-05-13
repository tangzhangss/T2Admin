package com.tangzhangss.commonutils.syscode;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tbl_common_utils_sys_code")
public class SysCodeEntity extends SysBaseEntity{
    private String noName; // 编码名称
    private String noCode; // 编码代码
    private String noGroup;  // 分组
    private String formula;   // 规则
    private Integer currentNo=0;   // 当前序号
}
