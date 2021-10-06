package com.tangzhangss.commonflowable.listener;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.impl.el.FixedValue;

/**
 * 流程监听器
 *
 * 开始和结束
 */
@Data
public class TestExecutionListener implements ExecutionListener {
    private FixedValue A;//对应流程字段flowable:field name="A"
    @Override
    public void notify(DelegateExecution execution) {
        System.out.println(JSON.toJSONString(execution));
        System.out.println("调用了执行监听器....");
        execution.getExecutions();
        System.out.println(execution.getExecutions().toString());
    }
}
