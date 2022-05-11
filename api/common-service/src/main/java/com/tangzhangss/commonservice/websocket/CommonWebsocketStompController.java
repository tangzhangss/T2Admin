package com.tangzhangss.commonservice.websocket;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class CommonWebsocketStompController {
    public final static String TEST_TOPIC="TEST-TOPIC";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping(TEST_TOPIC)
    public void testTopic(String payload){
        StaticLog.info("收到消息:{}",payload);
        WSMessage wsMessage = JSONUtil.toBean(payload, WSMessage.class);
        messagingTemplate.convertAndSend("/topic/"+TEST_TOPIC,wsMessage);
    }
    @MessageMapping(TEST_TOPIC+"/{sign}")
    public void testTopicSign(String payload, @DestinationVariable("sign")String sign){
        StaticLog.info("收到消息:{}",payload);
        WSMessage wsMessage = JSONUtil.toBean(payload, WSMessage.class);
        messagingTemplate.convertAndSend("/topic/"+TEST_TOPIC+"/"+sign,wsMessage);
    }


}
