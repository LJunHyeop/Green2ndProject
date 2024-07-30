package com.green.fefu.socket;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

;

@Getter
public class ChatRoom {
    private String roomId;

    private String name;

    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }
    public  void handlerActions(WebSocketSession session,ChatMessageDto chatMessageDto , ChatService chatService) {
        if(chatMessageDto.getType().equals(chatMessageDto.getType().ENTER)){
            sessions.add(session);
            chatMessageDto.setMessage((chatMessageDto.getSender())+"님이 입장했다.");
        }
        sendMessage(chatMessageDto,chatService);
    }
    public <T> void sendMessage(T message,ChatService chatservice){
        sessions.parallelStream().forEach(session -> chatservice.sendMessage(session,message));
    }
}
