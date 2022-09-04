package io.report.modules.ser.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 数据库支持类型
 * 
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@TableName("db_type")
public class DbTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 类型编号
	 */
	@TableId
	private Integer id;
	/**
	 * 类型名称
	 */
	private String name;
	/**
	 * 数据库大类
	 */
	private String model;
	/**
	 * 支持类型
	 */
	private String type;
	/**
	 * 数据库驱动
	 */
	private String driver;
	/**
	 * 数据库地址例子
	 */
	private String addrDemo;
	/**
	 * 状态
	 */
	private String sts;
	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 设置：类型编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：类型编号
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：类型名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：类型名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：数据库大类
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * 获取：数据库大类
	 */
	public String getModel() {
		return model;
	}
	/**
	 * 设置：支持类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：支持类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：数据库驱动
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * 获取：数据库驱动
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * 设置：数据库地址例子
	 */
	public void setAddrDemo(String addrDemo) {
		this.addrDemo = addrDemo;
	}
	/**
	 * 获取：数据库地址例子
	 */
	public String getAddrDemo() {
		return addrDemo;
	}
	/**
	 * 设置：状态
	 */
	public void setSts(String sts) {
		this.sts = sts;
	}
	/**
	 * 获取：状态
	 */
	public String getSts() {
		return sts;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
