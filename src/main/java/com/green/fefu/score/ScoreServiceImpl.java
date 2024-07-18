package com.green.fefu.score;

import com.green.fefu.score.model.*;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import com.green.fefu.student.model.dto.getDetail;
import com.green.fefu.student.model.dto.getStudent;
import com.green.fefu.student.service.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceImpl {
    private final ScoreMapper mapper;
    private final AuthenticationFacade authenticationFacade;
    private final StudentMapper studentMapper;


    //점수 넣기
    public long postScore(InsScoreReq p){
        MyUser user = authenticationFacade.getLoginUser();
        String userRole = user.getRole();
        DelScore delScore = new DelScore();
        delScore.setExam(p.getExam());
        delScore.setSemester(p.getSemester());
        delScore.setName(p.getName());
        delScore.setScId(p.getStudentPk());
        if(!userRole.equals("ROLE_TEAHCER")){
            throw new RuntimeException("권한이 없습니다");
        }
        getDetail list3 = studentMapper.getStudentDetail(p.getStudentPk()) ;

        GetClassIdRes res = mapper.getClassId(user.getUserId(), p.getStudentPk());

        if(!list3.getUClass().equals(res.getClassId())){
            throw new RuntimeException("담당 학생이 아닙니다");
        }



        List<InsScoreList> list = mapper.totalList(delScore);
        for(InsScoreList afterList : list){
            if (afterList != null){
                mapper.delScore(delScore);
            }
        }
        return mapper.postScore(p);
    }
    //점수 받아오기
    public Dto getScore(StuGetRes p){
        //학생, 학년, 년도, 학기, 과목(이름), 점수
        Dto dto = new Dto();
        StuGetRes res = mapper.getStu(p.getStudentPk());
        //학생의 점수 PK, 학생 PK, 직전 학년, 학기, 년도
        if (p.getLatestSemester() == 0) { //학기가 0이면
            Integer latestSemester = res.getLatestSemester();
            if (latestSemester == null || latestSemester == 0) {
                p.setLatestSemester(1);
            }
        }

        RankReq rank = new RankReq();

        rank.setStudentPk(res.getStudentPk());

        rank.setExam(p.getExam());

        rank.setGrade(res.getLatestGrade());

        rank.setSemester(res.getLatestSemester());

        log.info("resExam : {}", p.getExam());
        log.info("resSemester : {}", res.getLatestSemester());

        dto.setStudentPk(res.getStudentPk());

        dto.setLatestGrade(res.getLatestGrade());

        dto.setLatestSemester(res.getLatestSemester());

        dto.setLatestYear(res.getLatestYear());

        System.out.println(dto.getList().toString());

        p.setLatestGrade(res.getLatestGrade());

        res.setExam(p.getExam());

        log.info("resFinal: {}",mapper.rankListFinal(rank));

        if((res.getLatestSemester() == 1 || res.getLatestSemester() == 2)&& res.getExam() == 1){
            List<InsScoreList> list = mapper.getScoreMidterm(res);
            RankRes resMid = mapper.rankListMid(rank);
            dto.setClassRank(resMid);
            dto.setList(list);
//
        }else if((res.getLatestSemester() == 1 || res.getLatestSemester() == 2)&&res.getExam() == 2){
            List<InsScoreList> list1 = mapper.getScoreFinal(res);
            RankRes resFinal = mapper.rankListFinal(rank);
            dto.setClassRank(resFinal);
            dto.setList(list1);

        }else{
            return null;
        }
        return dto;
    }

    // 디테일하게 조회 EX 학년 학기
    public  DtoDetail getDetailScore(GetDetailScoreReq p){
        RankReq rank = new RankReq();
        StuGetRes res = mapper.getStu(p.getStudentPk());

        DtoDetail dto = new DtoDetail();


        rank.setStudentPk(p.getStudentPk());
        rank.setGrade(p.getGrade());
        rank.setExam(p.getExam());
        rank.setSemester(p.getSemester());

        log.info("studentPk: {}", rank.getStudentPk());
        log.info("semester: {}", rank.getSemester());
        log.info("grade: {}", rank.getGrade());
        log.info("exam: {}", rank.getExam());

        if ((p.getSemester() == 1 || p.getSemester() == 2) && p.getExam() == 1 ) {
//            dto.setClassRank(mapper.rankListMid(rank.getSemester()));
//            dto.setClassRank(mapper.rankListMid(rank.getExam()));
//            dto.setClassRank(mapper.rankListMid(rank.getGrade()));

            dto.setList(mapper.getDetailScore(p));

            dto.setClassRank(mapper.rankListMid(rank));

        }else if((p.getSemester() == 1 || p.getSemester() ==2) && p.getExam() ==2 ) {
            dto.setClassRank(mapper.rankListFinal(rank));

            dto.setList(mapper.getDetailScoreFinal(p));
        }
        if(res.getLatestGrade() < p.getGrade()){
            throw  new RuntimeException("잘못된 학년입니다.");
        }
        if(dto.getList() == null || dto.getList().size() == 0){
            throw new RuntimeException("조회된 성적이 없습니다");
        }
        dto.setStudentPk(p.getStudentPk());
        return dto;
    }
}
