package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import lombok.*;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {

    private Long roomId;

    private TeacherDto teaId;

    private Set<WebSocketSession> sessions = new HashSet<>();

    private ParentsDto parentsId;


    @Builder
    public ChatRoomDto(Long roomId, TeacherDto teaId, ParentsDto parentsId) {
        this.roomId = roomId;
        this.teaId = teaId;
        this.parentsId = parentsId;
    }
}
