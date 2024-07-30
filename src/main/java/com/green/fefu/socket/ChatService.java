package com.green.fefu.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper mapper;

    private Map<String,ChatRoom> chatRooms;

    @PostConstruct
    private void init(){
        chatRooms = new HashMap<>();
    }
    public List<ChatRoom> findALlRooms(){
        return  new ArrayList<>(chatRooms.values());
    }
    public ChatRoom findRoomById(String roomId){
        return chatRooms.get(roomId);
    }
    public ChatRoom createRoom(String name){
        String randomID = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomID)
                .name(name)
                .build();
        chatRooms.put(randomID, chatRoom);
        return chatRoom;
    }
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        }catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

}
