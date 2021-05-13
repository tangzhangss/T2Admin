package com.tangzhangss.commondata.album;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.base.SysDynamic;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 备忘录
 */
@Entity
@SysDynamic
@Table(name = "tbl_common_data_album")
@Data
public class AlbumEntity extends SysBaseEntity {
    private String title;
}
