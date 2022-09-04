package io.report.common.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.report.common.db.bean.DataSourceBean;
import io.report.modules.rdp.util.Cache;
import io.report.modules.ser.entity.DataSourceEntity;
import io.report.modules.ser.service.DataSourceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *         CommandLineRunner跟ApplicationRunner一样的功能
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
	protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DataSourceService dataSourceService;
    /**
     * 会在服务启动完成后立即执行
     */
    @Override
    public void run(String... args) throws Exception {
        init();
        logger.info("*********************************************项目启动完成************************************************");
    }

    public void init(){
        EntityWrapper<DataSourceEntity> wrapper=new EntityWrapper<DataSourceEntity>();
        List<DataSourceEntity> list=dataSourceService.selectList(wrapper);
        for(DataSourceEntity dsEntity:list){
            DataSourceBean dsb = new DataSourceBean();
            if (dsEntity != null) {
                dsb.setDataSourceName(dsEntity.getName());
                dsb.setModel(dsEntity.getModel());
                dsb.setType(dsEntity.getType());
                dsb.setDriver(dsEntity.getDriver());
                dsb.setDataBaseUrl(dsEntity.getAddr());
                dsb.setUserName(dsEntity.getUsr());
                dsb.setPassword(dsEntity.getPassword());
                dsb.setReadOnly("0".equals(dsEntity.getReadonly()) ? false : true);
                Cache.dataSourceBeanMap.put(dsEntity.getName(), dsb);
            }
        }
    }
}
