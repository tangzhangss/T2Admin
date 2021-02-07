package com.tangzhangss.commondata.memorandum;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 备忘录
 */
@Entity
@Table(name = "tbl_common_data_memorandum")
@Data
public class MemorandumEntity extends SysBaseEntity {
    private String title;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;
}
