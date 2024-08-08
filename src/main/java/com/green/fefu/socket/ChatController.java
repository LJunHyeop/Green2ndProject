package com.green.fefu.socket;



import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import com.green.fefu.parents.ParentsUserServiceImpl;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.socket.model.ChatMsgDto;
import com.green.fefu.socket.model.ChatRoomDto;
import com.green.fefu.teacher.repository.TeacherRepository;
import com.green.fefu.teacher.service.TeacherServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        Long roomId = chatService.createRoom(parentsId, teacher);

        log.info("Create Chat Room , senderNick"+teacher.getName()+"학부모님 입장했습니다");

        log.info("Create Chat Room , senderNick"+parentsId.getName()+"선생님이 입장했습니다");

        return "redirect:/teacher/"+teaId+"/chat/"+roomId;
    }
    @PostMapping(value = "teacher/{parentId}/chat")
    @Operation(summary = "채팅방 만들기", description = "학부모 로그인 -> 선생님 Pk값 받고 같이 리턴 ")
    public String parentCreateRoom(@PathVariable("parentId")Long parentsId, @AuthenticationPrincipal UserDetails userDetails){
        Teacher teaId = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        teaId.getName();
        Parents parents = parentRepository.getReferenceById(parentsId);
        Long roomId = chatService.createRoom(parents,teaId);
        log.info("Create Chat Room , senderNick"+parents.getName()+"학부모님 입장했습니다");
        log.info("Create Chat Room , senderNick"+teaId.getName()+"선생님이 입장했습니다");
        return "redirect:/parentsId/"+parentsId+"/chat/"+roomId;
    }
    @GetMapping(value = "teacher/{parentId}/chat")
    @Operation(summary = "선생님이 특정 학부모 채팅 가져오기 ")
    public String chatGetTeacher(@PathVariable("roomId")String roomId,@PathVariable UserDetails userDetails, Model model){
        log.info("GET Chat Room , roomId : " + roomId);
        ChatRoomDto chatRoomDto = chatService.findRoom(Long.parseLong(roomId));
        Teacher teacher = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        model.addAttribute("chatRoomDto", chatRoomDto);
        model.addAttribute("teachers", teacher);

        return "chat";
    }
    @GetMapping(value = "parentId/{teacher}/chat")
    @Operation(summary = "학부모가  특정  선생님 채팅 가져오기 ")
    public String chatGetParent(@PathVariable("roomId")String roomId,@PathVariable UserDetails userDetails, Model model){
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
    public void enter(ChatMsgDto message) {
        message.setMsg(message.getSender() + "님이 채팅방에 참여하셨습니다.");
        template.convertAndSend("/sub/item/"+ message.getItemId() + "/chat/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/item/{itemId}/chat/message")
    public void message(ChatMsgDto message) {
        chatService.saveChat(message);
        template.convertAndSend("/sub/item/" + message.getItemId() + "/chat/" + message.getRoomId(), message);
    }
}
