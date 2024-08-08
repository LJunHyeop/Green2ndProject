package com.green.fefu.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send/message")
    public void onReceiveMessage(String message) {
        System.out.println("Received message: " + message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}