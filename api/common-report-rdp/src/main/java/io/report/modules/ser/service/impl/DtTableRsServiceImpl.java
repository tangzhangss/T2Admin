package io.report.modules.ser.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;

import io.report.modules.ser.dao.DtTableRsDao;
import io.report.modules.ser.entity.DtTableRsEntity;
import io.report.modules.ser.service.DtTableRsService;


@Service("dtTableRsService")
public class DtTableRsServiceImpl extends ServiceImpl<DtTableRsDao, DtTableRsEntity> implements DtTableRsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DtTableRsEntity> page = this.selectPage(
                new Query<DtTableRsEntity>(params).getPage(),
                new EntityWrapper<DtTableRsEntity>()
        );

        return new PageUtils(page);
    }

}
