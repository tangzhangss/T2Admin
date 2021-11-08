package com.tangzhangss.commonmq.message;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tangzhangss.commonmq.config.RocketMQConfig;
import com.tangzhangss.commonmq.producer.Producer;
import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送（生产消息）
 */
@RestController
@RequestMapping("/message")
public class MessageApi extends SysBaseApi<MessageEntity, MessageService> {

    @Autowired
    private Producer producer;
    @Autowired
    private RocketMQConfig rocketMQConfig;

    private void initMessageEntity(MessageEntity messageEntity){
        if(StringUtils.isBlank(messageEntity.getSourceService())) ExceptionUtil.throwException("参数#{0}是必须的，请检查!","sourceService");
        if(StringUtils.isBlank(messageEntity.getContent())) ExceptionUtil.throwException("参数#{0}是必须的，请检查!","content");

        if(StringUtils.isBlank(messageEntity.getProducerGroup()))messageEntity.setProducerGroup(rocketMQConfig.getDefaultGroup());
        if(StringUtils.isBlank(messageEntity.getTopic()))messageEntity.setTopic(rocketMQConfig.getDefaultTopic());
    }

    /**
     * 发送同步消息
     * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知
     *
     * 注意:如果有消费者 会被消费者 消费 可能会在这个过程之前完成
     * @param messageEntity
     */
    @PutMapping("/send_sync")
    @Transactional
    public Result sendSync(@RequestBody MessageEntity messageEntity) throws Exception {

        MQProducer mqProducer = this.producer.getProducer(messageEntity.getProducerGroup());

        initMessageEntity(messageEntity);

        Message message = new Message(messageEntity.getTopic()
                ,messageEntity.getTag(),messageEntity.getContent().getBytes(RemotingHelper.DEFAULT_CHARSET));

        SendResult sendResult = mqProducer.send(message);

        messageEntity.setMsgId(sendResult.getMsgId());

        if(!sendResult.getSendStatus().toString().equals("SEND_OK")){
            ExceptionUtil.throwException("消息发送失败，#{0}",sendResult.getSendStatus().toString());
        }

        messageEntity.setSendResult(JSONUtil.toJsonStr(sendResult));

        //保存
        myService.save(messageEntity);

        return Result.ok().data(messageEntity);
    }


    /**
     *消费消息确认
     * [
     *    {
     * 		"msgId":"rocket msgId",
     * 		"topic":"消费主题",
     * 		"isConsumeSuccess":"是否消费成功",
     * 		"consumptionRemark":"消费备注",
     * 	    "consumptionInfo":"消费信息MessageExt”
     *    }
     * ]
     */
    @PutMapping("/consume_sure")
    public Result sure(@RequestBody List<JSONObject> paramArr){
        List<MessageEntity> messageEntityList = new ArrayList<>();
        for (JSONObject param : paramArr){//回写消息状态
            MessageEntity messageEntity = myService.getOneCustomWithMapString("msgId@EQ=" + param.getStr("msgId")
                    + "&topic@EQ=" + param.getStr("topic"));

            if (messageEntity == null) {
                ExceptionUtil.throwException("未找到topic:#{0},msgId:#{1}的发送记录，请检查!", param.getStr("topic"), param.getStr("msgId"));
            }
            messageEntity.setIsConsumed(true);
            //是否消费成功 默认true
            messageEntity.setIsConsumeSuccess(param.getBool("isConsumeSuccess",true));
            messageEntity.setConsumptionRemark(param.getStr("consumptionRemark"));
            //消费时间
            messageEntity.setConsumptionTime(LocalDateTime.now());
            //消费明细
            messageEntity.setConsumptionDetail(param.getStr("consumptionDetail"));

            messageEntityList.add(messageEntity);
        }
        //更新
        myService.update(messageEntityList);

        return Result.ok();
    }
}
