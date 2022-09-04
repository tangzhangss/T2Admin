package io.report.modules.ser.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.report.common.utils.PageUtils;
import io.report.common.utils.R;
import io.report.common.validator.ValidatorUtils;
import io.report.modules.ser.entity.DataSetEntity;
import io.report.modules.ser.service.DataSetService;
import io.report.modules.sys.shiro.ShiroUser;



/**
 * 数据集表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("ser/dataset")
public class DataSetController {
    @Autowired
    private DataSetService dataSetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ser:dataset:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataSetService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dtId}")
    @RequiresPermissions("ser:dataset:info")
    public R info(@PathVariable("dtId") Integer dtId){
        DataSetEntity dataSet = dataSetService.selectById(dtId);
        return R.ok().put("dataSet", dataSet);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ser:dataset:save")
    public R save(@RequestBody DataSetEntity dataSet){
    	dataSet.setTxOp(ShiroUser.getUserName());
    	dataSet.setUpOp(ShiroUser.getUserName());
    	dataSet.setTxTime(ShiroUser.getSysDate());
    	dataSet.setUpTime(ShiroUser.getSysDate());
        dataSetService.insert(dataSet);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ser:dataset:update")
    public R update(@RequestBody DataSetEntity dataSet){
        ValidatorUtils.validateEntity(dataSet);
        dataSet.setUpOp(ShiroUser.getUserName());
        dataSet.setUpTime(ShiroUser.getSysDate());
        dataSetService.updateAllColumnById(dataSet);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ser:dataset:delete")
    public R delete(@RequestBody Integer[] dtIds){
        dataSetService.deleteBatchIds(Arrays.asList(dtIds));
        return R.ok();
    }

}
