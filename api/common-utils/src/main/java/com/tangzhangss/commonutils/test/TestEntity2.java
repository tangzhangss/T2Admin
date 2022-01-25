package com.tangzhangss.commonutils.test;

import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_common_utils_test2")
@Data
public class TestEntity2 extends SysBaseEntity {
    private Integer i;
    private Integer ii;

}
