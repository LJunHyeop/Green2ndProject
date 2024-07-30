package com.green.fefu.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService service;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name){
        return service.createRoom(name);
    }
    @GetMapping
    public List<ChatRoom> getRooms(){
        return service.findALlRooms();
    }
}
