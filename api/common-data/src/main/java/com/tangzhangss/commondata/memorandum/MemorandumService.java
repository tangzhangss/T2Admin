package com.tangzhangss.commondata.memorandum;

import com.tangzhangss.commonutils.base.SysBaseService;
import org.springframework.stereotype.Service;

@Service
public class MemorandumService extends SysBaseService<MemorandumEntity, MemorandumDao> {
    @Override
    protected boolean isQueryAll() {
        return true;
    }
}
