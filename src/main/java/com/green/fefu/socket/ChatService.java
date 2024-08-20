package com.green.fefu.socket;

import com.green.fefu.entity.*;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import com.green.fefu.socket.model.*;
import com.green.fefu.socket.repository.ChatMsgRepository;
import com.green.fefu.socket.repository.ChatRoomMemberRepository;
import com.green.fefu.socket.repository.ChatRoomRepository;
import com.green.fefu.socket.repository.ChatRoomRepositoryCustomImpl;
import com.green.fefu.teacher.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.forEach;

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

    private final AuthenticationFacade authenticationFacade;

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

        // 현재 로그인한 사용자 정보 가져오기
        MyUser myUser = authenticationFacade.getLoginUser();
        User loginUser = getCurrentUser(myUser);

        // 채팅 메시지 가져오기 및 시간순 정렬
        List<ChatMsg> messages = chatMsgRepository.findByChatRoomOrderBySendTimeAsc(chatRoom);
        List<ChatMsgDto> messageDtos = messages.stream()
                .map(this::convertToChatMsgDto)
                .sorted(Comparator.comparing(ChatMsgDto::getSendTime))
                .collect(Collectors.toList());

        // 기본 ChatRoomDto 생성
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setRoomId(chatRoom.getId());
        chatRoomDto.setMessages(messageDtos);
        chatRoomDto.setLoginUserName(loginUser.getName());

        // Teacher 정보 설정
        chatRoom.getMembers().stream()
                .filter(member -> member.getTeacher() != null)
                .findFirst()
                .ifPresent(member -> chatRoomDto.setTeaId(new TeacherDto(member.getTeacher())));

        // Parent 정보 설정
        chatRoom.getMembers().stream()
                .filter(member -> member.getParent() != null)
                .findFirst()
                .ifPresent(member -> chatRoomDto.setParentsId(new ParentsDto(member.getParent())));

        // DTO 리스트에 추가
        dtos.add(chatRoomDto);

        System.out.println("체크포인트");
        return dtos;
    }

    private ChatMsgDto convertToChatMsgDto(ChatMsg chatMsg) {
        SenderDto senderDto = new SenderDto(); // SenderDto 클래스를 별도로 정의해야 합니다.
        // 필요한 경우 sender의 다른 정보도 설정
        return new ChatMsgDto(
                chatMsg.getMessage(),
                chatMsg.getChatRoom().getId(),
                chatMsg.getSender(),
                chatMsg.getCreatedAt()
        );
    }

    public void saveChat(ChatMsgDto chatMsgDto) {
        // 채팅방을 찾습니다.
        ChatRoom chatRoom = chatRoomRepository.findById(chatMsgDto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다."));

        MyUser myUser = authenticationFacade.getLoginUser();
        User user = getCurrentUser(myUser);
        String name = user.getName();

        // 새로운 채팅 메시지를 생성하고 설정합니다.
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setChatRoom(chatRoom);
        chatMsg.setSender(name);
        chatMsg.setMessage(chatMsgDto.getMsg());

        // 채팅 메시지를 저장합니다.
        chatMsgRepository.save(chatMsg);
    }

    private User getCurrentUser(MyUser myUser) {
        if (myUser.getRole().equals("ROLE_TEACHER")) {
            return teacherRepository.findById(myUser.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("선생님을 찾을 수 없습니다."));
        } else if (myUser.getRole().equals("ROLE_PARENTS")) {
            return parentRepository.findById(myUser.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("학부모를 찾을 수 없습니다."));
        } else {
            throw new IllegalStateException("잘못된 사용자 역할입니다.");
        }
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
    public List<GetMemberChat> findAllTeacher(Long teaId) {
        Teacher teacher = teacherRepository.findById(teaId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        List<ChatRoom> chatRooms = customRepository.findAllByMembersTeacher(teacher);
        return convertToChatRoomDtoTeacher(chatRooms, teacher);
    }

    private List<GetMemberChat> convertToChatRoomDtoTeacher(List<ChatRoom> chatRooms, Teacher currentTeacher) {
        List<GetMemberChat> result = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            GetMemberChat roomDto = new GetMemberChat();
            roomDto.setRoomId(chatRoom.getId());

            // 선생님 정보 설정
            roomDto.setTeaId(new TeacherDto(currentTeacher));

            // 부모 정보 설정
            List<ParentsDto> parentsList = new ArrayList<>();
            for (ChatRoomMember member : chatRoom.getMembers()) {
                if (member.getParent() != null) {
                    parentsList.add(new ParentsDto(member.getParent()));
                }
            }
            roomDto.setParents(parentsList);

            result.add(roomDto);
        }

        return result;
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

    public List<GetMemberChat> findAllByMembersParent(Long parentsId) {
        Parents parents = parentRepository.findById(parentsId)
                .orElseThrow(() -> new EntityNotFoundException("Parents not found"));
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByMembersParent(parents);
        return convertToChatRoomDto(chatRooms, parents);
    }

    private List<GetMemberChat> convertToChatRoomDto(List<ChatRoom> chatRooms, Parents currentParent) {
        List<GetMemberChat> result = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            GetMemberChat roomDto = new GetMemberChat();
            roomDto.setRoomId(chatRoom.getId());

            // 선생님 정보 설정
            chatRoom.getMembers().stream()
                    .filter(member -> member.getTeacher() != null)
                    .findFirst()
                    .ifPresent(member -> {
                        TeacherDto teacherDto = new TeacherDto(member.getTeacher());
                        roomDto.setTeaId(teacherDto);
                    });

            // 부모 정보 설정 (현재 부모 제외)
            List<ParentsDto> parentsDtos = chatRoom.getMembers().stream()
                    .filter(member -> member.getParent() != null && !member.getParent().equals(currentParent))
                    .map(member -> new ParentsDto(member.getParent()))
                    .collect(Collectors.toList());

            // GetMemberChat 객체에 부모 리스트 설정
            roomDto.setParents(parentsDtos);

            result.add(roomDto);
        }

        return result;
    }

    public Long findAllByMembersParent(Parents parent, Teacher teacher) {
        return chatRoomRepository.findByMembersParentAndMembersTeacher(parent, teacher)
                .map(ChatRoom::getId)
                .orElse(null);
    }
}