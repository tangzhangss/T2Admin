package com.tangzhangss.commonservice.dict;

import com.tangzhangss.commonutils.base.SysBaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DictService extends SysBaseService<DictEntity,DictDao> {
    @Override
    protected boolean isQueryAll() {
        return true;
    }

    @Override
    public Map<String, String> getCheckFields() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "type");
        return map;
    }
}
