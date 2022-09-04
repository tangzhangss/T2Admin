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

import io.report.modules.ser.entity.DtFilterEntity;
import io.report.modules.ser.service.DtFilterService;
import io.report.modules.sys.shiro.ShiroUser;
import io.report.common.utils.PageUtils;
import io.report.common.utils.R;



/**
 * 数据集过滤
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("ser/dtfilter")
public class DtFilterController {
    @Autowired
    private DtFilterService dtFilterService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ser:dtfilter:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dtFilterService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{filterId}")
    @RequiresPermissions("ser:dtfilter:info")
    public R info(@PathVariable("filterId") Integer filterId){
        DtFilterEntity dtFilter = dtFilterService.selectById(filterId);

        return R.ok().put("dtFilter", dtFilter);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ser:dtfilter:save")
    public R save(@RequestBody DtFilterEntity dtFilter){
    	dtFilter.setTxOp(ShiroUser.getUserName());
    	dtFilter.setUpOp(ShiroUser.getUserName());
    	dtFilter.setTxTime(ShiroUser.getSysDate());
    	dtFilter.setUpTime(ShiroUser.getSysDate());
        dtFilterService.insert(dtFilter);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ser:dtfilter:update")
    public R update(@RequestBody DtFilterEntity dtFilter){
        ValidatorUtils.validateEntity(dtFilter);
    	dtFilter.setUpOp(ShiroUser.getUserName());
    	dtFilter.setUpTime(ShiroUser.getSysDate());
        dtFilterService.updateAllColumnById(dtFilter);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ser:dtfilter:delete")
    public R delete(@RequestBody Integer[] filterIds){
        dtFilterService.deleteBatchIds(Arrays.asList(filterIds));

        return R.ok();
    }

}
