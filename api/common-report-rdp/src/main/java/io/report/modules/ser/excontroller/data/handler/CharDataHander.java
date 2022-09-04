package io.report.modules.ser.excontroller.data.handler;

import java.util.List;

import io.report.common.db.handler.DbInfoUtil;
import io.report.modules.ser.entity.DataSetEntity;
import io.report.modules.ser.util.SerHandler;

/**
 * 图表数据处理
 * Title:CharDataHander.java
 * @author JIZH
 * @date 2018-09-05 08:58:46
 */
public class CharDataHander {
	
	private static final String DATA_TYPE_SQL = "sql";//数据集数据类型 -SQL
	private static final String DATA_TYPE_TABLE = "table";//数据集数据类型 -Table
	/**
	 * 获取图表数据
	 * @param dataSet
	 */
	public static Object getChardata(DataSetEntity dataSet,String []columns,String [] groups,String []series,String params[]) {
		if(DATA_TYPE_SQL.equals(dataSet.getDataType())) {//处理Sql
			DbInfoUtil d = SerHandler.getDbInfoUtil(dataSet);
			List<?> list = d.getGroupSeriesList(dataSet.getSql(),columns, groups,series,new String[0],"");
			return list;
		}else if(DATA_TYPE_TABLE.equals(dataSet.getDataType())) {
			
			
			
		}
		return null;
	}
}
