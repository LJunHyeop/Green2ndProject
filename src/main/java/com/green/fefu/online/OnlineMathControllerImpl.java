package com.green.fefu.online;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/online/math")
@Tag(name = "온라인 학습-수학", description = "수학문제 CRUD")
public class OnlineMathControllerImpl {
    private final OnlineMathServiceImpl service;

}

