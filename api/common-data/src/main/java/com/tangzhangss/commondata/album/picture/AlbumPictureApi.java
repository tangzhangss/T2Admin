package com.tangzhangss.commondata.album.picture;


import com.tangzhangss.commonutils.base.SysBaseApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album_pic")
public class AlbumPictureApi extends SysBaseApi<AlbumPictureEntity, AlbumPictureService> {

}
