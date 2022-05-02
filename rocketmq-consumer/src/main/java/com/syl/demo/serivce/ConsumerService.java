package com.syl.demo.serivce;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}",
        topic = "syl-rocketmq-demo1",
        messageModel = MessageModel.CLUSTERING)
@Component
public class ConsumerService implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println(message);
        System.out.println("good");
    }
}
