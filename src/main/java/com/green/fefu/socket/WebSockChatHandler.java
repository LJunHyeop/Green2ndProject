package com.green.fefu.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
// 삭제        TextMessage textMessage = new TextMessage("Welcome chatting sever~^^ ");
// 삭제       session.sendMessage(textMessage);
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handlerActions(session, chatMessage, chatService);
    }
}
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class WebSocketChatHandler extends TextWebSocketHandler {
//    private  final ObjectMapper mapper;
//    // 현재 연결된 세션
//    private final Set<WebSocketSession> sessions = new HashSet<>();
//    // chatRoomId : {session1 , session2}
//    private final Map<String, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
//    // 소켓 연결 확인
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        //TODO Auto-generated method stud
//        log.info("{}연결됨", session.getId());
//        sessions.add(session);
//    }
//    // 소켓 통신시 메세지의 전송 을 다룸
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
//        //페이로드 -> chatMessageDto 로 변환
//        ChatMessageDto chatMessageDto = mapper.readValue(payload,ChatMessageDto.class);
//        log.info("session {}",chatMessageDto.toString());
//
//        String chatRoomId = chatMessageDto.getRoomId();
//        //메모리상에 채팅방이 없으면 만듦
//        if(!chatRoomSessionMap.containsKey(chatRoomId)) {
//            chatRoomSessionMap.put(chatRoomId, new HashSet<>());
//        }
//        //message 에 담긴 타입 확인
//        // message 에서 getType 로 가져온 내용이 chatDTO 의 열거형인 MessageType 안에있는 ENTER과 동일한 값이라면 ?
//        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);
//        if (chatMessageDto.getType().equals(ChatMessageDto.MessageType.ENTER)) {
//            // sessions 에 넘어온 session 을 담고,
//            chatRoomSession.add(session);
//        }
//        if(chatRoomSession.size() >= 3){
//            removeClosedSession(chatRoomSession);
//        }
//        sendMessageToChatRoom(chatMessageDto, chatRoomSession);
//    }
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        // TODO auto-generated method stub
//        log.info("{} 연결 끊김",session.getId());
//        sessions.remove(session);
//    }
//
//    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
//    }
//
//    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));//2
//    }
//    public <T> void sendMessage(WebSocketSession session, T message) {
//        try {
//            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
//        }catch (IOException e) {
//            log.error(e.getMessage(),e);
//        }
//    }
//}
