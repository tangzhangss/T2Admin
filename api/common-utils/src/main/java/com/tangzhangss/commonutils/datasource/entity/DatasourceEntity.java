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
    private String status;//状态 success fail
    @Column(columnDefinition = "TEXT")
    private String configuration;//配置详情

    private static final long serialVersionUID = 1L;
}
