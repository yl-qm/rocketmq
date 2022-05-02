package com.syl.demo;

import com.syl.demo.service.ProducerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RocketmqProducerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RocketmqProducerApplication.class, args);
        ProducerService producerService = (ProducerService) run.getBean("producerService");
        // producerService.sendMessage();
        // producerService.sendSyncMessage();
        // producerService.sendAsyncMessage();
        // producerService.sendOneWayMessage();
        // producerService.sendSyncOrderlyMessage();
        // producerService.sendDelayMessage();
        // producerService.sendTransactionMessage();
        producerService.sendMessageWithTag();
        System.out.println("sendMessage finish");
    }
}
