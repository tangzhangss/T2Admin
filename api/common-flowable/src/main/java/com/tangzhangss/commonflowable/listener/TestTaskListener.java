package com.tangzhangss.commonflowable.listener;


import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.impl.el.FixedValue;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务监听器
 *
 * 任务的执行
 */
@Data
public class TestTaskListener implements TaskListener {

    private FixedValue B;//对应流程字段flowable:field name="B"

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println(JSON.toJSONString(delegateTask));
        System.out.println("调用了任务监听器....");

        delegateTask.getVariables();
        System.out.println( delegateTask.getVariables().toString());
    }
}
