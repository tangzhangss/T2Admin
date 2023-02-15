package com.tangzhangss.commonutils.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.log.StaticLog;
import com.tangzhangss.commonutils.utils.KafkaUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Component
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaService {

    private String bootstrapServers;

    private Properties properties;

    @PostConstruct
    public void init(){

        if (StringUtils.isEmpty(bootstrapServers)) {
            StaticLog.info("........未检查到kafka配置........");
            return;
        }

        properties = new Properties();
        properties.setProperty(AdminClientConfig.
                BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //给kafka内部的key和value反序列化
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    /**
     * 获取消费组单个topic消息写入总量
     *
     * @param group 消费者组id,可为null，随机生成一个
     * @param topic topic
     * @return JSONArray
     */
    public JSONArray getEndOffset(String group, String topic) {
        return KafkaUtil.getEndOffset(group, topic, properties);
    }
    public boolean kafkaAlive() {
        return KafkaUtil.kafkaAlive(bootstrapServers);
    }

    public Producer<String, String> genProducer() {
        return KafkaUtil.genProducer(bootstrapServers);
    }

    public KafkaConsumer<String, String> genConsumer(String groupId, String[] topics) {
        return KafkaUtil.genConsumer(bootstrapServers, groupId, topics);
    }

}
