package io.report.modules.ser.util;

import io.report.common.db.bean.DataSourceBean;
import io.report.common.db.handler.DbInfoUtil;
import io.report.common.db.util.DBUtil;
import io.report.common.utils.SpringUtil;
import io.report.modules.ser.entity.DataSetEntity;
import io.report.modules.ser.entity.DataSourceEntity;
import io.report.modules.ser.service.DataSourceService;

public class SerHandler {

	/**
	 * 获取数据集bean实体类
	 * @param entity
	 * @return
	 */
	public static DataSourceBean getDataSourceBean(DataSourceEntity entity) {
		DataSourceBean dataSourceBean = new DataSourceBean();
		dataSourceBean.setDataSourceName(entity.getName());
		dataSourceBean.setModel(entity.getModel());
		dataSourceBean.setDriver(entity.getDriver());
		dataSourceBean.setDataBaseUrl(entity.getAddr());
		dataSourceBean.setUserName(entity.getUsr());
		dataSourceBean.setPassword(entity.getPassword());
		dataSourceBean.setType(entity.getType());
		return dataSourceBean;
	}
	
	/**
	 * 获取数据集bean实体类
	 * @param entity
	 * @return
	 */
	public static DataSourceBean getDataSourceBean(DataSetEntity dtEntity) {
		DataSourceService dsService = (DataSourceService)SpringUtil.getBean(DataSourceService.class);
		DataSourceEntity entity = dsService.selectById(dtEntity.getDsId());
		return getDataSourceBean(entity);
	}
	/**
	 * 获取 DbInfoUtil
	 * @param entity
	 * @return
	 */
	public static DbInfoUtil getDbInfoUtil(DataSetEntity dateSet) {
		DataSourceBean dataSourceBean = getDataSourceBean(dateSet);
		return DBUtil.getDbInfoUtil(dataSourceBean);
	}
	/**
	 * 获取 DbInfoUtil
	 * @param entity
	 * @return
	 */
	public static DbInfoUtil getDbInfoUtil(DataSourceEntity entity) {
		DataSourceBean dataSourceBean = getDataSourceBean(entity);
		return DBUtil.getDbInfoUtil(dataSourceBean);
	}
	
}
