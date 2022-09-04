package io.report.modules.rdp.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.t2admin.SysContext;
import com.t2admin.T2AdminUtil;
import com.t2admin.entity.ReportFile;
import com.t2admin.service.ReportFileServiceImpl;
import io.report.common.annotation.SysLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpUtil;
import io.report.common.db.bean.DataSourceBean;
import io.report.common.db.bean.TableBean;
import io.report.common.db.bean.TableColumnBean;
import io.report.common.db.util.DBUtil;
import io.report.common.utils.Base64Util;
import io.report.common.utils.R;
import io.report.common.utils.code.CodeUtil;
import io.report.modules.rdp.bean.ReportFileBean;
import io.report.modules.rdp.entity.JsonToXMLUtil;
import io.report.modules.rdp.entity.json.DataParms;
import io.report.modules.rdp.entity.json.JsonReportEntity;
import io.report.modules.rdp.entity.xml.ParmEntity;
import io.report.modules.rdp.entity.xml.ReportEntity;
import io.report.modules.rdp.service.DesignService;
import io.report.modules.rdp.skin.SkinUtil;
import io.report.modules.rdp.util.Cache;
import io.report.modules.rdp.util.DataSourceUtil;
import io.report.modules.rdp.util.DesignXmlUtil;
import io.report.modules.rdp.util.ImportExcelUtil;
import io.report.modules.ser.service.DataSourceService;

/*
 * 报表设计相关
 */
@RestController
@RequestMapping("/rdp")
public class RdpController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${report.rdp.data-path}")
    private String realPath;
    @Value("${report.relative-path}")
    private Boolean relativePath;
    @Autowired
    HttpServletRequest rq;
    @Autowired
    HttpServletResponse rp;

    @Autowired
    private ReportFileServiceImpl reportFileService;

    @Autowired
    private DesignService designService;

    @Autowired
    private DataSourceService dataSourceService;

    /**
     * 导入excel
     *
     * @param filedata // 要导入文件
     */

    @RequestMapping("/importExcelAction")
    public R ImportExcel(@RequestParam("filedata") MultipartFile filedata) {
        try {
            String filedataFileName = filedata.getOriginalFilename();
            String filedataContentType = filedata.getContentType();
            JsonReportEntity json = ImportExcelUtil.importExcel(filedata, filedataFileName, filedataContentType);
            return R.ok().put("data", json);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("导入出错");
        }
    }

    /**
     * 根据uuid取报表
     *
     * @param uuid
     * @return
     */
    @RequestMapping("/selectReport")
    public R SelectReport(@RequestParam("uuid") String uuid) {
        ReportEntity model = DesignXmlUtil.openXMLNew(T2AdminUtil.getDataPath(relativePath, realPath) + uuid + ".xml");
        if (model != null) {
            JsonReportEntity json = JsonToXMLUtil.XmlToJson(model);
            return R.ok().put("json", json);
        } else {
            return R.error("模板解析失败!");
        }
    }

    /**
     * 保存报表
     *
     * @param uuid
     * @param report
     * @return
     */
    @RequestMapping("/saveReport")
    public R SaveReport(@RequestParam("uuid") String uuid, @RequestParam("report") String report) {
        try {
            report = Base64Util.decode(report, "Unicode");
            /*
            拿到 报表的名字,uuid
             */
            JSONObject obj = JSON.parseObject(report);
            //先查询再更新
            EntityWrapper<ReportFile> ew = new EntityWrapper<>();
            ew.eq("uuid",uuid);
            ew.eq("client_id",SysContext.getClientId());
            ReportFile reportFile = reportFileService.selectOne(ew);
            if(reportFile==null)reportFile=new ReportFile();
            if(reportFile.getSystem()==true)return R.error("系统创建资料，不可修改！");
            if (reportFile==null)reportFile = new ReportFile();
            reportFile.setUuid(obj.getString("uuid"));
            reportFile.setName(obj.getString("reportDescription"));
            reportFile.setMemo(obj.getString("reportMemo"));
            reportFile.setClientId(SysContext.getClientId());
            reportFileService.insertOrUpdate(reportFile);
            //-----------------------------------------------------------------------------------
            JsonReportEntity json = JSON.parseObject(report, JsonReportEntity.class);
            ReportEntity re = JsonToXMLUtil.JsonToXml(json);
            String filePath = T2AdminUtil.getDataPath(relativePath, realPath) + re.getUuid() + ".xml";
            if (DesignXmlUtil.reportToXML(re, filePath)) {
                Cache.xmlMap.remove(re.getUuid());
                return R.ok("保存成功");
            } else {
                return R.error("保存失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存出错！");
        }
    }

    /**
     * 根据uuid删除报表
     *
     * @param uuid
     * @return
     */
    @RequestMapping("/deleteReport")
    public R DeleteReport(@RequestParam("uuid") String uuid) {
        try {
            EntityWrapper<ReportFile> ew = new EntityWrapper<>();
            ew.eq("uuid",uuid);
            ew.eq("client_id",SysContext.getClientId());
            ReportFile reportFile = reportFileService.selectOne(ew);
            if(reportFile.getSystem()==true)return R.error("系统创建资料，不可删除！");
            Map<String,Object> map = new HashMap<>();
            map.put("client_id",SysContext.getClientId());
            map.put("uuid",uuid);
            reportFileService.deleteByMap(map);
            designService.deleteReport(T2AdminUtil.getDataPath(relativePath, realPath) + uuid + ".xml");
            Cache.xmlMap.remove(uuid);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("删除失败！");
        }
    }

    /**
     * 根据uuid取报表
     *
     * @param uuid
     * @return
     */
    @RequestMapping("/viewReport")
    public R ViewReport(@RequestParam("uuid") String uuid) {
        try {
            String filepath = T2AdminUtil.getDataPath(relativePath, realPath) + uuid + ".xml";
            String fileContent = DesignXmlUtil.readToString(filepath);
            return R.ok().put("data", fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("删除失败！");
        }
    }

    /**
     * 查询所有的报表
     *
     * @return
     */
    @RequestMapping("/selectAllReportFile")
    public R SelectAllReportFile(@RequestParam(name = "kw") String kw) {
        try {
            List<ReportFileBean> list = designService.findAllReportFile(T2AdminUtil.getDataPath(relativePath, realPath));
            List<ReportFileBean> showlist = new ArrayList<ReportFileBean>();
            if (kw != null && !"".equals(kw) && !"undefined".equals(kw)) {
                for (ReportFileBean node : list) {
                    if (node.getName().indexOf(kw) > -1 || node.getUuid().indexOf(kw) > -1) {
                        showlist.add(node);
                    }
                }
            } else {
                showlist.addAll(list);
            }

            return R.ok().put("list", showlist);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("读取错误！");
        }
    }

    /**
     * 查询所有的报表
     *  从数据库中查询
     * @return
     */
    @RequestMapping("/selectAllReportFile2")
    public R SelectAllReportFile2(@RequestParam(name = "kw") String kw) {
        try {
            String clientId=SysContext.getClientId();
            if(clientId==null) return R.error("登录失效，请刷新重试！");
            EntityWrapper<ReportFile> ew = new EntityWrapper<>();
            ew.eq("client_id",clientId);
            if (StringUtils.isNotBlank(kw)){
                ew.like("name",kw);
            }
            List<ReportFile> reports = reportFileService.selectList(ew);
            List<ReportFile> showList = new ArrayList<>();
            //还需要比较一下，如果本地文件没有的直接删掉
            List<ReportFileBean> list = designService.findAllReportFile(T2AdminUtil.getDataPath(relativePath, realPath));
            for (ReportFile report:reports){
                boolean flag=false;
                for (ReportFileBean reportFileBean:list){
                    if (report.getUuid().equals(reportFileBean.getUuid())){
                        flag=true;break;
                    }
                }
                if(flag)showList.add(report);
            }
            showList = showList.stream().sorted(Comparator.comparing(ReportFile::getName)).collect(Collectors.toList());
            //---------------比较完毕------------
            return R.ok().put("list", showList);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("读取错误！");
        }
    }


    /**
     * 查询报表的所有参数
     *
     * @param uuid
     * @return
     */
    @RequestMapping("/selectAllParmsByUUID")
    public R SelectAllParmsByUUID(@RequestParam("uuid") String uuid) {
        try {
            List<ParmEntity> list = DesignXmlUtil.getParmsByUUID(T2AdminUtil.getDataPath(relativePath, realPath) + uuid + ".xml");
            Map<String, Object> map = new HashMap<String, Object>();
            for (ParmEntity entity : list) {
                map.put(entity.getName(), "");
            }
            return R.ok().put("list", map);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取参数出错！");
        }
    }

    /**
     * 取所有可用的数据源
     *
     * @return
     */
    @RequestMapping("/selectAllDataSourceName")
    public R SelectAllDataSourceName() {
        try {
            List<DataSourceBean> list = designService.selectAllDataSource();
            return R.ok().put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存出错！");
        }
    }

    /**
     * 取所有可用的数据源
     *
     * @return
     */
    @RequestMapping("/selectAllJSONName")
    public R selectAllJSONName() {
        try {
            List<DataSourceBean> list = designService.selectAllJSONNSource();
            return R.ok().put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存出错！");
        }
    }

    /**
     * @param dataSourceName 数据源名称
     * @param dataSetType    数据集类型
     * @param parm           数据集参数名称(以逗号间隔)
     * @param parms          报表参数集
     * @param value          sql语句
     * @return
     */
    @RequestMapping("/parFieldsForJSON")
    public R ParFieldsForJSON(@RequestParam("dataSourceName") String dataSourceName, @RequestParam("dataSetType") String dataSetType, @RequestParam("parm") String parm, @RequestParam("parms") String parms, @RequestParam("value") String value) {
        try {
            DataSourceBean dsb = new DataSourceBean();
            List<DataParms> parmsList = new ArrayList<DataParms>();
            if (StringUtils.isNotBlank(parms)) {
            	parmsList = JSONObject.parseArray(parms, DataParms.class);
            }
            Map<String, String> dataMap = new HashMap<String, String>();
            if (dataSourceName != null && !dataSourceName.equals("javabean") && !dataSourceName.equals("json")) {
                if (Cache.dataSourceBeanMap.get(dataSourceName) != null) {
                    dsb = Cache.dataSourceBeanMap.get(dataSourceName);
                } else {
                    dsb = dataSourceService.getDataSourceBeanByDataSourceName(dataSourceName);
                }
                dataMap = DBUtil.connTest(dsb);
            } else {
                dsb.setType(dataSourceName.toLowerCase());
                dataMap.put("flag", "1");
            }
            if (dataMap != null && dataMap.size() > 0) {
                if ("1".equals(dataMap.get("flag"))) {
                    String sqlStr = Base64Util.decode(value, "Unicode");
                    String errorMsg = "";
                    List<TableColumnBean> list = new ArrayList<TableColumnBean>();
                    try {
                    	list = designService.parFieldsForJSON(dsb, dataSetType, sqlStr, parm, parmsList);
                    }catch (Exception e) {
                    	if(e.getMessage()!=null&&e.getMessage().startsWith("ORA-")) {
        					errorMsg = e.getMessage()+"，请检查SQL是否编写错误，或数据源选择错误。";
        				}else if(e.getMessage()!=null){
        					errorMsg = e.getMessage();
        				}
					}
                    if (list != null && list.size() > 0) {
                    	String columnKey = "[\\u4e00-\\u9fa5\\x3130-\\x318F\\xAC00-\\xD7A\\u0800-\\u4e00_a-zA-Z0-9]+";
                    	Pattern columnPattern = Pattern.compile(columnKey);
                    	for(TableColumnBean columnBean:list) {
                    		String columnName = columnBean.getColumnName();
                    		if(!columnPattern.matcher(columnName).matches()) {
                    			errorMsg="Sql字段校验不合法，数据集字段存在特殊字符，如存在合计统计函数之类的字段请使用别名方式进行处理";
                    			break;
                    		}
                    	}
                    	if("".equals(errorMsg)) {
                    		return R.ok().put("list", list);
                    	}else {
                    		return R.error(errorMsg);
                    	}
                    } else {
                    	if("".equals(errorMsg)) {
                    		return R.error("SQL语句编写不合法或参数获取配置不正确、请检查SQL。");
                    	}else {
                    		return R.error(errorMsg);
                    	}
                    }
                } else {
                    return R.error(dataMap.get("msg"));
                }
            } else {
                return R.error("数据库连接失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存出错！");
        }
    }

    /**
     * 取所有的字段
     *
     * @param dataSourceName 数据源名称
     * @param path           服务地址
     * @param method         请求方式
     * @return
     */
    @RequestMapping("/getJSONDataByUrl")
    public R getJSONDataByUrl(@RequestParam("dataSourceName") String dataSourceName, @RequestParam("path") String path, @RequestParam("method") String method) {
        String result = "";
        try {
            DataSourceBean dataSourceBean = dataSourceService.getDataSourceBeanByDataSourceName(dataSourceName);
            String url = dataSourceBean.getDataBaseUrl() + path;
            url = url.replace("&amp;", "&");
            if ("0".equals(method)) {
                result = HttpUtil.get(url);
            } else {
                result = HttpUtil.post(url, "");
            }
            System.out.println("取字段：" + url);
            try {
                return R.ok().put("list", JSONObject.parseObject(result));
            } catch (Exception ex) {
                try {
                    return R.ok().put("list", JSONObject.parseArray(result));
                } catch (Exception ex1) {
                    return R.error("获取出错！JSON数据格式不正常");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取出错！");
        }

    }

    /**
     * 根据数据源取表的信息
     *
     * @param dataSourceName
     * @return
     */
    @RequestMapping("/getTableInfo")
    public R getTableInfo(@RequestParam("dataSourceName") String dataSourceName) {
        try {
            DataSourceBean dataSourceBean = dataSourceService.getDataSourceBeanByDataSourceName(dataSourceName);
            try {
                List<TableBean> listb = new DataSourceUtil().getTableInfo(dataSourceBean);
                return R.ok().put("list", listb);
            } catch (Exception ex1) {
                return R.error("获取出错！JSON数据格式不正常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取出错！");
        }
    }

    /**
     * 根据数据源取表字段的信息
     *
     * @param dataSourceName
     * @param tableName
     * @return
     */
    @RequestMapping("/getColumnInfo")
    public R getColumnInfo(@RequestParam("dataSourceName") String dataSourceName, @RequestParam("tableName") String tableName) {
        try {
            DataSourceBean dataSourceBean = dataSourceService.getDataSourceBeanByDataSourceName(dataSourceName);
            try {
                List<TableColumnBean> listb = new DataSourceUtil().getColumnsInfo(dataSourceBean, tableName);
                return R.ok().put("list", listb);
            } catch (Exception ex1) {
                return R.error("获取出错！JSON数据格式不正常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取出错！");
        }
    }

    /**
     * 获取条形码信息
     *
     * @param barType
     * @param barImage
     * @return
     */
    @RequestMapping("/getBarCode")
    public R getBarCode(@RequestParam("barType") String barType,
                        @RequestParam("barSize") String barSize,
                        @RequestParam("width") Integer width,
                        @RequestParam("height") Integer height,
                        @RequestParam("barImage") String barImage,
                        @RequestParam("showContentFlag") Boolean showContentFlag) {
        try {
            CodeUtil codeUtil = new CodeUtil();
            String base64Str = codeUtil.showImageCode(barType, width, height, barImage, showContentFlag);
            return R.ok().put("imageStr", base64Str);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取出错！");
        }
    }

    /**
     * 清空数据字典缓存
     *
     * @return
     */
    @RequestMapping("/refreshDic")
    public R refreshDic() {
        Cache.dicMap.clear();
        return R.ok();
    }

    /**
     * 检查自定义皮肤是否存在
     *
     * @param fileName
     * @return
     */
    @RequestMapping("/checkSkin")
    public R checkSkin(@RequestParam("fileName") String fileName) {
        if (SkinUtil.exsitCustomFile(fileName)) {
            return R.error("皮肤已经存在了");
        } else {
            return R.ok();
        }
    }

    /**
     * 取所有的自定义皮肤
     *
     * @return
     */
    @RequestMapping("/customSkinList")
    public R customSkinList() {
        List<String> lists = SkinUtil.getCustomFiles();
        return R.ok().put("list", lists);
    }

    /**
     * 取自定义皮肤对象
     *
     * @return
     */
    @RequestMapping("/getCustomSkin")
    public R getCustomSkin(@RequestParam("fileName") String fileName) {
        net.sf.json.JSONObject json = SkinUtil.getSustomSkinJson(fileName);
        return R.ok().put("json", json);
    }

    /**
     * 删除自定义皮肤
     *
     * @return
     */
    @RequestMapping("/removeCustomSkin")
    public R removeCustomSkin(@RequestParam("fileName") String fileName) {
        Boolean flag = SkinUtil.removeSustomSkinJson(fileName);
        if (flag) {
            return R.ok();
        } else {
            return R.error("删除失败！");
        }
    }

    /**
     * 保存自定义皮肤
     *
     * @return
     */
    @RequestMapping("/saveCustomSkin")
    public R saveCustomSkin(@RequestParam("fileName") String fileName, @RequestParam("content") String content) {
        SkinUtil.saveSustomSkinJson(fileName, content);
        return R.ok();
    }

    /**
     * 导入模板
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public R upload(MultipartFile file) {
        R result = new R();
        if (file == null) {
            result = R.error("file不能为空");
            return result;
        }
        String fileName = file.getOriginalFilename(); //获取文件名
        String filePathName = T2AdminUtil.getDataPath(relativePath, realPath) + File.separator + fileName;
        try {
            file.transferTo(new File(filePathName));
            if (fileName.toLowerCase().endsWith(".zip")) {
                ZipUtil.unzip(filePathName, realPath);
                new File(filePathName).delete();
            }
            result = R.ok();
        } catch (IOException e) {
            result = R.error(e.getMessage());
        }
        return result;
    }

    /**
     * 导出选中模板
     */
    @SysLog("导出选中模板")
    @RequestMapping("/down")
    public void down() {
        String uuIds=rq.getParameter("uuIds");
        if(uuIds!=null) {
            String zipFile = "report.zip";
            try {
                rp.reset();
                zipFile = URLEncoder.encode(zipFile, "UTF-8");
                rp.addHeader("Content-Disposition", "attachment;filename=" + zipFile);
                rp.setContentType("multipart/form-data");
                rp.setHeader("Set-Cookie", "fileDownload=true; path=/");
                rp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                ZipOutputStream zos = new ZipOutputStream(rp.getOutputStream());
                BufferedOutputStream bos = new BufferedOutputStream(zos);
                List<String> uuids= JSONArray.parseArray(uuIds,String.class);
                for (int i = 0; i < uuids.size(); i++) {
                    String fname = uuids.get(i) + ".xml";
                    byte[] f = FileUtil.readBytes(T2AdminUtil.getDataPath(relativePath, realPath) + File.separator + uuids.get(i) + ".xml");

                    BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(f));
                    zos.putNextEntry(new ZipEntry(fname));

                    int len = 0;
                    byte[] buf = new byte[10 * 1024];
                    while ((len = bis.read(buf, 0, buf.length)) != -1) {
                        bos.write(buf, 0, len);
                    }
                    bis.close();
                    bos.flush();
                }
                bos.close();
            } catch (Exception ex) {
                rp.setHeader("Set-Cookie", "fileDownload=false; path=/");
                rp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                logger.error(ex.getMessage());
            }
        }else{
            rp.setHeader("Set-Cookie", "fileDownload=false; path=/");
            rp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        }
    }

    /**
     * 导出全部模板
     */
    @SysLog("导出全部模板")
    @RequestMapping("/downall")
    public void downall() {
        String zipFile = "reportfiles.zip";
        try {
            rp.reset();
            zipFile = URLEncoder.encode(zipFile, "UTF-8");
            rp.addHeader("Content-Disposition", "attachment;filename=" + zipFile);
            rp.setContentType("multipart/form-data");
            rp.setHeader("Set-Cookie", "fileDownload=true; path=/");
            rp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            ZipOutputStream zos = new ZipOutputStream(rp.getOutputStream());
            BufferedOutputStream bos = new BufferedOutputStream(zos);
            List<File> files=FileUtil.loopFiles(T2AdminUtil.getDataPath(relativePath, realPath));
            for(File file : files){
                String fname = file.getName();            //每个zip文件名
                byte[]  f = FileUtil.readBytes(file);            //这个zip文件的字节

                BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(f));
                zos.putNextEntry(new ZipEntry(fname));

                int len = 0;
                byte[] buf = new byte[10 * 1024];
                while( (len=bis.read(buf, 0, buf.length)) != -1){
                    bos.write(buf, 0, len);
                }
                bis.close();
                bos.flush();
            }
            bos.close();
        }catch (Exception ex){
            rp.setHeader("Set-Cookie", "fileDownload=false; path=/");
            rp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            logger.error(ex.getMessage());
        }
    }
}
