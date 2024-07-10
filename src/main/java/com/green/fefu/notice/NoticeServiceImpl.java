package com.green.fefu.notice;

import com.green.fefu.exception.CustomException;
import com.green.fefu.exception.OutOfRangeErrorCode;
import com.green.fefu.notice.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if(!(p.getState()==1) && !(p.getState()==2)){
            throw new CustomException(OutOfRangeErrorCode.NOTICE_STATE_CHECK);
        }
        log.info("service : {}",p);
        //로그인 안 된 사람 처리
        return mapper.postNotice(p);
    }
    public List<GetNoticeRes> getNotice(GetNoticeReq p){
        return mapper.getNotice(p);
    }
    public int putNotice(PutNoticeReq p){ //구현 예정
        //p.setTeaId(authenticationFacade.getLoginUserId());
        return mapper.putNotice(p);
    }
    public int deleteNotice(DeleteNoticeReq p){
        //p.setTeaId(authenticationFacade.getLoginUserId());
        p.setClassId(mapper.teacherHomeroom(p.getTeaId()));
        return mapper.deleteNotice(p);
    }

}
