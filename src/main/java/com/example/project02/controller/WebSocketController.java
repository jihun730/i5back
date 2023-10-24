package com.example.project02.controller;

import com.example.project02.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  private final KafkaProducer kafkaProducer;

  @Autowired
  public WebSocketController(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }


  @MessageMapping("/IotControl")
  @SendTo("/topic/receive")
  public String sendMessage(String message) {
    // 메시지를 받아서 처리하는 로직 추가 (예: 로그에 기록)
    System.out.println("받은 메시지: " + message);

    // Kafka로 메시지를 전달
    kafkaProducer.sendMessageToIot(message);

    return "서버에서 보낸 메시지: " + message;
  }
}
