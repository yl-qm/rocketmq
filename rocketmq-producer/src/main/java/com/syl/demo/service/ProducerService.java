package com.syl.demo.service;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("producerService")
public class ProducerService {

    private final RocketMQTemplate rocketMQTemplate;

    @Autowired
    public ProducerService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void sendMessage() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.convertAndSend("syl-rocketmq-demo1", "hello mq" + i);
        }
    }

    public void sendSyncMessage() {
        for (int i = 0; i < 10; i++) {
            SendResult sendResult = rocketMQTemplate.syncSend("syl-rocketmq-demo1", "syncMessage" + i);
            System.out.println(sendResult);
        }
    }

    public void sendAsyncMessage() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.asyncSend("syl-rocketmq-demo1", "asyncMessage" + i, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功" + sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败" + throwable);
                }
            });
        }
    }

    public void sendOneWayMessage() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.sendOneWay("syl-rocketmq-demo1", "oneWayMessage" + i);
        }
    }

    public void sendSyncOrderlyMessage() {
        rocketMQTemplate.syncSendOrderly("syl-rocketmq-orderly1", "12211订单创建", "12211");
        rocketMQTemplate.syncSendOrderly("syl-rocketmq-orderly1", "12211订单支付", "12211");
        rocketMQTemplate.syncSendOrderly("syl-rocketmq-orderly1", "12211订单完成", "12211");

        rocketMQTemplate.syncSendOrderly("syl-rocketmq-orderly1", "12213订单创建", "12213");
        rocketMQTemplate.syncSendOrderly("syl-rocketmq-orderly1", "12213订单支付", "12213");
        rocketMQTemplate.syncSendOrderly("syl-rocketmq-orderly1", "12213订单完成", "12213");
    }

    public void sendDelayMessage() {
        rocketMQTemplate.syncSend("syl-rocketmq-demo1", MessageBuilder.withPayload("delayMessage-level1").build(), 3000, 1);
        rocketMQTemplate.syncSend("syl-rocketmq-demo1", MessageBuilder.withPayload("delayMessage-level2").build(), 3000, 2);
        rocketMQTemplate.syncSend("syl-rocketmq-demo1", MessageBuilder.withPayload("delayMessage-level3").build(), 3000, 3);
    }

    public void sendTransactionMessage() {
        rocketMQTemplate.sendMessageInTransaction("syl-rocketmq-demo1", MessageBuilder.withPayload("transactionMessage-1").build(), "extra-stringName");
    }

    public void sendMessageWithTag() {
        Map<String, Object> headers1 = new HashMap<>();
        headers1.put("type", "1");
        headers1.put("key", "12138");
        Map<String, Object> headers2 = new HashMap<>();
        headers2.put("type", "2");
        headers2.put("key", "15115");
        Map<String, Object> headers3 = new HashMap<>();
        headers3.put("type", "3");
        headers3.put("key", "33690");
        rocketMQTemplate.convertAndSend("syl-rocketmq-demo1:Tag1", "tagMessage1", headers1);
        rocketMQTemplate.convertAndSend("syl-rocketmq-demo1:Tag2", "tagMessage2", headers2);
        rocketMQTemplate.convertAndSend("syl-rocketmq-demo1:Tag3", "tagMessage3", headers3);
    }
}
