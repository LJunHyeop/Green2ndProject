package com.green.fefu.socket;

import com.green.fefu.entity.ChatMsg;
import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.socket.model.ChatMsgDto;
import com.green.fefu.socket.model.ChatRoomDto;
import com.green.fefu.socket.repository.ChatMsgRepository;
import com.green.fefu.socket.repository.ChatRoomRepository;
import com.green.fefu.teacher.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional // 채팅방생성
    public Long createRoom(Parents parentsId, Teacher teacherId) {
        ChatRoom chatRoom = new ChatRoom(teacherId,parentsId);
        chatRoomRepository.save(chatRoom);

        return chatRoom.getRoomId();
    }
    public ChatRoomDto findRoom(Long roomId){
        List<ChatRoomDto> chatRoomList = chatRoomRepository.getChatRoomList(roomId);
        return chatRoomList.get(0);
    }
    public Long findRoomId(Parents parentsId , Teacher teacherId){
        List<ChatRoomDto> chatRoomDtoList = chatRoomRepository.getChatRoomId(parentsId, teacherId);
        if(chatRoomDtoList.size()>0){
            return chatRoomDtoList.get(0).getRoomId();
        }
        return null;
    }

    @Transactional
    public void saveChat(ChatMsgDto chatMsgDto){
        List<ChatRoomDto> chatRooms = chatRoomRepository.getChatRoomList(chatMsgDto.getRoomId());
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
