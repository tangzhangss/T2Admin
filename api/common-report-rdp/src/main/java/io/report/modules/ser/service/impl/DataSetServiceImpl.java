package io.report.modules.ser.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;

import io.report.modules.ser.dao.DataSetDao;
import io.report.modules.ser.entity.DataSetEntity;
import io.report.modules.ser.service.DataSetService;


@Service("dataSetService")
public class DataSetServiceImpl extends ServiceImpl<DataSetDao, DataSetEntity> implements DataSetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DataSetEntity> page = this.selectPage(
                new Query<DataSetEntity>(params).getPage(),
                new EntityWrapper<DataSetEntity>()
        );

        return new PageUtils(page);
    }
    
    @Override
    public List<DataSetEntity> getList(DataSetEntity dataSet){
    	
    	List<DataSetEntity> list = this.selectList(new EntityWrapper<DataSetEntity>());
    	return list;
    }
}
