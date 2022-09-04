package io.report.modules.sys.shiro;

import java.util.Date;

import org.apache.shiro.SecurityUtils;

import io.report.modules.sys.entity.SysUserEntity;
import net.sf.json.JSONObject;

/**
 * Shiro 当前用户信息
 * @author JIZH
 *
 */
public class ShiroUser{
	/**
	 * 获取当前用户
	 * @return
	 */
	public static String getUserName() {
		 SysUserEntity su = (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
	    // System.out.println(JSONObject.fromObject(su));
	     if(su!=null) {
	    	 return su.getUsername();
	     }
	     return null;
	}
	/**
	 * 获取系统日期
	 * @return
	 */
	public static Date getSysDate() {
	     return new Date();
	}
}
