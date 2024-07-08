package com.green.fefu.Exam;

import com.green.fefu.Exam.model.ExamReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamServiceImpl implements ExamService {

    private ExamMapper mapper;

    public long examIns(ExamReq p){
        return mapper.examIns(p);
    }

}
