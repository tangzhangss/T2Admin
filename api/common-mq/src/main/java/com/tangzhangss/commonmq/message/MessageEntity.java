package com.tangzhangss.commonmq.message;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 消息实体(日志)
 */
@Data
@Entity
@Table(name = "tbl_common_mq_message")
public class MessageEntity extends SysBaseEntity {
    private String content;//消息内容
    private String tag;//消息tag
    @Column(nullable = false)
    private String topic;//消息topic
    @Column(nullable = false)
    private String producerGroup;//producer 组

    @Column(nullable = false)
    private String sourceService;//谁发的消息，一个标识

    @Column(columnDefinition = "TEXT")
    private String sendResult;//发送结果

    private Boolean isConsumed=false;//是否被消费
    private Boolean isConsumeSuccess;//是否消费成功

    private String consumptionRemark;//消费备注 "success"

    @Column(columnDefinition = "TEXT")
    private String consumptionDetail;//消费明细信息

    private LocalDateTime consumptionTime;//消费时间

    private String msgId;//rocket msgId 消费之后用来找到消息日志
}
