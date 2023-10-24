package com.example.project02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public KafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "toIot", groupId = "kafka-demo")
    public void consumeIot(String message) throws IOException {
        System.out.println(String.format("Consumed message : %s", message));
    }


    @KafkaListener(topics = "toWeb", groupId = "kafka-demo")
    public void consumeWeb(String message) {
        System.out.println(String.format("Consumed message from Kafka: %s", message));

        // 받은 메시지를 웹소켓으로 클라이언트에게 전달
        messagingTemplate.convertAndSend("/topic/receive", message);
    }
}
