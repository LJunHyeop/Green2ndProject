package com.green.fefu.user;

import com.green.fefu.user.model.SignInUser;
import com.green.fefu.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserMapper mapper;

    public UserInfo getParentInfo(SignInUser p) {
        UserInfo info=mapper.getParentInfo(p);
        if(info==null){
            throw new RuntimeException("일치하는 회원을 찾을 수 없습니다.");
        }else if(!BCrypt.checkpw(p.getUpw(), info.getUpw())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return info;
    }
}
