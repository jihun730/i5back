package com.example.project02.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final MyWebSocketHandler myWebSocketHandler;
//
//    public WebSocketConfig(MyWebSocketHandler myWebSocketHandler) {
//        this.myWebSocketHandler = myWebSocketHandler;
//    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(myWebSocketHandler, "/websocket") // WebSocket 핸들러 등록 및 경로 설정
//                .setAllowedOrigins("*");
//    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커 설정
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 엔드포인트 설정 및 CORS 설정
        registry.addEndpoint("/your-sockjs-endpoint")
                .setAllowedOriginPatterns("*") // 모든 origin에서 접근을 허용하도록 설정
                .withSockJS(); // SockJS 지원 활성화
    }

    // WebSocket 세션 시간 설정
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendTimeLimit(1000 * 1000); // 메시지 송신 제한 시간 (1000초)
        registration.setSendBufferSizeLimit(1024 * 1024); // 메시지 버퍼 크기 제한 (1MB)
        registration.setMessageSizeLimit(1024 * 1024); // 메시지 크기 제한 (1MB)
    }
}
