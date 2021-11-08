package com.tangzhangss.commonmq.producer;

import com.tangzhangss.commonmq.config.RocketMQConfig;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 生成消息
 */
@Component
public class Producer {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RocketMQConfig rocketMQConfig;

    //默认的消费者--长期存在
    public DefaultMQProducer producer = null;

    @PostConstruct
    public void init() throws MQClientException {
        producer = new DefaultMQProducer(rocketMQConfig.getDefaultGroup());

        producer.setNamesrvAddr(rocketMQConfig.getNameServer());

        producer.start();

        logger.info("默认的producer启动成功!");
    }

    /*
        获取一个生成者对象，需要在使用之后自己shutdown
     */
    public MQProducer getProducer(String producerGroup){
        if(StringUtils.isBlank(producerGroup)){
            //没有传就返回空的
            return this.producer;
        }
        DefaultMQProducer newProducer = null;
        try{
            newProducer = new DefaultMQProducer(producerGroup);
            newProducer.setNamesrvAddr(rocketMQConfig.getNameServer());
            newProducer.start();
        }catch (MQClientException e) {
            newProducer.shutdown();
            ExceptionUtil.throwException(e.getErrorMessage());
        }


        return newProducer;
    }


    @PreDestroy
    public void destroy(){
        producer.shutdown();
    }

}
