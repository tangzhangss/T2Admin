package com.tangzhangss.commonutils.test;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_common_utils_test")
@Data
public class TestEntity extends SysBaseEntity {
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private Integer i;
    private Long id;
    @Transient
    private String sss;

//    ----
//    @Version
//    protected long version=1;
}
