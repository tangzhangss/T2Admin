package com.tangzhangss.commonutils.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.log.StaticLog;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;


public class KafkaUtil {

    public static JSONArray getEndOffset(String group, String topic, Properties properties) {
        if (properties == null) {
            ExceptionUtil.throwException("The properties parameter(kafka configuration) is required");
        }
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

     public static boolean kafkaAlive(String bootstrapServers){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("connections.max.idle.ms", 10000);
        properties.put("request.timeout.ms", 5000);
        try (AdminClient client = KafkaAdminClient.create(properties)) {
            ListTopicsResult topics = client.listTopics(new ListTopicsOptions().timeoutMs(2000));
            Set<String> names = topics.names().get();
            StaticLog.info("connect to kafka success");
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static Producer<String, String> genProducer(String producerServers) {
        Properties kafkaProps = new Properties();
        kafkaProps.put("acks", "all");
        kafkaProps.put("retries", 0);
        // kafkaProps.put("compression.type", "snappy");
        kafkaProps.put("batch.size", 16384); // 100
        kafkaProps.put("linger.ms", 1);
        kafkaProps.put("buffer.memory", 1073741824); // 33554432 943718400
        // kafka 会接收单个消息size的最大限制 默认1m
        kafkaProps.put("message.max.bytes", 1073741824);
        kafkaProps.put("max.request.size", 1073741824);// 10485760 生产者能请求的最大消息
        kafkaProps.put("max.in.flight.requests.per.connection", 1);
        // 主机信息（broker）
        kafkaProps.put("bootstrap.servers", producerServers);
        // 键为字符串类型
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 值为字符串类型
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(kafkaProps);
        return producer;
    }

    public static KafkaConsumer<String, String> genConsumer(String consumerServers, String groupId, String... topics) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", consumerServers);// xxx是服务器集群的ip
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("fetch.max.bytes", 1073741824); // 这是消费者能读取的最大消息
        properties.put("auto.offset.reset", "latest");// latest,earliest
        properties.put("session.timeout.ms", "30000");
        properties.put("max.partition.fetch.bytes", 1073741824);
        properties.put("receive.message.max.bytes", 1073742824);
        properties.put("heartbeat.interval.ms", "2000");
        properties.put("max.poll.records", 100);
        properties.put("max.poll.interval.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList(topics));
        return kafkaConsumer;
    }


    public static void main(String[] args) {
//        System.out.println("kafka链接状态:" + new KafkaUtil().kafkaAlive());
        new Thread(()->{
            final KafkaConsumer<String, String> consumer = genConsumer("10.1.0.254:9092", "1websocket_alarm_data_topic", "alarm_data_topic");
            new Thread(() -> {
                int a = 0;
                while (true) {  //采用循环不断从partition里捞数据
                    ConsumerRecords<String, String> records = consumer.poll(200);
                    for (final ConsumerRecord record : records) {
                        System.out.println("消费数：" +  ++a);
                    }
                }
            }).start();
        }).start();

        new Thread(()->{
            final Producer<String, String> producer = genProducer("10.1.0.254:9092");
            for (int i = 0; i < 1000; i++) {
                producer.send(new ProducerRecord<>("alarm_data_topic", "{\"sid\": \"1675750648966575900\", \"alarm_subject\": \"10.0.86.148\", \"subject_mac\": \"40:a5:ef:49:47:f9\", \"subject_port\": \"58024\", \"subject_in_flag\": \"4\", \"alarm_object\": \"10.0.83.30\", \"object_mac\": \"60:da:83:36:bb:e7\", \"object_port\": \"53\", \"object_in_flag\": \"4\", \"alarm_name\": \"DGA恶意域名告警\", \"alarm_type\": \"2\", \"alarm_sub_type\": \"60\", \"alarm_level\": \"2\", \"alarm_desc\": \"当前访问的域名：mkyeexsbeqhh.com，判定为DGA恶意域名\", \"alarm_time\": \"1675750648\", \"suggestions\": \"请及时分析流量日志，排查是否存在风险行为和风险隐患。\", \"area\": \"1\", \"audit_dev_ip\": \"127.0.0.1\", \"audit_dev_mac\": \"00:00:00:00:00:00\", \"meta_data_from\": \"21\", \"meta_data_ids\": \"349398727064686\", \"detect_type\": \"31\", \"rule_id\": \"\", \"rule_name\": \"\", \"create_date\": \"1675750648\", \"read_flag\": \"\", \"full_audit\": \"\", \"full_packets\": \"\", \"ext1\": \"127.0.0.1\", \"ext2\": \"dns\", \"ext3\": \"\", \"ext4\": \"\", \"ext5\": \"\", \"ext6\": \"\", \"ext7\": \"异常流量分析\", \"ext8\": \"1\", \"ext9\": \"\"}"));
            }
        }).start();

    }
}
