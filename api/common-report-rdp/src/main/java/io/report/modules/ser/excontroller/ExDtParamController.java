package io.report.modules.ser.excontroller;

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
import io.report.modules.ser.entity.DtParamEntity;
import io.report.modules.ser.service.DtParamService;
import io.report.modules.sys.shiro.ShiroUser;

/**
 * 数据集参数表
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("/ex/ser/dtparam")
public class ExDtParamController {
    @Autowired
    private DtParamService dtParamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dtParamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{paramId}")
    public R info(@PathVariable("paramId") Integer paramId){
        DtParamEntity dtParam = dtParamService.selectById(paramId);

        return R.ok().put("dtParam", dtParam);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DtParamEntity dtParam){
    	dtParam.setTxOp(ShiroUser.getUserName());
    	dtParam.setUpOp(ShiroUser.getUserName());
    	dtParam.setTxTime(ShiroUser.getSysDate());
    	dtParam.setUpTime(ShiroUser.getSysDate());
        dtParamService.insert(dtParam);

        return R.ok().put("dtParam", dtParam);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DtParamEntity dtParam){
        ValidatorUtils.validateEntity(dtParam);
    	dtParam.setUpOp(ShiroUser.getUserName());
    	dtParam.setUpTime(ShiroUser.getSysDate());
        dtParamService.updateAllColumnById(dtParam);//全部更新
        
        return R.ok().put("dtParam", dtParam);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] paramIds){
        dtParamService.deleteBatchIds(Arrays.asList(paramIds));

        return R.ok();
    }

}
