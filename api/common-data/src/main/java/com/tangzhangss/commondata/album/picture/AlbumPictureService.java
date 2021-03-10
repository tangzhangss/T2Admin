package com.tangzhangss.commondata.album.picture;

import com.tangzhangss.commonutils.base.SysBaseService;
import org.springframework.stereotype.Service;

@Service
public class AlbumPictureService extends SysBaseService<AlbumPictureEntity, AlbumPictureDao> {
    @Override
    protected boolean bSureDelete() {
        return true;
    }
}
