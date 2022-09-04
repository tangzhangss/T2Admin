package io.report.modules.ser.excontroller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.report.common.db.bean.TableData;
import io.report.common.db.handler.DbInfoUtil;
import io.report.common.db.util.SqlHandler;
import io.report.common.db.util.StringUtil;
import io.report.common.utils.Base64Util;
import io.report.common.utils.R;
import io.report.common.validator.ValidatorUtils;
import io.report.modules.rdp.util.SqlParserUtil;
import io.report.modules.ser.entity.DataSetEntity;
import io.report.modules.ser.entity.DataSourceEntity;
import io.report.modules.ser.service.DataSetService;
import io.report.modules.ser.service.DataSourceService;
import io.report.modules.ser.util.SerHandler;
import io.report.modules.sys.shiro.ShiroUser;

/**
 * 数据集表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("/ex/ser/dataset")
public class ExDataSetController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DataSetService dataSetService;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        DataSetEntity dataSet = new DataSetEntity();
        List<DataSetEntity> list = dataSetService.getList(dataSet);
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{dtId}")
    public R info(@PathVariable("dtId") Integer dtId) {
        DataSetEntity dataSet = dataSetService.selectById(dtId);
        return R.ok().put("dataSet", dataSet);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DataSetEntity dataSet) {
        dataSet.setTxOp(ShiroUser.getUserName());
        dataSet.setUpOp(ShiroUser.getUserName());
        dataSet.setTxTime(ShiroUser.getSysDate());
        dataSet.setUpTime(ShiroUser.getSysDate());
        dataSetService.insert(dataSet);
        return R.ok().put("dataSet", dataSet);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DataSetEntity dataSet) {
        ValidatorUtils.validateEntity(dataSet);
        dataSet.setUpOp(ShiroUser.getUserName());
        dataSet.setUpTime(ShiroUser.getSysDate());
        dataSetService.updateAllColumnById(dataSet);// 全部更新
        return R.ok().put("dataSet", dataSet);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] dtIds) {
        dataSetService.deleteBatchIds(Arrays.asList(dtIds));
        return R.ok();
    }

    /**
     * 返回结果
     */
    @RequestMapping("/result")
    public R getResult(@PathParam("dtId") Integer dtId){
    	DataSetEntity dataSet = dataSetService.selectById(dtId);
        DbInfoUtil d = SerHandler.getDbInfoUtil(dataSet);

        JSONObject jsonObject = JSON.parseObject(dataSet.getParams());
        Map<String, String> paramMap = JSONObject.toJavaObject(jsonObject, Map.class);
        String sqlstr =  SqlHandler.sqlParamFor$(dataSet.getSql(), paramMap);
        sqlstr = SqlParserUtil.getPageSql(dataSet.getType(), sqlstr, 1, 1);
		TableData td = d.getTableDataList(sqlstr, null);
        return R.ok().put("data", td);
    }

    /**
     * 返回结果
     */
    @RequestMapping("/sqlresult")
    @ResponseBody
    public R getSqlResult(@PathParam("dsId") String dsId, @PathParam("sql") String sql,
            @PathParam("orderByStr") String orderByStr) {
        DataSourceEntity dataSource = dataSourceService.selectById(dsId);
        DbInfoUtil d = SerHandler.getDbInfoUtil(dataSource);
        try {
            sql = Base64Util.decode(sql, "Unicode");
        } catch (Exception e) {
            logger.error("sql解码错误");
        }
        sql = "select * from (" + sql + ") R ";
        if (orderByStr != null && !"".equals(orderByStr)) {
            sql += "order by " + orderByStr;
        }
        TableData td = d.getTableDataList(sql, null);
        return R.ok().put("data", td);
    }

    /**
     * 返回结果
     */
    @RequestMapping("/sqlgroupresult")
    @ResponseBody
    public R getSqlGroupResult(@RequestParam Map<String, Object> params) {
        Object data = null;
        String dtId = (String) params.get("dtId");
        String type = (String) params.get("type");
        String columns[] = StringUtil.getObjectToArray((String) params.get("columns"), ",");
        String groups[] = StringUtil.getObjectToArray((String) params.get("groups"), ",");
        DataSetEntity dataSet = dataSetService.selectById(dtId);
        DbInfoUtil d = SerHandler.getDbInfoUtil(dataSet);

        JSONObject jsonObject = JSON.parseObject((String) params.get("params"));
        Map<String, String> paramMap = JSONObject.toJavaObject(jsonObject, Map.class);
        try {
            
            String sqlstr =  SqlHandler.sqlParamFor$(dataSet.getSql(), paramMap);
            if ("table".equals(type)) {
                if (dataSet.getOrderByStr() != null && !"".equals(dataSet.getOrderByStr().trim())) {
                    sqlstr = "select * from ( " + sqlstr + " ) r order by " + dataSet.getOrderByStr();
                }
                data = d.getTableDataAuto(sqlstr, new String[0]);
            } else {
                data = d.getGroupList(sqlstr, columns, groups, new String[0], dataSet.getOrderByStr());
            }
            return R.ok().put("data", data);
        } catch (Exception e) {
            return R.error("请检查参数是否配置正确");
        }

    }

    /**
     * 返回结果
     */
    @RequestMapping("/sqlgroupresult/series")
    @ResponseBody
    public R getSqlGroupSeriesResult(@RequestParam Map<String, Object> params) {
        Object data = null;
        String dtId = (String) params.get("dtId");
        String type = (String) params.get("type");
        DataSetEntity dataSet = dataSetService.selectById(dtId);
        DbInfoUtil d = SerHandler.getDbInfoUtil(dataSet);
        if ("table".equals(type)) {
            data = d.getTableDataAuto(dataSet.getSql(), new String[0]);
        } else {
            String columns[] = StringUtil.getObjectToArray((String) params.get("columns"), ",");
            String groups[] = StringUtil.getObjectToArray((String) params.get("groups"), ",");
            String series[] = StringUtil.getObjectToArray((String) params.get("series"), ",");
            data = d.getGroupSeriesList(dataSet.getSql(), columns, groups, series, new String[0],"");
        }
        return R.ok().put("data", data);
    }
}
