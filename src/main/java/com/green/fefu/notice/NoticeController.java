package com.green.fefu.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notice")
public class NoticeController {
    private final NoticeService service;

    @PostMapping("")
    public int postNotice(){
        return 1;
    }

    @GetMapping("")
    public int getNotice(){
        return 1;
    }

    @PutMapping("")
    public int putNotice(){
        return 1;
    }

    @DeleteMapping("")
    public int deleteNotice(){
        return 1;
    }
}
