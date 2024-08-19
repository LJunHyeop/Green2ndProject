package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatMsg;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class ChatMsgDto {

    private String msg;

    private Long roomId;

   private SenderDto sender;

    // 모든 필드를 초기화하는 생성자
    public ChatMsgDto(String msg, Long roomId, SenderDto sender) {
        this.msg = msg;
        this.roomId = roomId;
        this.sender = sender;
    }
}
