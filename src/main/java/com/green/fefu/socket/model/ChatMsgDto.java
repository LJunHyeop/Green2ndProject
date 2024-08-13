package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatMsg;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChatMsgDto {

    private String sender;
    private String msg;
    private Long roomId;
    private Long ItemId;




}
