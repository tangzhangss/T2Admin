package com.tangzhangss.commondata.album.picture;


import com.tangzhangss.commonutils.base.SysBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album_pic")
public class AlbumPictureController extends SysBaseController<AlbumPictureEntity, AlbumPictureService> {

}
