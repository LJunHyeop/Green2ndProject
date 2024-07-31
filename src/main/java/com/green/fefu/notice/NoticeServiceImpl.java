package com.green.fefu.notice;

import com.green.fefu.entity.Notice;
import com.green.fefu.entity.Teacher;
import com.green.fefu.exception.CustomException;
import com.green.fefu.exception.CustomErrorCode;
import com.green.fefu.notice.model.*;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
    private final NoticeMapper mapper;
    private final AuthenticationFacade authenticationFacade; //PK값을 제공(getLoginUserId();
    private final NoticeRepository repository;
    //private final TeacherReposotory teacherReposotory;

    /*알림장 등록 : 권한있는 사람만 등록 가능*/
    public int postNotice(PostNoticeReq p){
        //Teacher teacher = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        Notice notice=new Notice();
        //notice.setTeacher(teacher);
        notice.setTitle(p.getTitle());
        notice.setContent(p.getContent());
        //notice.setClassId(p.getClassId());
        notice.setState(p.getState());
        notice.setCreatedAt(now());

        Notice notice2=repository.save(notice);


        p.setTeaId(authenticationFacade.getLoginUserId());
        MyUser myUser=authenticationFacade.getLoginUser();

        //if(!myUser.getRole().equals("TEACHER")){
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
        if(userRole.equals("ROLE_TEACHER")){
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
        if(userRole.equals("ROLE_TEACHER")){
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
        map.put("notice", state1);
        map.put("item", state2);
        return map;
    }

    Map<String, GetNoticeRes> noticeTypeOne(List<GetNoticeRes> allList){
        GetNoticeRes state1=null;
        GetNoticeRes state2=null;
        for(GetNoticeRes res:allList) {
            log.info("state 값 조회 for문 시작 : {}", res.getState());
            if (state1 == null && res.getState() == 1) {
                state1 = res;
                log.info("state1 : {}", state1);
            }
            log.info("state2-1 : {}", state2);
            log.info("state 값 조회 : {}", res.getState());
            if(state2 == null && res.getState() == 2){
                state2 = res;
                log.info("state2-2 : {}", state2);
            }
            log.info("state2-3 : {}", state2);
        }
        Map<String, GetNoticeRes> map=new LinkedHashMap();
        map.put("notice", state1);
        map.put("item", state2);
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
