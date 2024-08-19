package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import lombok.*;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoomDto {
    private Long roomId;
    private TeacherDto teaId;
    private Set<WebSocketSession> sessions = new HashSet<>();
    private ParentsDto parentsId;
    private List<ChatMsgDto> messages;



    @Builder
    public ChatRoomDto(Long roomId, TeacherDto teaId, ParentsDto parentsId, List<ChatMsgDto> messages ) {
        this.roomId = roomId;
        this.teaId = teaId;
        this.parentsId = parentsId;
        this.messages = messages;

    }
}
