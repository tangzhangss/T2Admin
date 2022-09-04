package io.report.modules.ser.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据集参数表
 * 
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@TableName("dt_param")
public class DtParamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数编号
	 */
	@TableId
	private Integer paramId;
	/**
	 * 参数名称
	 */
	private String paramName;
	/**
	 * 数据集编号
	 */
	private Integer dtId;
	/**
	 * 参数类型
	 */
	private String paramType;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 是否多值
	 */
	private String ifValues;
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
	 * 设置：参数编号
	 */
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
	/**
	 * 获取：参数编号
	 */
	public Integer getParamId() {
		return paramId;
	}
	/**
	 * 设置：参数名称
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	/**
	 * 获取：参数名称
	 */
	public String getParamName() {
		return paramName;
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
	 * 设置：参数类型
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	/**
	 * 获取：参数类型
	 */
	public String getParamType() {
		return paramType;
	}
	/**
	 * 设置：数据类型
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * 获取：数据类型
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * 设置：是否多值
	 */
	public void setIfValues(String ifValues) {
		this.ifValues = ifValues;
	}
	/**
	 * 获取：是否多值
	 */
	public String getIfValues() {
		return ifValues;
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
	public Date getTxTime() {
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
