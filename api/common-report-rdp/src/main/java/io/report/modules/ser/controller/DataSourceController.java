package io.report.modules.ser.controller;

import java.util.Arrays;
import java.util.Map;

import io.report.common.db.bean.DataSourceBean;
import io.report.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.report.modules.rdp.util.Cache;
import io.report.modules.ser.entity.DataSourceEntity;
import io.report.modules.ser.service.DataSourceService;
import io.report.modules.ser.util.SerHandler;
import io.report.modules.sys.shiro.ShiroUser;
import io.report.common.db.util.DBUtil;
import io.report.common.utils.PageUtils;
import io.report.common.utils.R;

/**
 * 数据源表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-27 15:35:01
 */
@RestController
@RequestMapping("ser/datasource")
public class DataSourceController {
	@Autowired
	private DataSourceService dataSourceService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ser:datasource:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = dataSourceService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 获取数据集信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id) {
		DataSourceEntity dataSource = dataSourceService.selectById(id);

		return R.ok().put("dataSource", dataSource);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody DataSourceEntity dataSource) {
		dataSource.setTxOp(ShiroUser.getUserName());
		dataSource.setUpOp(ShiroUser.getUserName());
		dataSource.setTxTime(ShiroUser.getSysDate());
		dataSource.setUpTime(ShiroUser.getSysDate());
		dataSourceService.insert(dataSource);
		//保存给报表调用
		DataSourceBean dsb=dataSourceService.getDataSourceBeanByDataSourceName(dataSource.getName());
		Cache.dataSourceBeanMap.put(dataSource.getName(), dsb);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ser:datasource:update")
	public R update(@RequestBody DataSourceEntity dataSource) {
		ValidatorUtils.validateEntity(dataSource);
		dataSource.setUpOp(ShiroUser.getUserName());
		dataSource.setUpTime(ShiroUser.getSysDate());
		dataSourceService.updateAllColumnById(dataSource);// 全部更新
		if(DBUtil.dataSourceMap.get(dataSource.getName())!=null) {
			try {
				DBUtil.dataSourceMap.get(dataSource.getName()).close();
			}catch (Exception e) {
				return R.error().put("msg", "数据源关闭失败！");
			}
		}
		//保存给报表调用
		DataSourceBean dsb=dataSourceService.getDataSourceBeanByDataSourceName(dataSource.getName());
		Cache.dataSourceBeanMap.put(dataSource.getName(), dsb);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ser:datasource:delete")
	public R delete(@RequestBody Integer[] ids) {
		for (Integer id : Arrays.asList(ids)) {
			DataSourceEntity dataSource = dataSourceService.selectById(id);
			if (dataSource != null) {
				if (Cache.dataSourceBeanMap.get(dataSource.getName()) != null) {
					Cache.dataSourceBeanMap.remove(dataSource.getName());
				}
			}
		}
		dataSourceService.deleteBatchIds(Arrays.asList(ids));
		return R.ok();
	}

	/**
	 * 获取数据集中的表
	 */
	@RequestMapping("/conntest/{id}")
	public R connTest(@PathVariable("id") Integer id) {
		DataSourceEntity dataSource = dataSourceService.selectById(id);
		Map<String, String> dataMap = DBUtil.connTest(SerHandler.getDataSourceBean(dataSource));
		if (dataMap != null && dataMap.size() > 0) {
			if ("1".equals(dataMap.get("flag"))) {
				return R.ok("连接测试成功!");
			} else {
				return R.error(dataMap.get("msg"));
			}
		} else {
			return R.error("数据库连接失败");
		}
	}
}
