package com.green.fefu.socket.model;

import com.green.fefu.entity.ChatMsg;
import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.ChatRoomId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMsgDto {

    private String sender;
    private String msg;
    private ChatRoomId roomId;
    private Long ItemId;


    public ChatMsg createChat(String sender, String msg, ChatRoomDto chatRoomDto) {

        ChatRoom chatRoom = new ChatRoom(chatRoomDto.getTeaId(), chatRoomDto.getParentsId());

        return ChatMsg.builder()
                .sender(sender)
                .msg(msg)
                .chatRoom(chatRoom)
                .build();
    }

}
