package io.report.modules.ser.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;

import io.report.modules.ser.dao.DtParamDao;
import io.report.modules.ser.entity.DtParamEntity;
import io.report.modules.ser.service.DtParamService;


@Service("dtParamService")
public class DtParamServiceImpl extends ServiceImpl<DtParamDao, DtParamEntity> implements DtParamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DtParamEntity> page = this.selectPage(
                new Query<DtParamEntity>(params).getPage(),
                new EntityWrapper<DtParamEntity>()
        );

        return new PageUtils(page);
    }

}
