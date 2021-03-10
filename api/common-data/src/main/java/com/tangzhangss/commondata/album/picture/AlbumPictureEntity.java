package com.tangzhangss.commondata.album.picture;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 备忘录
 */
@Entity
@Table(name = "tbl_common_data_album_pic")
@Data
public class AlbumPictureEntity extends SysBaseEntity {
    private String url="";
    private Long albumId;//相册头表Id
}
