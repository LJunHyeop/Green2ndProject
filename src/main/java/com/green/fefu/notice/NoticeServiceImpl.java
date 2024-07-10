package com.green.fefu.notice;

import com.green.fefu.common.AppProperties;
import com.green.fefu.common.CookieUtils;
import com.green.fefu.notice.model.*;
import com.green.fefu.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
    private final NoticeMapper mapper;
    //private CookieUtils cookieUtils;
    //private final AuthenticationFacade authenticationFacade; //PK값을 제공(getLoginUserId();
    //private final AppProperties appProperties;


    public int postNotice(PostNoticeReq p){
        //p.setTeaId(authenticationFacade.getLoginUserId());
        p.setClassId(mapper.teacherHomeroom(p.getTeaId()));
        log.info("service : {}",p);
        //로그인 안 된 사람 처리
        return mapper.postNotice(p);
    }
    public List<GetNoticeRes> getNotice(GetNoticeReq p){
        return mapper.getNotice(p);
    }
    public int putNotice(PutNoticeReq p){
        //p.setTeaId(authenticationFacade.getLoginUserId());
        return mapper.putNotice(p);
    }
    public int deleteNotice(DeleteNoticeReq p){
        //p.setTeaId(authenticationFacade.getLoginUserId());
        return mapper.deleteNotice(p);
    }

}
