package io.report.modules.ser.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.t2admin.SysContext;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据源表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-27 15:35:01
 */
@TableName("data_source")
public class DataSourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 租户ID
	 */
	private String clientId= SysContext.getClientId();


	/**
	 * 数据源编号
	 */
	@TableId
	private Integer id;
	/**
	 * 数据源名称
	 */
	private String name;
	/**
	 * 数据源大类
	 */
	private String model;
	/**
	 * 数据源类型
	 */
	private String type;
	/**
	 * 数据源版本
	 */
	@TableField(el = "version, jdbcType=VARCHAR")
	private String version;
	/**
	 * 驱动
	 */
	private String driver;
	/**
	 * 地址
	 */
	private String addr;
	/**
	 * 用户
	 */
	private String usr;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 数据类型对应
	 */
	@TableField(el = "icon, jdbcType=VARCHAR")
	private String icon;
	/**
	 *
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
	 * 只读状态
	 */
	private String readonly;

	/**
	 * 设置：数据源编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：数据源编号
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：数据源名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：数据源名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：数据源大类
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * 获取：数据源大类
	 */
	public String getModel() {
		return model;
	}
	/**
	 * 设置：数据源类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：数据源类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：数据源版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：数据源版本
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置：驱动
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * 获取：驱动
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * 设置：地址
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * 获取：地址
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * 设置：用户
	 */
	public void setUsr(String usr) {
		this.usr = usr;
	}
	/**
	 * 获取：用户
	 */
	public String getUsr() {
		return usr;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：
	 */
	public void setSts(String sts) {
		this.sts = sts;
	}
	/**
	 * 获取：
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
}
