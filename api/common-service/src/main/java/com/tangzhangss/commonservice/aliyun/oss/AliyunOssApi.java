package com.tangzhangss.commonservice.aliyun.oss;


import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.resultdata.ResultCode;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aliyunoss")
public class AliyunOssApi extends SysBaseApi<AliyunOssEntity, AliyunOssService> {

    @PostMapping("/upload_picture")
    public Result uploadPicture(@RequestParam("file") MultipartFile file) throws Exception {
        return new Result(HttpStatus.OK, myService.uploadPicture(file));
    }


    /**
     * 上传文件
     * @param file
     */
    @PostMapping("/upload_excel")
    public Result uploadExcel(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || !FileUtil.validateExcel(file.getOriginalFilename()))ExceptionUtil.throwException("请选择Excel文件");
        String url = myService.uploadFile(file,"excel");
        return new Result(HttpStatus.OK,url);
    }

    /**
     * excel解析
     * @param file 非必需
     * @param fileUrl 非必需（如果有，以这个为主）
     */
    @PostMapping("/analysis_excel")
    public Result analysisExcel(@RequestParam(name = "file",required = false)MultipartFile file,@RequestParam(name = "url",required = false)String fileUrl) throws Exception {
        File excelFile=null;
        if(fileUrl!=null){
            excelFile=FileUtil.urlPathTransferToFile(fileUrl);
        }else{
            excelFile= FileUtil.multipartFileTransferToFile(file);

        }
        if (excelFile == null || !FileUtil.validateExcel(excelFile.getName()))ExceptionUtil.throwException("请选择Excel文件");
        List<List<Object>> excelData = FileUtil.analysisExcel(excelFile);
        JSONObject data=FileUtil.getExcelHeaderData(excelData);
        List<Map<Object, Object>> resData = FileUtil.getJsonObject(excelData);
        JSONObject json = new JSONObject();
        json.set("header",data);
        json.set("body",resData);
        return new Result(HttpStatus.OK,json);
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
        return Result.ok();
    }


}
