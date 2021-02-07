package com.tangzhangss.commondata.album;


import com.tangzhangss.commonutils.base.SysBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController extends SysBaseController<AlbumEntity, AlbumService> {

}
