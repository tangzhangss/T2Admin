package io.report.modules.ser.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据集表关系表
 * 
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:09
 */
@TableName("dt_table_rs_condition")
public class DtTableRsConditionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 关系编号
	 */
	@TableId
	private Integer cdId;
	/**
	 * 关系类型
	 */
	private String cdType;
	/**
	 * 输出名称
	 */
	private String rename;
	/**
	 * 左侧表
	 */
	private String leftTable;
	/**
	 * 左侧字段
	 */
	private String leftCol;
	/**
	 * 操作符
	 */
	private String operator;
	/**
	 * 右侧表
	 */
	private String rightTable;
	/**
	 * 右侧字段
	 */
	private String rightCol;
	/**
	 * 创建时间
	 */
	private Date txTime;
	/**
	 * 登记人
	 */
	private String txOp;

	/**
	 * 设置：关系编号
	 */
	public void setCdId(Integer cdId) {
		this.cdId = cdId;
	}
	/**
	 * 获取：关系编号
	 */
	public Integer getCdId() {
		return cdId;
	}
	/**
	 * 设置：关系类型
	 */
	public void setCdType(String cdType) {
		this.cdType = cdType;
	}
	/**
	 * 获取：关系类型
	 */
	public String getCdType() {
		return cdType;
	}
	/**
	 * 设置：输出名称
	 */
	public void setRename(String rename) {
		this.rename = rename;
	}
	/**
	 * 获取：输出名称
	 */
	public String getRename() {
		return rename;
	}
	/**
	 * 设置：左侧表
	 */
	public void setLeftTable(String leftTable) {
		this.leftTable = leftTable;
	}
	/**
	 * 获取：左侧表
	 */
	public String getLeftTable() {
		return leftTable;
	}
	/**
	 * 设置：左侧字段
	 */
	public void setLeftCol(String leftCol) {
		this.leftCol = leftCol;
	}
	/**
	 * 获取：左侧字段
	 */
	public String getLeftCol() {
		return leftCol;
	}
	/**
	 * 设置：操作符
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 获取：操作符
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 设置：右侧表
	 */
	public void setRightTable(String rightTable) {
		this.rightTable = rightTable;
	}
	/**
	 * 获取：右侧表
	 */
	public String getRightTable() {
		return rightTable;
	}
	/**
	 * 设置：右侧字段
	 */
	public void setRightCol(String rightCol) {
		this.rightCol = rightCol;
	}
	/**
	 * 获取：右侧字段
	 */
	public String getRightCol() {
		return rightCol;
	}
	/**
	 * 设置：创建时间
	 */
	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getTxTime() {
		return txTime;
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
}
