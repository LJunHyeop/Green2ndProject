package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatMsg;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class ChatMsgDto {
    private String msg;
    private Long roomId;
    private String sender;
    private LocalDateTime sendTime;  // 시간 정보 추가

    // 생성자, getter, setter
    public ChatMsgDto(String msg, Long roomId, String sender, LocalDateTime sendTime) {
        this.msg = msg;
        this.roomId = roomId;
        this.sender = sender;
        this.sendTime = sendTime;
    }
}
