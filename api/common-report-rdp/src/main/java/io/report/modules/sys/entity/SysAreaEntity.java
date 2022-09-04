/**
 * Copyright 2018 RDP http://product.mftcc.cn/rdp/
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.report.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 行政区划信息
 */
@TableName("sys_area")
public class SysAreaEntity {
	private String areaNo;
	private String areaName;
	private String lev;
	private String uplev;
	private String areaSts;
	private String ifLeaf;
	
	public String getAreaNo() {
		return areaNo;
	}
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
	public String getUplev() {
		return uplev;
	}
	public void setUplev(String uplev) {
		this.uplev = uplev;
	}
	public String getAreaSts() {
		return areaSts;
	}
	public void setAreaSts(String areaSts) {
		this.areaSts = areaSts;
	}
	public String getIfLeaf() {
		return ifLeaf;
	}
	public void setIfLeaf(String ifLeaf) {
		this.ifLeaf = ifLeaf;
	}
}
