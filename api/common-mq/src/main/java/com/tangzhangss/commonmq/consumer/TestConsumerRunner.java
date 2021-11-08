package com.tangzhangss.commonmq.consumer;

import cn.hutool.json.JSONObject;
import com.tangzhangss.commonmq.config.RocketMQConfig;
import com.tangzhangss.commonmq.message.MessageApi;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试消费者线程
 */
@Component
public class TestConsumerRunner implements CommandLineRunner{
    @Autowired
    RocketMQConfig rocketMQConfig;
    @Autowired
    MessageApi messageApi;

    public void run(String... args) throws Exception {
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMQConfig.getDefaultGroup());

        // 设置NameServer的地址
        consumer.setNamesrvAddr(rocketMQConfig.getNameServer());

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(rocketMQConfig.getDefaultTopic(), "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            //消费消息
            try {
                List<JSONObject> paramArr = new ArrayList<>();
                for (MessageExt msg : msgs) {
                    JSONObject obj = new JSONObject();
                    System.out.println("正在消费消息....");
                    obj.set("msgId",msg.getMsgId());
                    obj.set("topic",msg.getTopic());
                    obj.set("isConsumeSuccess",true);//根据业务设置是否消费成功
                    obj.set("consumptionRemark","");//消费备注 根据业务设置是否消费成功
                    obj.set("consumptionDetail",msg);//消费信息明细
                    paramArr.add(obj);
                    Thread.sleep(2000);

                }
                messageApi.sure(paramArr);
                System.out.println("消息消费完成....");
            }catch (Exception e) {
                e.printStackTrace();
                // 标记该消息被消费失败
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            // 标记该消息已经被成功消费
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
