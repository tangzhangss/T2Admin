package io.report.modules.ser.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.report.common.utils.PageUtils;
import io.report.modules.ser.entity.DataSetEntity;

/**
 * 数据集表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
public interface DataSetService extends IService<DataSetEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<DataSetEntity> getList(DataSetEntity dataSet);
}

