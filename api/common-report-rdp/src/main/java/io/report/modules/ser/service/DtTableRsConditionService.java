package io.report.modules.ser.service;

import com.baomidou.mybatisplus.service.IService;
import io.report.common.utils.PageUtils;
import io.report.modules.ser.entity.DtTableRsConditionEntity;

import java.util.Map;

/**
 * 数据集表关系表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:09
 */
public interface DtTableRsConditionService extends IService<DtTableRsConditionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

