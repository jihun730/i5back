    package com.example.project02.config;

    import com.example.project02.service.KafkaProducer;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Component;
    import org.springframework.web.socket.CloseStatus;
    import org.springframework.web.socket.TextMessage;
    import org.springframework.web.socket.WebSocketSession;
    import org.springframework.web.socket.handler.TextWebSocketHandler;

    import java.util.Map;
    import java.util.concurrent.ConcurrentHashMap;

    @Component
    public class MyWebSocketHandler extends TextWebSocketHandler {
        // WebSocket 세션을 저장할 맵 선언 및 초기화
        private static final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
        private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

        private final KafkaProducer kafkaProducer;

        public MyWebSocketHandler(KafkaProducer kafkaProducer) {
            this.kafkaProducer = kafkaProducer;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            // 클라이언트와 연결이 성립되었을 때 실행되는 로직
            String clientId = getClientId(session); // 클라이언트 식별자 가져오기
            sessionMap.put(clientId, session); // 세션을 맵에 저장하여 추후 사용

            // 클라이언트에게 환영 메시지 보내기
            String welcomeMessage = "환영합니다. 연결이 성립되었습니다.";
            session.sendMessage(new TextMessage(welcomeMessage));
        }

        private String getClientId(WebSocketSession session) {
            // 클라이언트 식별자를 추출 또는 생성하는 로직
            // 예: 세션 아이디를 클라이언트 식별자로 사용
            return session.getId();
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            // 클라이언트로부터 메시지를 받아 처리
            String receivedMessage = message.getPayload();

            kafkaProducer.sendMessageToIot(receivedMessage);
            // 처리된 메시지를 다른 클라이언트에게 브로드캐스트
            session.sendMessage(new TextMessage("서버에서 보낸 메시지: " + receivedMessage));
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            // 연결이 종료되었을 때 실행되는 로직
            String clientId = getClientId(session); // 클라이언트 식별자 가져오기
            sessionMap.remove(clientId); // 세션 맵에서 연결이 종료된 클라이언트 세션 제거

            // 클라이언트가 종료한 이유에 따라 로깅 또는 추가 작업 수행
            if (status != null) {
                String reason = status.toString();
                logger.info("클라이언트 {} 연결 종료: {}", clientId, reason);
            }
        }
    }
