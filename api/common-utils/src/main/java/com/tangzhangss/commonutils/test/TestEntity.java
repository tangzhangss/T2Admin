package com.tangzhangss.commonutils.test;

import com.tangzhangss.commonutils.annotation.Excel;
import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;
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

    private Long test2Id;

    @Transient
    @OneToOne
    @JoinColumn(name = "test2Id",referencedColumnName = "id",insertable = false,updatable = false)
    private TestEntity2 testEntity2;


    /**
     * 导出测试列
     */
    @Excel(name = "名字")
    private String exportName;
    @Excel(name = "数值", readConverterExp = "1=one,2=two")
    private String exportValue;


//    ----
//    @Version
//    protected long version=1;
}
