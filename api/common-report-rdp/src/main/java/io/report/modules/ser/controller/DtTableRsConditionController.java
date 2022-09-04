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

import io.report.modules.ser.entity.DtTableRsConditionEntity;
import io.report.modules.ser.service.DtTableRsConditionService;
import io.report.modules.sys.shiro.ShiroUser;
import io.report.common.utils.PageUtils;
import io.report.common.utils.R;



/**
 * 数据集表关系表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:09
 */
@RestController
@RequestMapping("ser/dttablerscondition")
public class DtTableRsConditionController {
    @Autowired
    private DtTableRsConditionService dtTableRsConditionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ser:dttablerscondition:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dtTableRsConditionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cdId}")
    @RequiresPermissions("ser:dttablerscondition:info")
    public R info(@PathVariable("cdId") Integer cdId){
        DtTableRsConditionEntity dtTableRsCondition = dtTableRsConditionService.selectById(cdId);

        return R.ok().put("dtTableRsCondition", dtTableRsCondition);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ser:dttablerscondition:save")
    public R save(@RequestBody DtTableRsConditionEntity dtTableRsCondition){
    	dtTableRsCondition.setTxOp(ShiroUser.getUserName());
    	dtTableRsCondition.setTxTime(ShiroUser.getSysDate());
        dtTableRsConditionService.insert(dtTableRsCondition);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ser:dttablerscondition:update")
    public R update(@RequestBody DtTableRsConditionEntity dtTableRsCondition){
        ValidatorUtils.validateEntity(dtTableRsCondition);
        dtTableRsCondition.setTxOp(ShiroUser.getUserName());
    	dtTableRsCondition.setTxTime(ShiroUser.getSysDate());
        dtTableRsConditionService.updateAllColumnById(dtTableRsCondition);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ser:dttablerscondition:delete")
    public R delete(@RequestBody Integer[] cdIds){
        dtTableRsConditionService.deleteBatchIds(Arrays.asList(cdIds));

        return R.ok();
    }

}
