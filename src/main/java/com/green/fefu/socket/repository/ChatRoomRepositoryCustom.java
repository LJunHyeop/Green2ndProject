package com.green.fefu.socket.repository;

import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import com.green.fefu.socket.model.ChatRoomDto;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoomDto> getChatRoomList(Long roomId);

    List<ChatRoomDto> getChatRoomId(Parents ParentsId, Teacher teacher);
}
