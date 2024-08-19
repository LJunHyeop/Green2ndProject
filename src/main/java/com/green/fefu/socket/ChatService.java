package com.green.fefu.socket;

import com.green.fefu.entity.*;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.socket.model.ChatMsgDto;
import com.green.fefu.socket.model.ChatRoomDto;
import com.green.fefu.socket.model.ParentsDto;
import com.green.fefu.socket.model.TeacherDto;
import com.green.fefu.socket.repository.ChatMsgRepository;
import com.green.fefu.socket.repository.ChatRoomMemberRepository;
import com.green.fefu.socket.repository.ChatRoomRepository;
import com.green.fefu.socket.repository.ChatRoomRepositoryCustomImpl;
import com.green.fefu.teacher.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.xerces.impl.dv.xs.IDDV;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    // 필요한 리포지토리들을 주입받습니다.
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMsgRepository chatMsgRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomRepositoryCustomImpl customRepository;

    /*
      새로운 채팅방을 생성하고 부모와 선생님을 멤버로 추가합니다.
      @param parent 채팅방에 추가할 부모
      @param teacher 채팅방에 추가할 선생님
      @return 생성된 채팅방의 ID
     */
    @Transactional
    public Long createRoom(Parents parent, Teacher teacher) {
        // 새로운 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();

        chatRoomRepository.save(chatRoom);

        // 부모와 선생님을 채팅방 멤버로 추가
        ChatRoomMember parentMember = new ChatRoomMember(chatRoom, parent);
        ChatRoomMember teacherMember = new ChatRoomMember(chatRoom, teacher);

        chatRoomMemberRepository.save(parentMember);
        chatRoomMemberRepository.save(teacherMember);

        return chatRoom.getId();
    }

    /**
     * 채팅방 ID로 채팅방을 찾아 DTO로 변환하여 반환합니다.

     */
    public List<ChatRoomDto> findRoom(Long roomId) {
       ChatRoom chatRoom = customRepository.findById(roomId).orElseThrow(NullPointerException::new);
       List<ChatRoomDto> dtos = new ArrayList<>();
       ChatRoomDto a = new ChatRoomDto();
       chatRoom.getMembers().forEach(chatRoomMember -> {
               if (chatRoomMember.getTeacher() != null) {
                    a.setTeaId(new TeacherDto(chatRoomMember.getTeacher()));
               }
               if (chatRoomMember.getParent() != null) {
                   a.setParentsId(new ParentsDto(chatRoomMember.getParent()));
               }
               a.setRoomId(chatRoom.getId());
               dtos.add(a);
           }) ;
        System.out.println("체크포인트");
       return dtos;
//        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
//                .orElseThrow(() -> new ResourceNotFoundException("ChatRoom not found"));
//        return convertToChatRoomDto(chatRoom);
    }
    /*
     부모와 선생님으로 채팅방을 찾아 ID를 반환합니다.
      @param parent 찾을 채팅방의 부모
      @param teacher 찾을 채팅방의 선생님
     @return 채팅방 ID, 없으면 null
     */
    public Long findAllByMembersParent(Parents parent, Teacher teacher) {
        return chatRoomRepository.findByMembersParentAndMembersTeacher(parent, teacher)
                .map(ChatRoom::getId)
                .orElse(null);
    }

    /*
      채팅 메시지를 저장합니다.
      @param chatMsgDto 저장할 채팅 메시지 DTO
     */
    @Transactional
    public void saveChat(ChatMsgDto chatMsgDto) {
        // 채팅방을 찾습니다.
        ChatRoom chatRoom = chatRoomRepository.findById(chatMsgDto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다."));

        // 새로운 채팅 메시지를 생성하고 설정합니다.
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setChatRoom(chatRoom);
        chatMsg.setSender(chatMsgDto.getSender());
        chatMsg.setMessage(chatMsgDto.getMsg());

        // 채팅 메시지를 저장합니다.
        chatMsgRepository.save(chatMsg);
    }

    /*
      채팅방에 부모를 추가합니다.
      @param roomId 부모를 추가할 채팅방 ID
      @param parent 추가할 부모
     */
    @Transactional
    public void addParentToRoom(Long roomId, Parents parent) {
        // 채팅방을 찾습니다.
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다."));

        // 새로운 채팅방 멤버를 생성하고 저장합니다.
        ChatRoomMember chatRoomMember = new ChatRoomMember(chatRoom, parent);
        chatRoomMemberRepository.save(chatRoomMember);
    }

    /*
      선생님 ID로 모든 채팅방을 찾아 DTO 리스트로 반환합니다.
      @param teacherId 찾을 선생님의 ID
      @return 채팅방 DTO 리스트
     */
    public List<ChatRoomDto> findAllTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        List<ChatRoom> chatRooms = customRepository.findAllByMembersTeacher(teacher);
        return chatRooms.stream()
                .map(this::convertToChatRoomDto)
                .collect(Collectors.toList());
    }

    /*
      ChatRoom 엔티티를 ChatRoomDto로 변환합니다.
      @param chatRoom 변환할 ChatRoom 엔티티
      @return 변환된 ChatRoomDto
     */
    private ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom) {
        // 채팅방 멤버 중 선생님을 찾습니다.
        Teacher teacher = chatRoom.getMembers().stream()
                .filter(member -> member.getTeacher() != null)
                .map(ChatRoomMember::getTeacher)
                .findFirst()
                .orElse(null);

        // 채팅방 멤버 중 부모를 찾습니다.
        Parents parent = chatRoom.getMembers().stream()
                .filter(member -> member.getParent() != null)
                .map(ChatRoomMember::getParent)
                .findFirst()
                .orElse(null);

        // ChatRoomDto를 생성하여 반환합니다.
        return ChatRoomDto.builder()
                .roomId(chatRoom.getId())
                .teaId(teacher == null ? null : new TeacherDto(teacher))
                .parentsId(parent == null ? null : new ParentsDto(parent))
                .build();
    }
    /*
      부모 ID로 모든 채팅방을 찾아 DTO 리스트로 반환합니다.
      @param parentsId 찾을 부모의 ID
      @return 채팅방 DTO 리스트
     */
    public List<ChatRoomDto> findAllByMembersParent(Long parentsId) {
        // 부모를 찾습니다.
        Parents parents = parentRepository.findById(parentsId)
                .orElseThrow(() -> new EntityNotFoundException("parents not found"));

        // 해당 부모가 속한 모든 채팅방을 찾습니다.
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByMembersParent(parents);

        // 찾은 채팅방들을 DTO로 변환하여 반환합니다.
        return chatRooms.stream()
                .map(this::convertToChatRoomDto)
                .collect(Collectors.toList());
    }
}