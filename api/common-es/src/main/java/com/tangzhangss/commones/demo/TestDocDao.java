package com.tangzhangss.commones.demo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TestDocDao extends ElasticsearchRepository<TestDocEntity,Long>{
}
