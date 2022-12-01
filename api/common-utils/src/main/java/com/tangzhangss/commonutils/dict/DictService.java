package com.tangzhangss.commonutils.dict;

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
    protected boolean bSureDelete() {
        return true;
    }

    @Override
    public Map<String, String> getCheckFields() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "type");
        return map;
    }

    public String getDictDataByType(String type) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("type", type);
            //肯定有一个，如果没有报错就返回null
            return get(null, map).getContent().get(0).getData();
        }catch (Exception e) {
            return null;
        }

    }
}
