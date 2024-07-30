package com.green.fefu.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //endpoint 설정 : /api/v1/char/{postId}
        //이를 통해 ws : // localhost : 8080/ws/chat로 들어오면 websocket 통신 진챙
        //setAllowedOrigins 모든 ip 에서 접속 가능
        registry.addHandler(webSocketHandler, "/ws").setAllowedOrigins("*");
    }
}
