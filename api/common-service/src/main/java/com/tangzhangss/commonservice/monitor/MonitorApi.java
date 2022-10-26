package com.tangzhangss.commonservice.monitor;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tangzhangss.commonservice.dict.DictEntity;
import com.tangzhangss.commonservice.dict.DictService;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.server.Server;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.runtime.OSInfo;
import com.tangzhangss.commonutils.utils.runtime.RuntimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/monitor")
public class MonitorApi {
    @Autowired
    DictService dictService;

    @GetMapping("/system")
    public Result system() throws Exception {
        Server server = new Server();
        server.copyTo();

        return Result.ok().data(server);
    }


    @GetMapping("/service")
    public Result service() throws Exception {
        //获取系统服务
        DictEntity dictEntityList = dictService.getOneWithMapString("type@EQ=system-service");
        JSONArray serviceStatusArr = new JSONArray();
        if(dictEntityList!=null){
            serviceStatusArr = JSONUtil.parseArray(dictEntityList.getData());
            for (Object service : serviceStatusArr) {
                String cmd = "ps aux|grep -v 'grep'|grep ${service}";
                if(OSInfo.isWindows())cmd="tasklist|findstr ${service}";

                JSONObject obj = (JSONObject) service;
                cmd = cmd.replace("${service}", obj.getStr("label"));
                String s = RuntimeUtil.executeRuntimeCommand(cmd);
                if (StringUtils.isEmpty(s)) {
                    obj.set("status",false);
                }else{
                    obj.set("status",true);
                    Float ramUseRate = 0f;//内存使用率
                    Long ramUse = 0l;//使用内存
                    Float cpuUseRate = 0f;//cpu使用率
                    if(OSInfo.isLinux()){
                        JSONArray array = RuntimeUtil.parsePsAuxCmdRes(s);
                        for (int i1 = 0; i1 < array.size(); i1++) {
                            JSONObject obj1 = array.getJSONObject(i1);
                            ramUseRate += obj1.getFloat("%mem");
                            ramUse+=obj1.getLong("rss");
                            cpuUseRate+=obj1.getFloat("%cpu");
                        }
                    }else if(OSInfo.isWindows()){
                        JSONArray array = RuntimeUtil.parseTasklistCmdRes(s);
                        for (int i2 = 0; i2 < array.size(); i2++) {
                            JSONObject obj2 = array.getJSONObject(i2);
                            ramUse+=Long.parseLong(obj2.getStr("ramUse").replaceAll(",",""));
                        }
                    }
                    obj.set("ramUseRate",ramUseRate + "%");
                    obj.set("ramUse",(BaseUtil.round(ramUse/1024,2)+" MB"));
                    obj.set("cpuUseRate",cpuUseRate + "%");
                }
            }
        }

        return Result.ok().data(serviceStatusArr);
    }
}
