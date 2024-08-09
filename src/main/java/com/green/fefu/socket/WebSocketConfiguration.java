package com.green.fefu.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class WebSocketConfiguration implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler()).setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler webSocketHandler(){
        return new WebSocketHandler();
    }

}
