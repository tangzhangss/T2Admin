package com.tangzhangss.commondata.album;

import com.tangzhangss.commondata.album.picture.AlbumPictureDao;
import com.tangzhangss.commondata.album.picture.AlbumPictureEntity;
import com.tangzhangss.commondata.album.picture.AlbumPictureService;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService extends SysBaseService<AlbumEntity, AlbumDao> {

    @Autowired
    AlbumPictureService albumPictureService;
    @Autowired
    AlbumPictureDao albumPictureDao;

    @Override
    protected boolean bSureDelete() {
        return true;
    }

    @Override
    protected boolean isQueryAll() {
        return true;
    }

    @Override
    protected void beforeDeleteData(List<AlbumEntity> data) {
        super.beforeDeleteData(data);
        //判断是否存在图片，如果存在需要先删除图片（明细）
        data.forEach(d->{
            long sum = albumPictureDao.countAllByAlbumId(d.getId());
            if(sum>0) ExceptionUtil.throwException("#{0}相册下存在图片，请删删除图片!",d.getTitle());
        });
    }
}
