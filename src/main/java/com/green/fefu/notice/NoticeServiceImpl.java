package com.green.fefu.notice;

import com.green.fefu.exception.CustomException;
import com.green.fefu.exception.OutOfRangeErrorCode;
import com.green.fefu.notice.model.*;
import com.green.fefu.security.AuthenticationFacade;
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
    private final AuthenticationFacade authenticationFacade; //PK값을 제공(getLoginUserId();
    //private final AppProperties appProperties;

    public int postNotice(PostNoticeReq p){
        p.setTeaId(authenticationFacade.getLoginUserId());
        p.setClassId(mapper.teacherHomeroom(p.getTeaId()));
        log.info("{}", p);
        if(!(p.getState()==1) && !(p.getState()==2)){
            throw new CustomException(OutOfRangeErrorCode.NOTICE_STATE_CHECK);
        }
        log.info("service : {}",p);
        //로그인 안 된 사람 처리
        return mapper.postNotice(p);
    }
    //학부모냐 선생님이냐 따라 갈림
    public List<GetNoticeRes> getNotice(GetNoticeReq p){
        long userId=authenticationFacade.getLoginUserId();

        if(p.getState()>1){
            return mapper.getNotice_teacher(p);
        }else{
            return mapper.getNotice_parent(p);
        }

    }


    public int putNotice(PutNoticeReq p){ //구현 예정
        p.setTeaId(authenticationFacade.getLoginUserId());
        return mapper.putNotice(p);
    }



    public int deleteNotice(DeleteNoticeReq p){
        p.setTeaId(authenticationFacade.getLoginUserId());
        //p.setClassId(mapper.teacherHomeroom(p.getTeaId()));
        return mapper.deleteNotice(p);
    }

}
