package com.tangzhangss.commonmq.config;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 消息队列配置
 */
@ConfigurationProperties(prefix = "rocket-mq")
@Component
@Getter
@Setter
public class RocketMQConfig {
    //路由地址多个;分隔
    private String nameServer;
    //默认的组
    private String defaultGroup;
    //默认的topic
    private String defaultTopic;


    public String getNameServer() {
        if(nameServer.contains(";")){
            //有多个地址，随机取一个
            String[] nameServerArr = nameServer.split(";");
            return nameServerArr[RandomUtil.randomInt(nameServerArr.length-1)];
        }

        return nameServer;
    }
}
