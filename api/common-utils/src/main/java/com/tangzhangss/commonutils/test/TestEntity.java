package com.tangzhangss.commonutils.test;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.base.SysDynamic;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_common_utils_test")
@Data
@DynamicUpdate
public class TestEntity extends SysBaseEntity {
    @Column(name = "local_date")
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
