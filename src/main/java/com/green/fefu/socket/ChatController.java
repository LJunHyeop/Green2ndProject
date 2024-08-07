package com.green.fefu.socket;

import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import com.green.fefu.parents.ParentsUserServiceImpl;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.teacher.repository.TeacherRepository;
import com.green.fefu.teacher.service.TeacherServiceImpl;
import com.green.fefu.teacher.test.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final TeacherServiceImpl teacherService;
    private final ChatService chatService;
    private final ParentsUserServiceImpl parentsUserService;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticationFacade authenticationFacade;
//    private final SimpMessagingTemplate Template;


    @PostMapping(value = "parents/{parentsId}/chat")
    @Operation(summary = "채팅방 만들기",deprecated = true)
    public String createRoom(@PathVariable("teaId")Long teaId, @AuthenticationPrincipal UserDetails userDetails){
        Parents parentsId = parentRepository.getReferenceById(authenticationFacade.getLoginUserId());
        parentsId.getName();
        Teacher teacher = teacherRepository.getReferenceById(teaId);
        log.info("Create Chat Room , senderNick"+parentsId.getName());
        Long roomId = chatService.createRoom(parentsId, teacher);
        return "redirect:/teacher/"+teaId+"/chat/"+roomId;
    }
}
