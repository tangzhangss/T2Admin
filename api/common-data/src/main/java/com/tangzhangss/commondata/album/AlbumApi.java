package com.tangzhangss.commondata.album;


import com.tangzhangss.commonutils.base.SysBaseApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumApi extends SysBaseApi<AlbumEntity, AlbumService> {

}
