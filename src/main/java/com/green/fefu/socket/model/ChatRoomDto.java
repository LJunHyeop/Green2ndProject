package com.green.fefu.socket.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRoomDto {
    private Long roomId;
    private TeacherDto teaId;
    private Set<WebSocketSession> sessions = new HashSet<>();
    private List<ParentsDto> parents = new ArrayList<>();  // 초기화
    private List<ChatMsgDto> messages;
    private String loginUserName;

    @Builder
    public ChatRoomDto(Long roomId, TeacherDto teaId, List<ParentsDto> parents, List<ChatMsgDto> messages) {
        this.roomId = roomId;
        this.teaId = teaId;
        this.parents = parents != null ? parents : new ArrayList<>();
        this.messages = messages;
    }

    public void addParents(List<ParentsDto> newParents) {
        if (this.parents == null) {
            this.parents = new ArrayList<>();
        }
        this.parents.addAll(newParents);
    }
}