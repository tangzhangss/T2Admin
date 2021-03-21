package com.tangzhangss.quanvjkshop.specification;


import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.base.SysDynamic;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 规格
 */
@Entity
@SysDynamic
@Table(name = "tbl_quanvjkshop_specification")
public class SpecificationEntity extends SysBaseEntity{
    private String name;//规格名
}
