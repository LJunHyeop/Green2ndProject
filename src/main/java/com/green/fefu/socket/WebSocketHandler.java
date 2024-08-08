package com.green.fefu.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();


    protected void handleTextMessage(WebSocketSession session, String message) throws  Exception {

        for (WebSocketSession socketSession: list) {
            socketSession.textMessage(message);
        }
    }

    // 클라이언트 접속 시 호출되는 메소드

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session + " 클라이언트 접속");
    }

    // 클라이언트 접속 해제 시 호출되는 메소드

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }

}
