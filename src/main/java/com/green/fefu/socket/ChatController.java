package com.green.fefu.socket;



import com.green.fefu.entity.ChatRoomId;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import com.green.fefu.parents.ParentsUserServiceImpl;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.socket.model.ChatMsgDto;
import com.green.fefu.socket.model.ChatRoomDto;
import com.green.fefu.socket.repository.ChatRoomIdRepository;
import com.green.fefu.teacher.repository.TeacherRepository;
import com.green.fefu.teacher.service.TeacherServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor

public class ChatController {
    private final TeacherServiceImpl teacherService;
    private final ChatService chatService;
    private final ParentsUserServiceImpl parentsUserService;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticationFacade authenticationFacade;

    private final SimpMessagingTemplate template;


    @PostMapping(value = "parents/{teaId}/chat")
    @Operation(summary = "채팅방 만들기", description = "학부모 로그인 -> 선생님 Pk값 받고 같이 리턴 ")
    public String teacherCreateRoom(@PathVariable("teaId")Long teaId, @AuthenticationPrincipal UserDetails userDetails){
        Parents parentsId = parentRepository.getReferenceById(authenticationFacade.getLoginUserId());
        parentsId.getName();

        Teacher teacher = teacherRepository.getReferenceById(teaId);
        ChatRoomId roomId = chatService.createRoom(parentsId, teacher);
        log.info("Create Chat Room , senderNick"+teacher.getName()+"학부모님 입장했습니다");
        log.info("Create Chat Room , senderNick"+parentsId.getName()+"선생님이 입장했습니다");
        return "chatRoomId : "+roomId.getId();
    }
    @PostMapping(value = "teacher/chat")
    @Operation(summary = "채팅방 만들기", description = "학부모 로그인 -> 선생님 PK값 받고 같이 리턴 ")
    public ResponseEntity<String> parentCreateRoom(
            @RequestBody List<Long> parentsId,
            @AuthenticationPrincipal UserDetails userDetails) {

        // 현재 로그인한 선생님의 ID를 가져옵니다.
        Teacher teaId = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        String teacherName = teaId.getName();

        // 최초의 부모 ID를 사용하여 채팅방을 생성합니다. (여기서는 첫 번째 부모 ID 사용)
        Parents firstParent = parentRepository.getReferenceById(parentsId.get(0)); // 첫 번째 부모 객체 가져오기
        ChatRoomId roomId = chatService.createRoom(firstParent, teaId); // 첫 번째 학부모와 선생님으로 방 생성

        // 모든 학부모에 대해 로그 기록
        for (int i = 0; i<parentsId.size(); i++) {
            Parents parents = parentRepository.getReferenceById(parentsId.get(i));
            String roomLink = "/parentsId/" + parentsId.get(i) + "/chat/" + roomId; // 첫 번째 부모 ID 사용
            log.info("Create Chat Room, senderNick: {} 학부모님이 입장했습니다", parents.getName());
            return ResponseEntity.ok(roomLink); // 생성된 링크 반환
        }
        log.info("Create Chat Room, senderNick: {} 선생님이 입장했습니다", teacherName);
        // 생성된 채팅방 링크를 만듭니다.
        return ResponseEntity.ok(teacherName);
    }
    @GetMapping(value = "teacher/{roomId}/chat")
    @Operation(summary = "선생님이 특정 학부모 채팅 가져오기 ")
    public String findRoomId(@PathVariable("roomId")String roomId,@PathVariable Long parentsPk, Model model){
        log.info("GET Chat Room , roomId : " + roomId);
        Teacher teacher = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        List<ChatRoomDto> chatRoomDto = chatService.findRoomId(parentRepository.getReferenceById(parentsPk), teacher);
        model.addAttribute("chatRoomDto", chatRoomDto);
        model.addAttribute("teachers", teacher);
        log.info("GET Chat Room , roomId : " + roomId);
        return "chat";
    }
    @GetMapping(value = "parentId/{roomId}/chat")
    @Operation(summary = "학부모가  특정  선생님 채팅 가져오기 ")
    public String findRoomId(@PathVariable("roomId")String roomId, Model model){
        log.info("GET Chat Room , roomId : " + roomId);
        ChatRoomDto chatRoomDto = chatService.findRoom(Long.parseLong(roomId));
        Parents parents = parentRepository.getReferenceById(authenticationFacade.getLoginUserId());
        model.addAttribute("chatRoomDto", chatRoomDto);
        model.addAttribute("parents", parents);

        return "chat";
    }

    @GetMapping(value = "teacher/chats")
    @Operation(summary = "로그인한 선생님 전체 채팅 리스트 조회")
    public String getAllTeacherChats(Model model) {
        Long teacherId = authenticationFacade.getLoginUserId();
        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        List<ChatRoomDto> chatRooms = chatService.findAllRoomsByTeacher(teacherId);

        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("teacher", teacher);
        return "chatList";
    }

    @GetMapping(value = "parents/chats")
    @Operation(summary = "로그인한 학부모 전체 채팅 리스트 조회")
    public String getAllParentsChats(Model model) {
        Long parentsId = authenticationFacade.getLoginUserId();
        Parents parents = parentRepository.getReferenceById(parentsId);
        List<ChatRoomDto> chatRooms = chatService.findAllRoomsByTeacher(parentsId);
        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("teacher", parents);
        return "chatList";
    }
    @MessageMapping(value = "/item/{itemId}/chat/enter")
    @Operation(summary = "채팅생성시 자동적으로 보내주는 알람 ")
    public void enter(ChatMsgDto message) {
        message.setMsg(message.getSender() + "님이 채팅방에 참여하셨습니다.");
        template.convertAndSend("/sub/item/"+ message.getItemId() + "/chat/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/item/{itemId}/chat/message")
    @Operation(summary = "채팅 보내기 ")
    public void message(ChatMsgDto message) {
        chatService.saveChat(message);
        template.convertAndSend("/sub/item/" + message.getItemId() + "/chat/" + message.getRoomId(), message);
    }
}
