package com.green.fefu.notice;

import com.green.fefu.exception.CustomException;
import com.green.fefu.exception.CustomErrorCode;
import com.green.fefu.notice.model.*;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
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

    /*알림장 등록 : 권한있는 사람만 등록 가능*/
    public int postNotice(PostNoticeReq p){
        p.setTeaId(authenticationFacade.getLoginUserId());
        MyUser myUser=authenticationFacade.getLoginUser();
        log.info("{}", myUser.getRole());
//        if(!myUser.getRole().equals("TEAHCER")){
//            throw new CustomException(CustomErrorCode.YOU_ARE_NOT_TEACHER);
//        }
        p.setClassId(mapper.teacherHomeroom(p.getTeaId()));
        log.info("{}", p);
        if(!(p.getState()==1) && !(p.getState()==2)){
            throw new CustomException(CustomErrorCode.NOTICE_STATE_CHECK);
        }
        log.info("service : {}",p);
        //로그인 안 된 사람 처리
        return mapper.postNotice(p);
    }

    //학부모냐 선생님이냐 따라 갈림
    public List<GetNoticeRes> getNotice(GetNoticeReq p){
        MyUser user=authenticationFacade.getLoginUser();
        String userRole=user.getRole();
        if(userRole.equals("TEAHCER")){ //선생님 PK
            long classId=mapper.teacherHomeroom(authenticationFacade.getLoginUserId());
            p.setClassId(classId);
            return mapper.getNotice(p);
        }else{
            long classId=mapper.teacherHomeroom(authenticationFacade.getLoginUserId());
            p.setClassId(classId);
            return mapper.getNotice(p);
        }

    }


    public int putNotice(PutNoticeReq p){ //구현 예정
        p.setTeaId(authenticationFacade.getLoginUserId());
        p.setTeaId(authenticationFacade.getLoginUserId());
        return mapper.putNotice(p);
    }



    public int deleteNotice(DeleteNoticeReq p){
        p.setTeaId(authenticationFacade.getLoginUserId());
        p.setClassId(mapper.teacherHomeroom(p.getTeaId()));
        return mapper.deleteNotice(p);
    }

}
