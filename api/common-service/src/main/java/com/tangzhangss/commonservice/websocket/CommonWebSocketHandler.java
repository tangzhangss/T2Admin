package com.tangzhangss.commonservice.websocket;


import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class CommonWebSocketHandler implements WebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession){
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus){
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage){
        if (webSocketMessage instanceof TextMessage) {
            String msg = ((TextMessage) webSocketMessage).getPayload();
            WSMessage message = JSONUtil.toBean(msg, WSMessage.class);
            StaticLog.info("Websocket收到消息:{}",message);
            //-----------------------------------------
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable){
        System.out.println(throwable.getMessage());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
