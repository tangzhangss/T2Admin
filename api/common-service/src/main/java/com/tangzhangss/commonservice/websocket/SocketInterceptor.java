package com.tangzhangss.commonservice.websocket;

import cn.hutool.http.HttpUtil;
import cn.hutool.log.StaticLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class SocketInterceptor implements HandshakeInterceptor {

    /**
     * websocket 握手之前
     *
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        StaticLog.info("websocket starts handshaking");
        // 获取请求参数
        return true;
    }

    /**
     * websocket 握手之后
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {
        StaticLog.info("握手完成!");
    }
}
