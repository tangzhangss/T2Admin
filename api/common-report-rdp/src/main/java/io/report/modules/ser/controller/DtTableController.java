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

import io.report.modules.ser.entity.DtTableEntity;
import io.report.modules.ser.service.DtTableService;
import io.report.modules.sys.shiro.ShiroUser;
import io.report.common.utils.PageUtils;
import io.report.common.utils.R;



/**
 * 据集使用表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("ser/dttable")
public class DtTableController {
    @Autowired
    private DtTableService dtTableService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ser:dttable:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dtTableService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{useId}")
    @RequiresPermissions("ser:dttable:info")
    public R info(@PathVariable("useId") Integer useId){
        DtTableEntity dtTable = dtTableService.selectById(useId);

        return R.ok().put("dtTable", dtTable);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ser:dttable:save")
    public R save(@RequestBody DtTableEntity dtTable){
    	dtTable.setTxOp(ShiroUser.getUserName());
    	dtTable.setUpOp(ShiroUser.getUserName());
    	dtTable.setTxTime(ShiroUser.getSysDate());
    	dtTable.setUpTime(ShiroUser.getSysDate());
        dtTableService.insert(dtTable);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ser:dttable:update")
    public R update(@RequestBody DtTableEntity dtTable){
        ValidatorUtils.validateEntity(dtTable);
    	dtTable.setUpOp(ShiroUser.getUserName());
    	dtTable.setUpTime(ShiroUser.getSysDate());
        dtTableService.updateAllColumnById(dtTable);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ser:dttable:delete")
    public R delete(@RequestBody Integer[] useIds){
        dtTableService.deleteBatchIds(Arrays.asList(useIds));

        return R.ok();
    }

}
