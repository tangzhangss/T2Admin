package io.report.modules.ser.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;

import io.report.modules.ser.dao.DtTableRsConditionDao;
import io.report.modules.ser.entity.DtTableRsConditionEntity;
import io.report.modules.ser.service.DtTableRsConditionService;


@Service("dtTableRsConditionService")
public class DtTableRsConditionServiceImpl extends ServiceImpl<DtTableRsConditionDao, DtTableRsConditionEntity> implements DtTableRsConditionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DtTableRsConditionEntity> page = this.selectPage(
                new Query<DtTableRsConditionEntity>(params).getPage(),
                new EntityWrapper<DtTableRsConditionEntity>()
        );

        return new PageUtils(page);
    }

}
