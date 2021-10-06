package com.tangzhangss.commonflowable.listener;

import com.tangzhangss.commonutils.utils.BaseUtil;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.engine.delegate.event.BaseEntityEventListener;

/**
 * 实体的监听器
 */
public class GlobalEntityEventListener extends BaseEntityEventListener {
    @Override
    protected void onCreate(FlowableEvent event) {
        System.out.println(
                BaseUtil.string("BaseEntityEventListener[create]=>",event.getClass().toString())
        );
    }

    @Override
    protected void onUpdate(FlowableEvent event) {
        System.out.println(
                BaseUtil.string("BaseEntityEventListener[update]=>",event.getClass().toString())
        );
    }

    @Override
    protected void onInitialized(FlowableEvent event) {
        System.out.println(
                BaseUtil.string("BaseEntityEventListener[initialized]=>",event.getClass().toString())
        );
    }

    @Override
    protected void onDelete(FlowableEvent event) {
        System.out.println(
                BaseUtil.string("BaseEntityEventListener[delete]=>",event.getClass().toString())
        );
    }
}
