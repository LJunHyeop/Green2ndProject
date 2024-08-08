package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatMsg;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMsgDto {

    private String sender;
    private String msg;
    private Long roomId;
    private Long ItemId;


    public ChatMsg createChat(String sender, String msg, ChatRoomDto chatRoomDto) {
        return ChatMsg.builder()
                .sender(sender)
                .msg(msg)
                .chatRoom(chatRoomDto.Entity())
                .build();
    }

}
