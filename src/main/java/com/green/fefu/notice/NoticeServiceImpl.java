package com.green.fefu.notice;

import com.green.fefu.exception.CustomException;
import com.green.fefu.exception.CustomErrorCode;
import com.green.fefu.notice.model.*;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
    private final NoticeMapper mapper;
    private final AuthenticationFacade authenticationFacade; //PK값을 제공(getLoginUserId();

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
    public Map<String, List<GetNoticeRes>> getNotice(GetNoticeReq p){
        MyUser user=authenticationFacade.getLoginUser();
        log.info("pk : {}", authenticationFacade.getLoginUser().getRole());
        String userRole=user.getRole();
        if(userRole.equals("ROLE_TEAHCER")){
            long teaId=authenticationFacade.getLoginUserId();
            int classId=mapper.teacherHomeroom(teaId);
            p.setClassId(classId);
            List<GetNoticeRes> allList=mapper.getNotice(p);
            return noticeType(allList);
        }
        long parentsId=authenticationFacade.getLoginUserId();
        int classId=mapper.childClassRoomList(parentsId, p.getStudentPk());
        p.setClassId(classId);
        List<GetNoticeRes> allList = mapper.getNotice(p);
        return noticeType(allList);
    }
    //최신의 알림장 정보 1개 조회
    public Map<String, GetNoticeRes> getNoticeLatest(GetNoticeReq p){
        MyUser user=authenticationFacade.getLoginUser();
        String userRole=user.getRole();
        if(userRole.equals("ROLE_TEAHCER")){
            long teaId=authenticationFacade.getLoginUserId();
            int classId=mapper.teacherHomeroom(teaId);
            p.setClassId(classId);
            List<GetNoticeRes> allList=mapper.getNoticeLatest(p);
            return noticeTypeOne(allList);
        }
        long parentsId=authenticationFacade.getLoginUserId();
        int classId=mapper.childClassRoomList(parentsId, p.getStudentPk());
        p.setClassId(classId);
        List<GetNoticeRes> allList=mapper.getNoticeLatest(p);
        return noticeTypeOne(allList);
    }

    Map<String, List<GetNoticeRes>> noticeType(List<GetNoticeRes> allList){
        List<GetNoticeRes> state1=new ArrayList();
        List<GetNoticeRes> state2=new ArrayList();

        for(GetNoticeRes res:allList){
            if(res.getState()==1){
                state1.add(res);
            }else{
                state2.add(res);
            }
        }
        Map<String, List<GetNoticeRes>> map=new LinkedHashMap();
        map.put("알림장", state1);
        map.put("준비물", state2);
        return map;
    }

    Map<String, GetNoticeRes> noticeTypeOne(List<GetNoticeRes> allList){
        GetNoticeRes state1=null;
        GetNoticeRes state2=null;
        for(GetNoticeRes res:allList) {
            if (state1 == null && res.getState() == 1) {
                state1 = res;
            }
            if(state2 == null && res.getState() == 2){
                state2 = res;
            }//준비물을 못 담아오는데 이유를 모르겠다!!~!!!!!@!!!!!!
        }
        Map<String, GetNoticeRes> map=new LinkedHashMap();
        map.put("알림장", state1);
        map.put("준비물", state2);
        return map;
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
