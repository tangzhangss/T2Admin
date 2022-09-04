package io.report.modules.material.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;

import io.report.modules.material.dao.MaterialDao;
import io.report.modules.material.entity.MaterialEntity;
import io.report.modules.material.service.MaterialService;

@Service("materialService")
public class MaterialServiceImpl extends ServiceImpl<MaterialDao, MaterialEntity> implements MaterialService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MaterialEntity> page = new Query<MaterialEntity>(params).getPage();
        EntityWrapper<MaterialEntity> ew =  new EntityWrapper<MaterialEntity>();
        if (params.containsKey("group")&&params.get("group")!=null&&!"".equals(params.get("group"))) {
            ew.where("material_group={0}", params.get("group"));
        } 
        ew.orderBy("upload_time", false);
        page.setSize(20);
        page = this.selectPage(page, ew);

        return new PageUtils(page);
    }

}
