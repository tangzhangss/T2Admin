package com.tangzhangss.commonreport.api;

import cn.hutool.core.io.IoUtil;
import com.tangzhangss.commonutils.toolapi.FileTool;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.FileUtil;
import com.tangzhangss.commonutils.utils.RequestUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class CommonApi {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private HttpServletRequest request;
    @Value("${custom.staticFilePath}")
    private String staticFilePath;
    @Autowired
    private HttpServletResponse response;

    /**
     * 获取报表文件 默认 pdf格式
     * 支持xml html excel word
     * @param parameters 表表参数
     */
    @GetMapping("/print_report")
    public void printReport(@RequestParam(required = false) Map<String, Object> parameters) throws  Exception{
        String type = String.valueOf(parameters.getOrDefault("type","PDF"));
        String fileUrl = (String) parameters.get("fileUrl");
        if(StringUtils.isBlank(fileUrl)) ExceptionUtil.throwException("fileUrl参数不能为空");

        File file = FileUtil.urlPathTransferToFile(fileUrl);
        //获取文件流
        InputStream jasperStream = new FileInputStream(file);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
        //在线预览PDF
        response.setContentType("utf-8");
        response.setHeader("Content-Disposition", "inline;");
        switch (type.toUpperCase()){
            case "WORD":{
                //可以显示中午 docx
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                JRDocxExporter docExporter = new JRDocxExporter();

                try(OutputStream outputStream = response.getOutputStream()){
                    docExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    docExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                    SimpleDocxExporterConfiguration configuration = new SimpleDocxExporterConfiguration();

                    docExporter.setConfiguration(configuration);
                    docExporter.exportReport();
                }
                break;
            }
            case "EXCEL":{//可以显示中文
                response.setContentType("application/vnd.ms-excel");
                JRXlsExporter exporter = new JRXlsExporter();
                try(OutputStream outputStream = response.getOutputStream()){
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                    SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
//                    configuration.setOnePagePerSheet(true);//在一个excel中，每个单独的jasper对象（页数）放入到一个sheet页中
//                    configuration.setSheetNames(sheetNames);//页名称集合
//                    configuration.setDetectCellType(true);// 检查单元格格式
                    configuration.setWhitePageBackground(false);//去除白边
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                }
                break;
            }
            case "HTML":{//可以显示中文
                response.setContentType("text/html");
                File tempFile = File.createTempFile("jasper", "html");
                JasperExportManager.exportReportToHtmlFile(jasperPrint,tempFile.getAbsolutePath());
                //。。。
                try(OutputStream outputStream = response.getOutputStream();InputStream fileInputStream = new FileInputStream(tempFile)){
                    IoUtil.copy(fileInputStream,outputStream);
                    outputStream.flush();
                }
                break;
            }
            case "XML":{//可以显示中文
                response.setContentType("text/xml");
                try(OutputStream outputStream = response.getOutputStream()){
                    JasperExportManager.exportReportToXmlStream(jasperPrint,outputStream);
                    outputStream.flush();
                }
                break;
            }
            default:{
                response.setContentType("application/pdf");
                try(OutputStream outputStream = response.getOutputStream()){
                    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                }
            }
        }
    }
}
