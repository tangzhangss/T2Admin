package com.tangzhangss.commonservice.websocket;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WSMessage implements java.io.Serializable {
    /**
     * 服务标识
     * 设备监视  = kafka-topic-monitor
     * 【不能为null】
     */
    private String tag="";
    /**
     消息内容
     */
    private String content;
}
