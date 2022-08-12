package com.tangzhangss.commones.demo;

import com.tangzhangss.commonutils.resultdata.Result;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test_doc")
public class TestDocApi {
    @Autowired
    TestDocDao testDocDao;

    @PostMapping
    public Result add(@RequestBody TestDocEntity testDocEntity){
        return Result.ok().data(testDocDao.save(testDocEntity));
    }
    @GetMapping
    public Result get(){
        //创建builder
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        return Result.ok().data(testDocDao.findAll());
    }
}
