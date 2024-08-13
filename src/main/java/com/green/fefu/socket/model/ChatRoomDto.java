package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

public class ChatRoomDto {

    private Long roomId;

    private Teacher teaId;

    private Set<WebSocketSession> sessions = new HashSet<>();

    private Parents parentsId;


    @Builder
    public ChatRoomDto(Long roomId, Teacher teaId, Parents parentsId) {
        this.roomId = roomId;
        this.teaId = teaId;
        this.parentsId = parentsId;
    }
}
