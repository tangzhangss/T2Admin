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

import io.report.modules.ser.entity.DtTableRsEntity;
import io.report.modules.ser.service.DtTableRsService;
import io.report.modules.sys.shiro.ShiroUser;
import io.report.common.utils.PageUtils;
import io.report.common.utils.R;



/**
 * 数据集引用表关系表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("ser/dttablers")
public class DtTableRsController {
    @Autowired
    private DtTableRsService dtTableRsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ser:dttablers:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dtTableRsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{rsId}")
    @RequiresPermissions("ser:dttablers:info")
    public R info(@PathVariable("rsId") Integer rsId){
        DtTableRsEntity dtTableRs = dtTableRsService.selectById(rsId);

        return R.ok().put("dtTableRs", dtTableRs);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ser:dttablers:save")
    public R save(@RequestBody DtTableRsEntity dtTableRs){
    	dtTableRs.setTxOp(ShiroUser.getUserName());
    	dtTableRs.setUpOp(ShiroUser.getUserName());
    	dtTableRs.setTxTime(ShiroUser.getSysDate());
    	dtTableRs.setUpTime(ShiroUser.getSysDate());
        dtTableRsService.insert(dtTableRs);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ser:dttablers:update")
    public R update(@RequestBody DtTableRsEntity dtTableRs){
        ValidatorUtils.validateEntity(dtTableRs);
    	dtTableRs.setUpOp(ShiroUser.getUserName());
    	dtTableRs.setUpTime(ShiroUser.getSysDate());
        dtTableRsService.updateAllColumnById(dtTableRs);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ser:dttablers:delete")
    public R delete(@RequestBody Integer[] rsIds){
        dtTableRsService.deleteBatchIds(Arrays.asList(rsIds));

        return R.ok();
    }

}
