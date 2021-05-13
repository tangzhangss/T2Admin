package com.tangzhangss.commonservice.aliyun.oss;


import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/aliyunoss")
public class AliyunOssApi extends SysBaseApi<AliyunOssEntity, AliyunOssService> {

    @PostMapping("/upload_picture")
    public Result uploadPicture(@RequestParam("file") MultipartFile file) throws Exception {
        return new Result(HttpStatus.OK, myService.uploadPicture(file));
    }

    /*
    可删除的图片（需要保证一个图片都不能被引用两次以上，唯一）
     */
    @PostMapping("/upload_picture/deletable")
    public Result uploadPictureDeletable(@RequestParam("file") MultipartFile file) throws Exception {
        return new Result(HttpStatus.OK, myService.uploadPictureDeletable(file));
    }

    @PostMapping("/upload_picture/no_auth")
    public Result uploadPictureNoAuth(@RequestParam("file") MultipartFile file) throws Exception {
        return new Result(HttpStatus.OK, myService.uploadPictureNoAuth(file));
    }
    @DeleteMapping("/delete_by_urls")
    public Result deleteByUrls(@RequestBody List<String> urls){
        //删除文件有错误不需要处理
        try {
            myService.deleteByUrl(urls);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.ok;
    }


}
