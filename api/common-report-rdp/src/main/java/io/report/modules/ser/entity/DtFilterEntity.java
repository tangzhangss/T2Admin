package io.report.modules.ser.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据集过滤
 * 
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@TableName("dt_filter")
public class DtFilterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 过滤编号
	 */
	@TableId
	private Integer filterId;
	/**
	 * 数据集编号
	 */
	private Integer dtId;
	/**
	 * 过滤表名
	 */
	private String tableName;
	/**
	 * 过滤字段
	 */
	private String column;
	/**
	 * 操作符
	 */
	private String colType;
	/**
	 * 
	 */
	private String operator;
	/**
	 * 参数数据类型
	 */
	private String paramType;
	/**
	 * 参数
	 */
	private String param;
	/**
	 * 登记日期
	 */
	private Date txTime;
	/**
	 * 更新日期
	 */
	private Date upTime;
	/**
	 * 登记人
	 */
	private String txOp;
	/**
	 * 更新人
	 */
	private String upOp;

	/**
	 * 设置：过滤编号
	 */
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	/**
	 * 获取：过滤编号
	 */
	public Integer getFilterId() {
		return filterId;
	}
	/**
	 * 设置：数据集编号
	 */
	public void setDtId(Integer dtId) {
		this.dtId = dtId;
	}
	/**
	 * 获取：数据集编号
	 */
	public Integer getDtId() {
		return dtId;
	}
	/**
	 * 设置：过滤表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 获取：过滤表名
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * 设置：过滤字段
	 */
	public void setColumn(String column) {
		this.column = column;
	}
	/**
	 * 获取：过滤字段
	 */
	public String getColumn() {
		return column;
	}
	/**
	 * 设置：操作符
	 */
	public void setColType(String colType) {
		this.colType = colType;
	}
	/**
	 * 获取：操作符
	 */
	public String getColType() {
		return colType;
	}
	/**
	 * 设置：
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 获取：
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 设置：参数数据类型
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	/**
	 * 获取：参数数据类型
	 */
	public String getParamType() {
		return paramType;
	}
	/**
	 * 设置：参数
	 */
	public void setParam(String param) {
		this.param = param;
	}
	/**
	 * 获取：参数
	 */
	public String getParam() {
		return param;
	}
	/**
	 * 设置：登记日期
	 */
	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}
	/**
	 * 获取：登记日期
	 */
	public Date getTxDate() {
		return txTime;
	}
	/**
	 * 设置：更新日期
	 */
	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}
	/**
	 * 获取：更新日期
	 */
	public Date getUpTime() {
		return upTime;
	}
	/**
	 * 设置：登记人
	 */
	public void setTxOp(String txOp) {
		this.txOp = txOp;
	}
	/**
	 * 获取：登记人
	 */
	public String getTxOp() {
		return txOp;
	}
	/**
	 * 设置：更新人
	 */
	public void setUpOp(String upOp) {
		this.upOp = upOp;
	}
	/**
	 * 获取：更新人
	 */
	public String getUpOp() {
		return upOp;
	}
}
