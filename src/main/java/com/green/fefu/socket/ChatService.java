
package com.green.fefu.socket;

import com.green.fefu.entity.*;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.socket.model.ChatMsgDto;
import com.green.fefu.socket.model.ChatRoomDto;
import com.green.fefu.socket.repository.ChatMsgRepository;
import com.green.fefu.socket.repository.ChatRoomIdRepository;
import com.green.fefu.socket.repository.ChatRoomRepository;
import com.green.fefu.teacher.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.Result;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatMsgRepository chatMsgRepository;

    private final TeacherRepository teacherRepository;

    private final ParentRepository parentRepository;

    private final ChatRoomIdRepository chatRoomIdRepository;

    @Transactional // 채팅방생성
    public ChatRoomId createRoom(Parents parentsId, Teacher teacherId) {
        ChatRoomId chatRoomId = new ChatRoomId();
        chatRoomIdRepository.save(chatRoomId);

            ChatRoom chatRoom = new ChatRoom(teacherId,parentsId);
            chatRoom.setRoomId(chatRoomId);
            chatRoomRepository.save(chatRoom);
        return chatRoom.getRoomId();
    }

        // 채팅방 찾기
    public ChatRoomDto findRoom(Long roomId){
        List<ChatRoomDto> chatRoomList = chatRoomRepository.getChatRoomList(roomId);
        return chatRoomList.get(0);
    }
    // 특정 채팅방 조회
    public List<ChatRoomDto> findRoomId(Parents parentsId , Teacher teacherId){
        List<ChatRoomDto> chatRoomDtoList = chatRoomRepository.getChatRoomId(parentsId, teacherId);

        return chatRoomDtoList;
    }


    @Transactional
    // 채팅메시지 DB에 저장
    public void saveChat(ChatMsgDto chatMsgDto){
        List<ChatRoomDto> chatRooms = chatRoomRepository.getChatRoomList(chatMsgDto.getRoomId().getId());
        ChatMsg chatMsg = chatMsgDto.createChat(chatMsgDto.getSender(), chatMsgDto.getMsg(), chatRooms.get(0));
        chatMsgRepository.save(chatMsg);
    }


    // 선생 전체리스트
    public List<ChatRoomDto> findAllRoomsByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        List<ChatRoom> chatRooms = chatRoomRepository.findAllByTeacherId(teacher);

        return chatRooms.stream()
                .map(this::convertToChatRoomDto)
                .collect(Collectors.toList());
    }
    private ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom) {
        return ChatRoomDto.builder()
                .roomId(chatRoom.getRoomId())
                .teaId(chatRoom.getTeaId())
                .parentsId(chatRoom.getParentsId())
                .build();
    }

    //학부모 채팅  전체리스트
    public List<ChatRoomDto> findAllRoomsByParents(Long parentsId) {
        Parents parents = parentRepository.findById(parentsId)
                .orElseThrow(() -> new EntityNotFoundException("parents not found"));
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByParentsId(parents);

        return chatRooms.stream()
                .map(this::convertToChatRoomDto)
                .collect(Collectors.toList());
    }

}
