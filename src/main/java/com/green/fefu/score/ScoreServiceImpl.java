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
        return mapper.postScore(p);
    }
    //점수 받아오기
    public Dto getScore(InsScoreReq p){
        //학생, 학년, 년도, 학기, 과목(이름), 점수
        Dto dto = new Dto();
        StuGetRes res = mapper.getStu(p.getStuId());
        //학생의 점수 PK, 학생 PK, 직전 학년, 학기, 년도

        if (p.getSemester() == 0) { //학기가 0이면
            Integer latestSemester = res.getLatestSemester();
            if (latestSemester == null || latestSemester == 0) {
                p.setSemester(1);
            } else {
                p.setSemester(latestSemester);
            }
        }
        dto.setStuId(p.getStuId());
        if(res.getLatestGrade() < p.getGradle()){
            throw  new RuntimeException("잘못된 학년입니다.");
        }
        System.out.println(dto.getList().toString());
        log.info("List : {}", dto.getList().toString());
        StuGetRes aa = mapper.getStu(p.getStuId());
        dto.setStuId(aa.getStuId());
        List<InsScoreList> list = mapper.getScore(p);
        dto.setList(list);
//        List <InsScoreReq> list = new ArrayList<>();
//       if(list.size() == 0){
//            List list1 = new ArrayList();
//            dto.setList(list1);
//            log.info("에러");
//            return dto;
//        }
        return dto;
    }


}
