package io.report.modules.ser.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.report.common.db.bean.DataSourceBean;
import io.report.common.utils.PageUtils;
import io.report.modules.ser.entity.DataSourceEntity;

/**
 * 数据源表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-27 15:35:01
 */
public interface DataSourceService extends IService<DataSourceEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<DataSourceEntity> queryAll(Map<String, Object> params);
    
    List<DataSourceEntity> queryList(Map<String, Object> params);
    
    DataSourceBean getDataSourceBeanByDataSourceName(String dataSourceName);
}

