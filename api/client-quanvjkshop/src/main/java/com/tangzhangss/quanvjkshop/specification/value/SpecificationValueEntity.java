package com.tangzhangss.quanvjkshop.specification.value;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.base.SysDynamic;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@SysDynamic
@Table(name = "tbl_quanvjkshop_specification_value")
public class SpecificationValueEntity extends SysBaseEntity{
    private long specificationId;//规格的id
    private String value;//值
}
