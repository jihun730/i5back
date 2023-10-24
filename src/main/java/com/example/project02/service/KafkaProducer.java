package com.example.project02.service;


import com.example.project02.DTO.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToIot(String websocketMessage) {
        KafkaMessage kafkaMessage= KafkaMessage.builder()
            .Server("Spring")
            .Status("on")
            .Command(KafkaMessage.Command.builder()
                .Target("Cam")
                .Status(websocketMessage)
                .build())
            .build();

        // Kafka Producer를 통해 메시지를 Kafka 토픽으로 전송
        kafkaTemplate.send("toIot", kafkaMessage);
    }
}
