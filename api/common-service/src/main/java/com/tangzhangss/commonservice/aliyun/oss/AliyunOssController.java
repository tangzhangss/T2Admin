package com.tangzhangss.commonservice.aliyun.oss;


import com.tangzhangss.commonutils.base.SysBaseController;
import com.tangzhangss.commonutils.resultdata.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/aliyunoss")
public class AliyunOssController extends SysBaseController<AliyunOssEntity, AliyunOssService> {

    @PostMapping("/upload_picture")
    public Result uploadPicture(@RequestParam("file") MultipartFile file) throws Exception {
        return new Result(HttpStatus.OK, myService.uploadPicture(file));
    }

    @PostMapping("/upload_picture/no_auth")
    public Result uploadPictureNoAuth(@RequestParam("file") MultipartFile file) throws Exception {
        return new Result(HttpStatus.OK, myService.uploadPictureNoAuth(file));
    }


}
