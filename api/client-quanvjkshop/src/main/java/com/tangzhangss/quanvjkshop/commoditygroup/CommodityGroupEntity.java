package com.tangzhangss.quanvjkshop.commoditygroup;


import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.base.SysDynamic;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商品组
 */
@Entity
@SysDynamic
@Table(name = "tbl_quanvjkshop_commodity_group")
public class CommodityGroupEntity extends SysBaseEntity{
    private String name;//组名
    private String code;//组编码，唯一
}
