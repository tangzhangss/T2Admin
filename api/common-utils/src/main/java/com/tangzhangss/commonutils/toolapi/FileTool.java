package com.tangzhangss.commonutils.toolapi;

import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;


@Api(tags = "文件处理通用接口")
@RestController
@RequestMapping("/file_tool")
public class FileTool {
    /**
     * excel解析
     * @param file 非必需
     * @param fileUrl 非必需（如果有，以这个为主）
     * @param isVerifiable 是否需要校验 默认校验 如果需要校验excel格式需要变化，相见具体方法注释
     */
    @ApiOperation("Excel解析")
    @PostMapping("/analysis_excel")
    public Result analysisExcel(@RequestParam(name = "file",required = false) MultipartFile file,
                                @RequestParam(name = "url",required = false)String fileUrl,
                                @RequestParam(name = "isVerifiable",required = false,defaultValue = "true")Boolean isVerifiable) throws Exception {
        File excelFile=null;
        if(fileUrl!=null){
            excelFile= FileUtil.urlPathTransferToFile(fileUrl);
        }else{
            excelFile= FileUtil.multipartFileTransferToFile(file);

        }
        if (excelFile == null || !FileUtil.validateExcel(excelFile.getName())) ExceptionUtil.throwException("invalid_excel");
        Object analysisResult;

        if(isVerifiable){
            List<List<Object>> excelData = FileUtil.analysisExcelVerifiable(excelFile);
            JSONObject data=FileUtil.getExcelHeaderData(excelData);
            List<Map<Object, Object>> resData = FileUtil.getJsonObject(excelData);
            JSONObject json = new JSONObject();
            json.set("header",data);
            json.set("body",resData);
            analysisResult=json;
        }else{
            analysisResult=FileUtil.analysisExcel(excelFile);
        }

        return new Result(HttpStatus.OK,analysisResult);
    }

}
