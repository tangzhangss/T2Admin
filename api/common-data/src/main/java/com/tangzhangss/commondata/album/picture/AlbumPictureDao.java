package com.tangzhangss.commondata.album.picture;

import com.tangzhangss.commonutils.base.SysBaseDao;

public interface AlbumPictureDao extends SysBaseDao<AlbumPictureEntity, String> {
    long countAllByAlbumId(long albumId);
}
