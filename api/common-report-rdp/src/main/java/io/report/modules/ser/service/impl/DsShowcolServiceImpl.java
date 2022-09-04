package io.report.modules.ser.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;

import io.report.modules.ser.dao.DsShowcolDao;
import io.report.modules.ser.entity.DsShowcolEntity;
import io.report.modules.ser.service.DsShowcolService;


@Service("dsShowcolService")
public class DsShowcolServiceImpl extends ServiceImpl<DsShowcolDao, DsShowcolEntity> implements DsShowcolService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DsShowcolEntity> page = this.selectPage(
                new Query<DsShowcolEntity>(params).getPage(),
                new EntityWrapper<DsShowcolEntity>()
        );

        return new PageUtils(page);
    }

}
