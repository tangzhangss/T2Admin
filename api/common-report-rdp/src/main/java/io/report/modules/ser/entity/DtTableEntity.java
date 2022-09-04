package io.report.modules.ser.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 据集使用表
 * 
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@TableName("dt_table")
public class DtTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer useId;
	/**
	 * 数据集编号
	 */
	private Integer dtId;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 表说明
	 */
	private String tableComments;
	/**
	 * 层级
	 */
	private String level;
	/**
	 * 显示名称
	 */
	private String showName;
	/**
	 * 横向位置
	 */
	private Integer px;
	/**
	 * 纵向位置
	 */
	private Integer py;
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
	 * 设置：编号
	 */
	public void setUseId(Integer useId) {
		this.useId = useId;
	}
	/**
	 * 获取：编号
	 */
	public Integer getUseId() {
		return useId;
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
	 * 设置：表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 获取：表名
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * 设置：表说明
	 */
	public void setTableComments(String tableComments) {
		this.tableComments = tableComments;
	}
	/**
	 * 获取：表说明
	 */
	public String getTableComments() {
		return tableComments;
	}
	/**
	 * 设置：层级
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * 获取：层级
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * 设置：显示名称
	 */
	public void setShowName(String showName) {
		this.showName = showName;
	}
	/**
	 * 获取：显示名称
	 */
	public String getShowName() {
		return showName;
	}
	/**
	 * 设置：横向位置
	 */
	public void setPx(Integer px) {
		this.px = px;
	}
	/**
	 * 获取：横向位置
	 */
	public Integer getPx() {
		return px;
	}
	/**
	 * 设置：纵向位置
	 */
	public void setPy(Integer py) {
		this.py = py;
	}
	/**
	 * 获取：纵向位置
	 */
	public Integer getPy() {
		return py;
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
