package io.report.modules.ser.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;

import io.report.modules.ser.dao.DtFilterDao;
import io.report.modules.ser.entity.DtFilterEntity;
import io.report.modules.ser.service.DtFilterService;


@Service("dtFilterService")
public class DtFilterServiceImpl extends ServiceImpl<DtFilterDao, DtFilterEntity> implements DtFilterService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DtFilterEntity> page = this.selectPage(
                new Query<DtFilterEntity>(params).getPage(),
                new EntityWrapper<DtFilterEntity>()
        );

        return new PageUtils(page);
    }

}
