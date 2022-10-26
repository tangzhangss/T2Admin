package com.tangzhangss.commonutils.datasource.entity;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_common_service_data_source")
@Data
public class DatasourceEntity extends SysBaseEntity implements Serializable {

    private String name;//名称
    private String description;//描述
    private String type;//类型 mysql pg oracle doris...
    //每次修改都回到待测试，只有测试成功才能使用(可以在保存的时候自动链接测试回写连接状态)
    private Integer status=0;//状态 0 待测试 1 成功 2 失败
    private String message;//状态 连接失败的message或其他
    @Column(columnDefinition = "TEXT")
    private String configuration;//配置详情

    private static final long serialVersionUID = 1L;
}
