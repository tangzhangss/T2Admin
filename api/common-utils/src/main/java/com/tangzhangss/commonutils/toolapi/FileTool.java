package com.tangzhangss.commonutils.toolapi;

import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/file_tool")
public class FileTool {
    /**
     * excel解析
     * @param file 非必需
     * @param fileUrl 非必需（如果有，以这个为主）
     */
    @PostMapping("/analysis_excel")
    public Result analysisExcel(@RequestParam(name = "file",required = false) MultipartFile file, @RequestParam(name = "url",required = false)String fileUrl) throws Exception {
        File excelFile=null;
        if(fileUrl!=null){
            excelFile= FileUtil.urlPathTransferToFile(fileUrl);
        }else{
            excelFile= FileUtil.multipartFileTransferToFile(file);

        }
        if (excelFile == null || !FileUtil.validateExcel(excelFile.getName())) ExceptionUtil.throwException("请选择Excel文件");
        List<List<Object>> excelData = FileUtil.analysisExcel(excelFile);
        JSONObject data=FileUtil.getExcelHeaderData(excelData);
        List<Map<Object, Object>> resData = FileUtil.getJsonObject(excelData);
        JSONObject json = new JSONObject();
        json.set("header",data);
        json.set("body",resData);
        return new Result(HttpStatus.OK,json);
    }


}
