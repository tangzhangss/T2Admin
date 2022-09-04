package io.report.modules.rdp.controller;


import com.t2admin.T2AdminUtil;
import io.report.modules.rdp.handler.ReportAnalysis;
import io.report.modules.rdp.handler.chartdata.DataCharts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/*
 * 报表设计相关
 */
@Controller
@RequestMapping("/rdppage")
public class RdpPageController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${report.rdp.iscellauto}")
    private int iscellauto;
    @Value("${report.rdp.data-path}")
    private String realPath;
    @Value("${report.relative-path}")
    private Boolean relativePath;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    /**
     * 报表调用页面20191025，以后解析及调用以此方法为准，其它方法仅维护
     * @param map
     * @param uuid
     * @return
     */
    @ResponseBody
    @RequestMapping("/main/{uuid}")
    public void index(Map<String, Object> map, @PathVariable("uuid") String uuid) {
        String realPath = this.realPath+File.separator+request.getParameter("u_client_id")+File.separator;
        ReportAnalysis.index(request, response, relativePath, realPath, map, iscellauto, uuid);
    }
    @ResponseBody
    @RequestMapping("/show")
    public void show(Map<String, Object> map) {
        String realPath = this.realPath+File.separator+request.getParameter("u_client_id")+File.separator;
        ReportAnalysis.show(request, response, relativePath, realPath, map, iscellauto, "");
    }
    @ResponseBody
    @RequestMapping("/show/{uuid}")
    public void show(Map<String, Object> map, @PathVariable("uuid") String uuid) {
        String realPath = this.realPath+File.separator+request.getParameter("u_client_id")+File.separator;
        ReportAnalysis.show(request, response, relativePath, realPath, map, iscellauto, uuid);
    }

    /**
     * 自定义报表20191104
     * @param map
     * @param uuid
     * @return
     */
    @ResponseBody
    @RequestMapping("/custom/{uuid}")
    public void custom(Map<String, Object> map,@PathVariable("uuid") String uuid) {
        String realPath = this.realPath+File.separator+request.getParameter("u_client_id")+File.separator;
        ReportAnalysis.custom(request, response, relativePath, realPath, map, iscellauto, uuid);
    }

    @ResponseBody
    @RequestMapping("/view/1")
    public void view(Map<String, Object> map) {
        String realPath = T2AdminUtil.handleRealPath(this.realPath);
        ReportAnalysis.view(request, response, relativePath, realPath, map, iscellauto, "");
    }

    @ResponseBody
    @RequestMapping("/getChartsData")
    public Map<String,Object> getChartsData(HttpServletRequest request) {
        String realPath = this.realPath+File.separator+request.getParameter("u_client_id")+File.separator;
        return new DataCharts(relativePath,realPath,request).getData();
    }
}
