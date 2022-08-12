package com.tangzhangss.commones.demo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//需要使用Document注解，indexName:库的名称,建议以项目的名称命名，type:存储类型，一般使用bean的名称，
//分片（Shard）以及副本（Replica），分布式存储系统为了解决单机容量以及容灾的问题，都需要有分片以及副本机制。
@Data
@Document(indexName = "common_es",type = "test_doc",shards = 1,replicas = 0)
public class TestDocEntity {
    @Id
    private Long id;

    private String content;
}
