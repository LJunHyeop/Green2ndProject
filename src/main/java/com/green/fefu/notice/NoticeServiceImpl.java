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
        log.info("signedUserId:{}, UserRole:{}", p.getTeaId(), myUser.getRole());
    //if(!myUser.getRole().equals("TEAHCER")){
    // throw new CustomException(CustomErrorCode.YOU_ARE_NOT_TEACHER);
    //}
        p.setClassId(mapper.teacherHomeroom(p.getTeaId()));

        if(!(p.getState()==1 || p.getState()==2)){
            throw new CustomException(CustomErrorCode.NOTICE_STATE_CHECK);
        }

        //로그인 안 된 사람 처리
        return mapper.postNotice(p);
    }

    //
    public List<GetNoticeRes> getNotice(GetNoticeReq p){

        MyUser user=authenticationFacade.getLoginUser();
        log.info("pk : {}", authenticationFacade.getLoginUser().getRole());
        String userRole=user.getRole();
        if(userRole.equals("ROLE_TEAHCER")){
            long teaId=authenticationFacade.getLoginUserId();
            int classId=mapper.teacherHomeroom(teaId);
            p.setClassId(classId);
            return mapper.getNotice(p);
        }

        long parentsId=authenticationFacade.getLoginUserId();
        int classId=mapper.childClassRoom(parentsId);
        p.setClassId(classId);
        return mapper.getNotice(p);
    }
    //최신의 알림장 정보 1개 조회
    public GetNoticeRes getNoticeLatest(GetNoticeReq p){
        MyUser user=authenticationFacade.getLoginUser();
        String userRole=user.getRole();
        if(userRole.equals("ROLE_TEAHCER")){
            long teaId=authenticationFacade.getLoginUserId();
            int classId=mapper.teacherHomeroom(teaId);
            p.setClassId(classId);
            return mapper.getNoticeLatest(p);
        }
        long parentsId=authenticationFacade.getLoginUserId();
        int classId=mapper.childClassRoom(parentsId);
        p.setClassId(classId);
        return mapper.getNoticeLatest(p);
    }

    public int putNotice(PutNoticeReq p){ //구현 예정
        p.setTeaId(authenticationFacade.getLoginUserId());
        return mapper.putNotice(p);
    }




    public int deleteNotice(DeleteNoticeReq p){
        log.info("ser1: {}", p);
        p.setTeaId(authenticationFacade.getLoginUserId());
        p.setClassId(mapper.teacherHomeroom(p.getTeaId()));
        log.info("ser2: {}", p);
        return mapper.deleteNotice(p);
    }

}
