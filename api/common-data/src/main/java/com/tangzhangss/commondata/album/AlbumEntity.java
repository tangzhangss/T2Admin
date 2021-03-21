package com.tangzhangss.commondata.album;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.base.SysDynamic;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
