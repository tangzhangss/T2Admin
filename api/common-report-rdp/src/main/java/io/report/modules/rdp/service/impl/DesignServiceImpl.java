package io.report.modules.rdp.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.t2admin.T2AdminUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.hutool.core.util.StrUtil;
import io.report.common.db.bean.DataSourceBean;
import io.report.common.db.bean.TableColumnBean;
import io.report.common.db.util.DBUtil;
import io.report.modules.rdp.bean.ReportFileBean;
import io.report.modules.rdp.bean.XMLbean;
import io.report.modules.rdp.entity.json.DataParms;
import io.report.modules.rdp.service.DesignService;
import io.report.modules.rdp.util.DesignXmlUtil;
import io.report.modules.rdp.util.SqlParserUtil;
import io.report.modules.rdp.util.Tools;
import io.report.modules.ser.entity.DataSourceEntity;
import io.report.modules.ser.service.DataSourceService;

import static io.report.common.db.util.SqlHandler.getSqlPrep;

@Service("designService")
public class DesignServiceImpl implements DesignService {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataSourceService dataSourceService;

	/**
	 * 根据条件查询报表
	 *
	 * @param realPath
	 * @param conditions 查询条件
	 * @return
	 */
	@Override
	public List<ReportFileBean> findAllReportFile(String realPath, ReportFileBean conditions) {
		List<ReportFileBean> list = new ArrayList<ReportFileBean>();
		try {
			File file = new File(realPath);
			File[] files = file.listFiles();
			Comparator<File> comparator = new Comparator<File>() {
				@Override
				public int compare(File f1, File f2) {
					Long a = new Long(f1.lastModified());
					Long b = new Long(f2.lastModified());
					if (a.longValue() == b.longValue()) {
						return 1;
					} else {
						return (a < b) ? 1 : -1;
					}
				}
			};
			// 使用以前版本的sort来排序
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			try {
				Arrays.sort(files, comparator);
			} catch (Exception e) {

			}
			for (File f : files) {
				if (!f.isFile()) {
                    continue;
                }
				try {
					//SAXReader reader = new SAXReader();// 创建SAXReader对象
					// reader.setEncoding("utf8");
					//Document document = reader.read(f);
					String xmlStr = DesignXmlUtil.readToString(f.getPath());
					Document document = DocumentHelper.parseText(xmlStr);
					Element root = document.getRootElement();
					String uuid = root.attributeValue("uuid");
					Double version = root.attributeValue("version") != null ? Double.parseDouble(root.attributeValue("version")) : 1.0d;
					String reportStyle = root.attributeValue("reportStyle");
					String reportDesc = root.attributeValue("reportDescription");
					String mainUuid = root.attributeValue("mainUuid");
					String reprotVersion = root.attributeValue("reportVersion");
					String reprotMemo = root.attributeValue("reportMemo");

					// 为没有版本号的报表增加报表版本、简要描述、主版本UUID-2016-12-13
					String reportVersion = root.attributeValue("reportVersion");
					String iscustom = root.attributeValue("iscustom");
					if ("1".equals(iscustom)) {
						continue;
					}

					ReportFileBean reportFileBean = new ReportFileBean();
					// Calendar cal = Calendar.getInstance();
					// cal.setTimeInMillis(f.lastModified());
					reportFileBean.setReportStyle((reportStyle.equals("D") ? "动态模板" : (reportStyle.equals("N")) ? "普通模板" : "图表"));
					reportFileBean.setUuid(mainUuid);
					reportFileBean.setVersion(version);
					reportFileBean.setName(reportDesc);
					// 根据查询条件查询报表
					if (conditions == null || conditions.getName() == null || conditions.getName().equals("") || reportDesc.contains(conditions.getName()) || uuid.contains(conditions.getName())) {
						ReportFileBean reportBean = null;
						for (ReportFileBean temp : list) {
							if (mainUuid != null && temp.getUuid() != null) {
								if (temp.getUuid().equals(mainUuid)) {
									reportBean = temp;
								}
							}
						}

						if (reportBean == null) {
							list.add(reportFileBean);
						} else {
							reportFileBean = reportBean;
						}
						ReportFileBean bean = new ReportFileBean();
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(f.lastModified());
						bean.setReportStyle((reportStyle.equals("D") ? "动态模板" : (reportStyle.equals("N")) ? "普通模板" : "图表"));
						bean.setUuid(uuid);
						bean.setVersion(version);
						bean.setName(reportDesc);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						bean.setLastEditDate(formatter.format(cal.getTime()));
						bean.setReportVersion(reprotVersion);
						bean.setReportMemo(reprotMemo);
						bean.setMainUuid(mainUuid);

						reportFileBean.getReportFiles().add(bean);
					}
				}catch (Exception e) {
					System.out.println("报表文件："+f.getName()+"。无法读取,请检查源文件。");
					e.printStackTrace();
				}
			}

			for (ReportFileBean bean : list) {
				Collections.sort(bean.getReportFiles(), new Comparator<ReportFileBean>() {
					@Override
					public int compare(ReportFileBean bean1, ReportFileBean bean2) {
						return (-1) * bean1.getReportVersion().compareTo(bean2.getReportVersion());
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询所有报表文件
	 *
	 * @param realPath 报表文件路径
	 * @return
	 */
	@Override
	public List<ReportFileBean> findAllReportFile(String realPath) {
		return findAllReportFile(realPath, null);
	}

	@Override
	public void deleteReport(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 取所有状态可用的DataSourceName
	 *
	 * @return
	 */
	@Override
	public List<DataSourceBean> selectAllDataSource() {
		List<DataSourceBean> list = new ArrayList<DataSourceBean>();
		Wrapper<DataSourceEntity> wrapper = new EntityWrapper<DataSourceEntity>();
		wrapper.eq("sts", "1");// 状态可用
		//		wrapper.ne("type", "Json");
		wrapper.ne("type", "json");//改成小写
		wrapper.eq("client_id", T2AdminUtil.getClientId());//添加租户Id
		List<DataSourceEntity> listds = dataSourceService.selectList(wrapper);
		DataSourceBean dsb;
		for (DataSourceEntity ds : listds) {
			dsb = new DataSourceBean();
			dsb.setDataSourceName(ds.getName());
			dsb.setModel(ds.getModel());
			dsb.setType(ds.getType());
			dsb.setDriver(ds.getDriver());
			dsb.setDataBaseUrl(ds.getAddr());
			dsb.setUserName(ds.getUsr());
			dsb.setPassword(ds.getPassword());
			dsb.setReadOnly(ds.getReadonly() != null && ds.getReadonly().equals("0") ? true : false);
			list.add(dsb);
		}
		return list;
	}

	/**
	 * 取所有状态可用的JSON数据源
	 *
	 * @return
	 */
	@Override
	public List<DataSourceBean> selectAllJSONNSource() {
		List<DataSourceBean> list = new ArrayList<DataSourceBean>();
		Wrapper<DataSourceEntity> wrapper = new EntityWrapper<DataSourceEntity>();
		wrapper.eq("sts", "1");// 状态可用
		//wrapper.eq("type", "Json");
		wrapper.eq("type", "json");//改成小写
		wrapper.eq("client_id", T2AdminUtil.getClientId());//添加租户Id
		List<DataSourceEntity> listds = dataSourceService.selectList(wrapper);
		DataSourceBean dsb;
		for (DataSourceEntity ds : listds) {
			dsb = new DataSourceBean();
			dsb.setDataSourceName(ds.getName());
			dsb.setModel(ds.getModel());
			dsb.setType(ds.getType());
			dsb.setDriver(ds.getDriver());
			dsb.setDataBaseUrl(ds.getAddr());
			dsb.setUserName(ds.getUsr());
			dsb.setPassword(ds.getPassword());
			list.add(dsb);
		}
		return list;
	}

	/**
	 * 解析字段
	 * @param dsb
	 * @param dsType
	 * @param sqlStr
	 * @param dtparm
	 * @param dtparms
	 * @return
	 */
	@Override
	public List<TableColumnBean> parFieldsForJSON(DataSourceBean dsb, String dsType, String sqlStr, String dtparm, List<DataParms> dtparms) throws Exception{
		List<TableColumnBean> list = new ArrayList<TableColumnBean>();
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		if(dtparms!=null&&dtparms.size()>0) {
			for(int i=0,len=dtparms.size();i<len;i++) {
				DataParms dataParms = dtparms.get(i);
				if(dataParms!=null&&dataParms.getParmvl()!=null) {
					paramsMap.put(dataParms.getParmName(), dataParms.getParmvl());
				}
			}
		}
		sqlStr= Tools.preSqlParms(sqlStr,dtparm,dtparms);
		sqlStr=getSqlPrep(sqlStr);//预处理sql
		if (dsType == null || dsType.length() == 0 || "1".equals(dsType)) {// SQL查询
			sqlStr = sqlStr.replace("\n"," ").replace("&quot;", "\"").replace("&gt;", ">").replace("&lt;", "<").replace("&amp;", "&");
			if (sqlStr.endsWith(";")) {
				sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
			}

			if ("javabean".equals(dsb.getDataSourceName())) {
				if (sqlStr.indexOf("|") != -1) {
					sqlStr = sqlStr.split("\\|")[0];
				}
				dsb.setDataSourceName(dsb.getDataSourceName());
				dsb.setType(DBUtil.TYPE_JAVABEAN);
			}
			sqlStr = SqlParserUtil.getPageSql(dsb.getType(), sqlStr, 1, 1);
			list = DBUtil.getDbInfoUtil(dsb).getTableColumnListByDs(sqlStr,false,paramsMap);
		} else if ("2".equals(dsType) || "3".equals(dsType)) {// 存储过程或函数
			list = DBUtil.getDbInfoUtil(dsb).getTableColumnListByDs(sqlStr,true,paramsMap);
		}
		return list;
	}

}
