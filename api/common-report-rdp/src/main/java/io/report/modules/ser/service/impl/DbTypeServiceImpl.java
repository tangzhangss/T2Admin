package io.report.modules.ser.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;
import io.report.modules.ser.dao.DbTypeDao;
import io.report.modules.ser.entity.DbTypeEntity;
import io.report.modules.ser.service.DbTypeService;


@Service("dbTypeService")
public class DbTypeServiceImpl extends ServiceImpl<DbTypeDao, DbTypeEntity> implements DbTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DbTypeEntity> page = this.selectPage(
                new Query<DbTypeEntity>(params).getPage(),
                new EntityWrapper<DbTypeEntity>()
        );

        return new PageUtils(page);
    }
    
    @Override
    public 	List<DbTypeEntity> getList(DbTypeEntity dbTypeEntity){
    	 List<DbTypeEntity> list = this.selectList(new EntityWrapper<DbTypeEntity>().where("sts={0}",dbTypeEntity.getSts()));
    	 return list;
    }

}
