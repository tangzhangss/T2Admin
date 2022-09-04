package io.report.modules.ser.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据集表
 * 
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@TableName("data_set")
public class DataSetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据集编号
	 */
	@TableId
	private Integer dtId;
	/**
	 * 数据集名称
	 */
	private String dtName;
	/**
	 * 数据源编号
	 */
	private Integer dsId;
	/**
	 * 数据集分类
	 */
	private String type;
	/**
	 * 数据集SQL
	 */
	@TableField("sql_str")
	private String sql;
	/**
	 * 数据来源类型
	 */
	private String dataType;
	/**
	 * 数据集状态
	 */
	private String sts;
	/**
	 * 登记日期
	 */
	@TableField(el = "txTime, jdbcType=DATE")
	private Date txTime;
	/**
	 * 更新日期
	 */
	private Date upTime;
	/**
	 * 登记人
	 */
	@TableField(el = "txOp, jdbcType=VARCHAR")
	private String txOp;
	/**
	 * 更新人
	 */
	private String upOp;
	/**
	 * 排序字段
	 */
	@TableField(el = "orderByStr, jdbcType=VARCHAR")
	private String orderByStr;

	private String params;

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
	 * 设置：数据集名称
	 */
	public void setDtName(String dtName) {
		this.dtName = dtName;
	}
	/**
	 * 获取：数据集名称
	 */
	public String getDtName() {
		return dtName;
	}
	/**
	 * 设置：数据源编号
	 */
	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}
	/**
	 * 获取：数据源编号
	 */
	public Integer getDsId() {
		return dsId;
	}
	/**
	 * 设置：数据集分类
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：数据集分类
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 设置：数据集SQL
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}
	/**
	 * 获取：数据集SQL
	 */
	public String getSql() {
		return sql;
	}
	/**
	 * 设置：数据集状态
	 */
	public void setSts(String sts) {
		this.sts = sts;
	}
	/**
	 * 获取：数据集状态
	 */
	public String getSts() {
		return sts;
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
	
	/**
	 * 设置：数据来源类型
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * 获取：数据来源类型
	 */
	public String getDataType() {
		return dataType;
	}
	public String getOrderByStr() {
		return orderByStr;
	}
	public void setOrderByStr(String orderByStr) {
		this.orderByStr = orderByStr;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
}
