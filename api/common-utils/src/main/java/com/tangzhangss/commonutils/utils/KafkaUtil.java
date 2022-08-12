package com.tangzhangss.commonutils.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.log.StaticLog;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
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
public class KafkaUtil {
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
        JSONArray array = new JSONArray();

        Properties props = (Properties) properties.clone();
        props.put("group.id", Optional.ofNullable(group).orElseGet(()-> UUID.randomUUID().toString()));
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 获取分区信息
        List<PartitionInfo> partitionsFor = consumer.partitionsFor(topic);

        for (PartitionInfo partitionInfo : partitionsFor) {
            JSONObject obj = new JSONObject();
            Collection<TopicPartition> partitions = new ArrayList<>();
            TopicPartition tp = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
            partitions.add(tp);
            //获取当前分区，消费总数
            Map<TopicPartition, Long> endOffset = consumer.endOffsets(partitions);
            long logEndOffSet = Optional.ofNullable(endOffset.get(tp)).orElse(0l);
            obj.set("logEndOffset",logEndOffSet);

            //获取当前分区，当前消费数
            final OffsetAndMetadata committed = consumer.committed(tp);
            long offset = 0;
            if(committed!=null) offset=committed.offset();
            obj.set("currentOffset",offset);
            //积压数
            obj.set("lag",logEndOffSet-offset);
            //分区
            obj.set("partition",tp.partition());
            obj.set("topic",topic);
            obj.set("group",group);
            array.add(obj);
        }
        consumer.close();

        return array;
    }


   /* @PostConstruct
    public void test() throws ExecutionException, InterruptedException {

        if (StringUtils.isEmpty(bootstrapServers)) {
            return;
        }

        Properties props = (Properties) properties.clone();

        AdminClient adminClient = AdminClient.create(props);

        final Set<String> strings = adminClient.listTopics().names().get();

        String[] topicArr = new String[strings.size()];
        strings.toArray(topicArr);

        for (String s : topicArr) {
            StaticLog.info(getEndOffset("t",s).toStringPretty());

        }

    }*/
}
