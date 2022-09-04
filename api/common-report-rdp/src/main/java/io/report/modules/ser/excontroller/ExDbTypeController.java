package io.report.modules.ser.excontroller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.report.common.utils.R;
import io.report.modules.ser.entity.DbTypeEntity;
import io.report.modules.ser.service.DbTypeService;



/**
 * 数据库支持类型
 *
 * @author jizh
 * @email jzh15084102133@126.com
 * @date 2018-08-29 18:01:10
 */
@RestController
@RequestMapping("/ex/ser/dbtype")
public class ExDbTypeController {
    @Autowired
    private DbTypeService dbTypeService;
    @Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
    	DbTypeEntity dbTypeEntity = new DbTypeEntity();
    	dbTypeEntity.setSts("1");
        List<DbTypeEntity> list = dbTypeService.getList(dbTypeEntity);

        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        DbTypeEntity dbType = dbTypeService.selectById(id);

        return R.ok().put("dbType", dbType);
    }

}
