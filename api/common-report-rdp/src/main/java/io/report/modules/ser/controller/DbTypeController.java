package io.report.modules.ser.controller;

import java.util.Arrays;
import java.util.Map;

import io.report.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.report.modules.ser.entity.DbTypeEntity;
import io.report.modules.ser.service.DbTypeService;
import io.report.common.utils.PageUtils;
import io.report.common.utils.R;



/**
 * 数据库支持类型
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("ser/dbtype")
public class DbTypeController {
    @Autowired
    private DbTypeService dbTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ser:dbtype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dbTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ser:dbtype:info")
    public R info(@PathVariable("id") Integer id){
        DbTypeEntity dbType = dbTypeService.selectById(id);

        return R.ok().put("dbType", dbType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ser:dbtype:save")
    public R save(@RequestBody DbTypeEntity dbType){
        dbTypeService.insert(dbType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ser:dbtype:update")
    public R update(@RequestBody DbTypeEntity dbType){
        ValidatorUtils.validateEntity(dbType);
        dbTypeService.updateAllColumnById(dbType);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ser:dbtype:delete")
    public R delete(@RequestBody Integer[] ids){
        dbTypeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
