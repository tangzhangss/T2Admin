package com.tangzhangss.commonutils.test;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_common_utils_test")
@Data
public class TestEntity extends SysBaseEntity {
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private Integer i;
}
