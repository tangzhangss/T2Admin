package io.report.modules.ser.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.report.common.utils.PageUtils;
import io.report.modules.ser.entity.DbTypeEntity;

/**
 * 数据库支持类型
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
public interface DbTypeService extends IService<DbTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<DbTypeEntity> getList(DbTypeEntity dbTypeEntity);
}

