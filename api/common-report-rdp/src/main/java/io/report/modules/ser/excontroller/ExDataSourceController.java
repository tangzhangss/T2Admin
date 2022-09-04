package io.report.modules.ser.excontroller;

import com.t2admin.SysContext;
import io.report.common.db.handler.DbInfoUtil;
import io.report.common.db.util.DBUtil;
import io.report.common.utils.R;
import io.report.common.validator.ValidatorUtils;
import io.report.modules.rdp.util.Cache;
import io.report.modules.ser.entity.DataSourceEntity;
import io.report.modules.ser.service.DataSourceService;
import io.report.modules.ser.util.SerHandler;
import io.report.modules.sys.shiro.ShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-27 16:10:01
 */
@RestController
@RequestMapping("/ex/ser/datasource")
public class ExDataSourceController {
	@Autowired
	private DataSourceService dataSourceService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表
	 */
	@RequestMapping("/getList")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("client_id", SysContext.getClientId());
		List<DataSourceEntity> dataSourceList = dataSourceService.selectByMap(params);
		return R.ok().put("list", dataSourceList);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/getInfo/{id}")
	public R info(@PathVariable("id") Integer id) {
		DataSourceEntity dataSource = dataSourceService.selectById(id);
		return R.ok().put("dataSource", dataSource);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody DataSourceEntity dataSource) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", dataSource.getName());
		params.put("clientId", SysContext.getClientId());
		List<DataSourceEntity> list = dataSourceService.queryList(params);
		if (list != null && list.size() > 0) {
			return R.error("已存在相同名称的数据源！");
		} else {
			dataSource.setTxOp(ShiroUser.getUserName());
			dataSource.setUpOp(ShiroUser.getUserName());
			dataSource.setTxTime(ShiroUser.getSysDate());
			dataSource.setUpTime(ShiroUser.getSysDate());
			dataSourceService.insert(dataSource);
			return R.ok().put("dataSource", dataSource);
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody DataSourceEntity dataSource) {
		ValidatorUtils.validateEntity(dataSource);
		dataSource.setUpOp(ShiroUser.getUserName());
		dataSource.setUpTime(ShiroUser.getSysDate());
		dataSourceService.updateAllColumnById(dataSource);// 全部更新
		if (Cache.dataSourceBeanMap.get(dataSource.getName()) != null) {
			Cache.dataSourceBeanMap.remove(dataSource.getName());
		}
		return R.ok().put("dataSource", dataSource);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
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
	 * 测试数据源链接
	 */
	@RequestMapping("/conntest")
	public R connTest(@RequestBody DataSourceEntity dataSource) {
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

	/**
	 * 获取数据集中的表
	 */
	@RequestMapping("/getTableList/{id}")
	public R saveTableList(@PathVariable("id") Integer id) {
		DataSourceEntity dataSource = dataSourceService.selectById(id);
		if (dataSource != null) {
			DbInfoUtil d = DBUtil.getDbInfoUtil(SerHandler.getDataSourceBean(dataSource));
			return R.ok().put("tableList", d.getTableList());
		} else {
			return R.error("未找到数据集");
		}
	}
}
