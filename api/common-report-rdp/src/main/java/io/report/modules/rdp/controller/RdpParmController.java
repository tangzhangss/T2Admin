package io.report.modules.rdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.report.common.utils.R;
import io.report.modules.sys.entity.SysAreaEntity;
import io.report.modules.sys.entity.SysWayEntity;
import io.report.modules.sys.service.SysAreaService;
import io.report.modules.sys.service.SysWayService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 报表展现相关--不拦截
 */
@RestController
@RequestMapping("/rdpparm")
public class RdpParmController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	HttpServletRequest rq;
	@Autowired
	HttpServletResponse rp;
	@Autowired
	SysWayService sysWayService;
	@Autowired
	SysAreaService sysAreaService;
	
	public final static Map<String,Object> map = new HashMap<String,Object>();
	@RequestMapping("/parm/{parmType}")
	public R parm(@PathVariable("parmType") String parmType) {
		System.out.println(parmType);
		JSONArray data = new JSONArray();
		if("SYS_AREA".equals(parmType)) {//行政区划
			if(map.containsKey(parmType)) {
				data = (JSONArray)map.get(parmType);
			}else {
				List<SysAreaEntity> list = sysAreaService.getList(null);
				for (SysAreaEntity sae: list) {
					JSONObject obj = JSONObject.fromObject(sae);
					obj.put("name", sae.getAreaName());
					obj.put("id", sae.getAreaNo());
					obj.put("pId", sae.getUplev());
					data.add(obj);
				}
				map.put(parmType, data);
			}
		}else if("SYS_WAY".equals(parmType)) {//行业分类
			if(map.containsKey(parmType)) {
				data = (JSONArray)map.get(parmType);
			}else {
				List<SysWayEntity> list = sysWayService.getList(null);
				for (SysWayEntity sae: list) {
					JSONObject obj = JSONObject.fromObject(sae);
					obj.put("name", sae.getWayName());
					obj.put("id", sae.getWayNo());
					obj.put("pId", sae.getUplev());
					data.add(obj);
				}
				data = JSONArray.fromObject(data);
				map.put(parmType, data);
			}
		}
		return R.ok().put("data", data);
	}
}
