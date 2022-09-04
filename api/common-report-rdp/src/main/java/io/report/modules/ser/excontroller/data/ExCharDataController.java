package io.report.modules.ser.excontroller.data;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.report.common.db.util.StringUtil;
import io.report.common.utils.R;
import io.report.modules.ser.entity.DataSetEntity;
import io.report.modules.ser.excontroller.data.handler.CharDataHander;
import io.report.modules.ser.service.DataSetService;
/**
 * 图表数据获取
 * @author JIZH
 */
@RestController
@RequestMapping("/ex/chardata/getData")
public class ExCharDataController {
	@Autowired
    private DataSetService dataSetService;
	
	/**
	 * @param params
	 * @return
	 */
	@RequestMapping("/type1")
	public R getData(@RequestParam Map<String, Object> params) {
		String dtId = (String)params.get("dtId");
    	String columns[] = StringUtil.getObjectToArray((String)params.get("columns"),",");
    	String groups[] = StringUtil.getObjectToArray((String)params.get("groups"),",");
    	String series[] = StringUtil.getObjectToArray((String)params.get("series"),",");
		DataSetEntity dataSet = dataSetService.selectById(dtId);
		if(dataSet!=null) {
			try {
				Object obj = CharDataHander.getChardata(dataSet, columns, groups, series, new String[0]);
				return R.ok().put("data", obj);
			}catch (Exception e) {
				e.printStackTrace();
				return R.error("图表数据获取失败！");
			}
		}else {
			return R.error("数据集不存在！");
		}
	}
}
