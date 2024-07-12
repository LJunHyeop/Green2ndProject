package com.green.fefu.score;

import com.green.fefu.score.model.*;
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
    //점수 넣기
    public long postScore(InsScoreReq p){
        DelScore delScore = new DelScore();
        delScore.setExam(p.getExam());
        delScore.setSemester(p.getSemester());
        delScore.setName(p.getName());
        delScore.setScId(p.getStuId());
        List<InsScoreList> list = mapper.totalList(delScore) ;
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
        StuGetRes res = mapper.getStu(p.getStuId());
        //학생의 점수 PK, 학생 PK, 직전 학년, 학기, 년도
        if (p.getLatestSemester() == 0) { //학기가 0이면
            Integer latestSemester = res.getLatestSemester();
            if (latestSemester == null || latestSemester == 0) {
                p.setLatestSemester(1);
            }
            log.info("adsfasdf : {}",res.getLatestSemester());

            log.info("2: {}",res.getExam());
        }
        dto.setStuId(res.getStuId());
        dto.setLatestGrade(res.getLatestGrade());
        log.info("StuGetRes - latestGrade: {}", res.getLatestGrade());
        dto.setLatestSemester(res.getLatestSemester());
        dto.setLatestYear(res.getLatestYear());
        dto.setExam(res.getExam());


        System.out.println(dto.getList().toString());
        log.info("List : {}", dto.getList().toString());
        StuGetRes aa = mapper.getStu(p.getStuId());
        dto.setStuId(aa.getStuId());
        p.setLatestGrade(res.getLatestGrade());
        res.setExam(p.getExam());
        log.info("exam: {}", res.getExam());
        log.info("latestSemester: {}", res.getLatestSemester());

        if(res.getExam() == 1){
            List<InsScoreList> list = mapper.getScoreMidterm(res);
            dto.setList(list);
        }else if(res.getExam() == 2){
            List<InsScoreList> list1 = mapper.getScoreFinal(res);
            dto.setList(list1);
        }

        //        List <InsScoreReq> list = new ArrayList<>();
        //       if(list.size() == 0){
        //            List list1 = new ArrayList();
        //            dto.setList(list1);
        //            log.info("에러");
        //            return dto;
        //        }
        return dto;
    }


    // 디테일하게 조회 EX 학년 학기

    public  DtoDetail getDetailScore(GetDetailScoreReq p){
        StuGetRes res = mapper.getStu(p.getStuId());
        log.info("ddd: {}",p.getSemester());
        log.info("ddd: {}",p.getExam());
        DtoDetail dto = new DtoDetail();

        log.info("dto: {}", dto.getList().toString());
        if ((p.getSemester() == 1 || p.getSemester() == 2) && p.getExam() == 1 ) {
            dto.setList(mapper.getDetailScore(p));
        }else if((p.getSemester() == 1 || p.getSemester() ==2) && p.getExam() ==2 ) {
            dto.setList(mapper.getDetailScoreFinal(p));
        }
        if(res.getLatestGrade() < p.getGradle()){
            throw  new RuntimeException("잘못된 학년입니다.");
        }
        if(dto.getList() == null || dto.getList().size() == 0){
            throw new RuntimeException("조회된 성적이 없습니다");
        }
        dto.setStuId(p.getStuId());
        return dto;
    }
}
