package io.report.modules.ser.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据集表显示字段表
 * 
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@TableName("ds_showcol")
public class DsShowcolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 显示编号
	 */
	@TableId
	private Integer showId;
	/**
	 * 数据集编号
	 */
	private Integer dtId;
	/**
	 * 引用表编号
	 */
	private Integer useId;
	/**
	 * 是否显示
	 */
	private String isShow;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 别名
	 */
	private String alias;
	/**
	 * 描述
	 */
	private String describe;
	/**
	 * 创建时间
	 */
	private Date txTime;
	/**
	 * 登记人
	 */
	private String txOp;

	/**
	 * 设置：显示编号
	 */
	public void setShowId(Integer showId) {
		this.showId = showId;
	}
	/**
	 * 获取：显示编号
	 */
	public Integer getShowId() {
		return showId;
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
	 * 设置：引用表编号
	 */
	public void setUseId(Integer useId) {
		this.useId = useId;
	}
	/**
	 * 获取：引用表编号
	 */
	public Integer getUseId() {
		return useId;
	}
	/**
	 * 设置：是否显示
	 */
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	/**
	 * 获取：是否显示
	 */
	public String getIsShow() {
		return isShow;
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
	 * 设置：别名
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * 获取：别名
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * 设置：描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * 获取：描述
	 */
	public String getDescribe() {
		return describe;
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
