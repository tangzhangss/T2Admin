package io.report.modules.ser.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.report.common.db.bean.DataSourceBean;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;
import io.report.modules.rdp.util.Cache;
import io.report.modules.ser.dao.DataSourceDao;
import io.report.modules.ser.entity.DataSourceEntity;
import io.report.modules.ser.service.DataSourceService;

@Service("dataSourceService")
public class DataSourceServiceImpl extends ServiceImpl<DataSourceDao, DataSourceEntity> implements DataSourceService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<DataSourceEntity> page = this.selectPage(new Query<DataSourceEntity>(params).getPage(), new EntityWrapper<DataSourceEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<DataSourceEntity> queryAll(Map<String, Object> params) {
		List<DataSourceEntity> list = this.selectList(new EntityWrapper<DataSourceEntity>().where("name like '%" + params.get("name") + "%'"));
		return list;
	}
	
	@Override
	public List<DataSourceEntity> queryList(Map<String, Object> params) {
		List<DataSourceEntity> list = this.selectList(new EntityWrapper<DataSourceEntity>().where("name = '" + params.get("name") + "'"));
		return list;
	}

	@Override
	public DataSourceBean getDataSourceBeanByDataSourceName(String dataSourceName) {
		Wrapper<DataSourceEntity> dse = new EntityWrapper<DataSourceEntity>();
		dse.eq("name", dataSourceName);
		DataSourceEntity dsEntity = this.selectOne(dse);
		DataSourceBean dsb = new DataSourceBean();
		if (dsEntity != null) {
			dsb.setDataSourceName(dataSourceName);
			dsb.setModel(dsEntity.getModel());
			dsb.setType(dsEntity.getType());
			dsb.setDriver(dsEntity.getDriver());
			dsb.setDataBaseUrl(dsEntity.getAddr());
			dsb.setUserName(dsEntity.getUsr());
			dsb.setPassword(dsEntity.getPassword());
			dsb.setReadOnly("0".equals(dsEntity.getReadonly()) ? false : true);
			Cache.dataSourceBeanMap.put(dataSourceName, dsb);
		}
		return dsb;
	}

}
