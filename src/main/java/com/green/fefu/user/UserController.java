package com.green.fefu.user;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.user.model.SignInUser;
import com.green.fefu.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private UserService service;

    @PostMapping("")
    public ResultDto<UserInfo> getParentInfo(SignInUser p){
        UserInfo info= service.getParentInfo(p);
        return ResultDto.<UserInfo>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .result(info)
                .build();
    }

}
