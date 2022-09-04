package io.report.modules.ser.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据集引用表关系表
 * 
 * @author jizh
 * @email jzh15084102133@126.com
 * @Time 2018-08-29 18:01:10
 */
@TableName("dt_table_rs")
public class DtTableRsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 关系编号
	 */
	@TableId
	private Integer rsId;
	/**
	 * 引用编号
	 */
	private Integer useId;
	/**
	 * 关系类型(union/join)
	 */
	private String rsType;
	/**
	 * 关系模式
	 */
	private String rsModel;
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
	 * 设置：关系编号
	 */
	public void setRsId(Integer rsId) {
		this.rsId = rsId;
	}
	/**
	 * 获取：关系编号
	 */
	public Integer getRsId() {
		return rsId;
	}
	/**
	 * 设置：引用编号
	 */
	public void setUseId(Integer useId) {
		this.useId = useId;
	}
	/**
	 * 获取：引用编号
	 */
	public Integer getUseId() {
		return useId;
	}
	/**
	 * 设置：关系类型(union/join)
	 */
	public void setRsType(String rsType) {
		this.rsType = rsType;
	}
	/**
	 * 获取：关系类型(union/join)
	 */
	public String getRsType() {
		return rsType;
	}
	/**
	 * 设置：关系模式
	 */
	public void setRsModel(String rsModel) {
		this.rsModel = rsModel;
	}
	/**
	 * 获取：关系模式
	 */
	public String getRsModel() {
		return rsModel;
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
