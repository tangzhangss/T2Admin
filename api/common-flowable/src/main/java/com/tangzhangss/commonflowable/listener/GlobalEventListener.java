package com.tangzhangss.commonflowable.listener;

import com.tangzhangss.commonutils.utils.BaseUtil;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.delegate.event.BaseEntityEventListener;

public class GlobalEventListener implements FlowableEventListener {
    @Override
    public void onEvent(FlowableEvent event) {
        System.out.println(
                BaseUtil.string("FlowableEventListener[",event.getType().toString(),"]=>",event.getClass().toString())
        );
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
