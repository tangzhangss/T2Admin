package io.report.modules.ser.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;

import io.report.modules.ser.dao.DtTableDao;
import io.report.modules.ser.entity.DtTableEntity;
import io.report.modules.ser.service.DtTableService;


@Service("dtTableService")
public class DtTableServiceImpl extends ServiceImpl<DtTableDao, DtTableEntity> implements DtTableService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DtTableEntity> page = this.selectPage(
                new Query<DtTableEntity>(params).getPage(),
                new EntityWrapper<DtTableEntity>()
        );

        return new PageUtils(page);
    }

}
