package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.ChatRoomId;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class ChatRoomDto {

    private ChatRoomId roomId;

    private Teacher teaId;

    private Set<WebSocketSession> sessions = new HashSet<>();

    private Parents parentsId;

    @Builder
    public ChatRoomDto(ChatRoomId roomId, Teacher teaId, Parents parentsId) {
        this.roomId = roomId;
        this.teaId = teaId;
        this.parentsId = parentsId;
    }
}
