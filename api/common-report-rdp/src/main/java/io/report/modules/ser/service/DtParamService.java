package io.report.modules.ser.service;

import com.baomidou.mybatisplus.service.IService;
import io.report.common.utils.PageUtils;
import io.report.modules.ser.entity.DtParamEntity;

import java.util.Map;

/**
 * 数据集参数表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
public interface DtParamService extends IService<DtParamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

