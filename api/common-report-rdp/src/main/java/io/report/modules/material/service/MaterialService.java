package io.report.modules.material.service;

import com.baomidou.mybatisplus.service.IService;
import io.report.common.utils.PageUtils;
import io.report.modules.material.entity.MaterialEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 09:08:35
 */
public interface MaterialService extends IService<MaterialEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

