package com.green.fefu.socket.model;

import com.green.fefu.security.AuthenticationFacade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Getter
@Setter
public class SenderDto {
    private final AuthenticationFacade authenticationFacade;

    private TeacherDto teacherDto;

    private ParentsDto parentsDto;

}
