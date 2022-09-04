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

import io.report.modules.ser.entity.DsShowcolEntity;
import io.report.modules.ser.service.DsShowcolService;
import io.report.modules.sys.shiro.ShiroUser;
import io.report.common.utils.PageUtils;
import io.report.common.utils.R;



/**
 * 数据集表显示字段表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("ser/dsshowcol")
public class DsShowcolController {
    @Autowired
    private DsShowcolService dsShowcolService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ser:dsshowcol:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dsShowcolService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{showId}")
    @RequiresPermissions("ser:dsshowcol:info")
    public R info(@PathVariable("showId") Integer showId){
        DsShowcolEntity dsShowcol = dsShowcolService.selectById(showId);

        return R.ok().put("dsShowcol", dsShowcol);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ser:dsshowcol:save")
    public R save(@RequestBody DsShowcolEntity dsShowcol){
    	dsShowcol.setTxOp(ShiroUser.getUserName());
    	dsShowcol.setTxTime(ShiroUser.getSysDate());
        dsShowcolService.insert(dsShowcol);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ser:dsshowcol:update")
    public R update(@RequestBody DsShowcolEntity dsShowcol){
        ValidatorUtils.validateEntity(dsShowcol);
        dsShowcol.setTxOp(ShiroUser.getUserName());
    	dsShowcol.setTxTime(ShiroUser.getSysDate());
        dsShowcolService.updateAllColumnById(dsShowcol);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ser:dsshowcol:delete")
    public R delete(@RequestBody Integer[] showIds){
        dsShowcolService.deleteBatchIds(Arrays.asList(showIds));

        return R.ok();
    }

}
