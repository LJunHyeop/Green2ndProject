package com.green.fefu.online;

import com.green.fefu.entity.OnlineKorean;
import com.green.fefu.entity.Teacher;
import com.green.fefu.online.model.PostOnlineQuestionReq;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineKoreanServiceImpl {
    private final OnlineKoreanRepository onlineKoreanRepository;

    public int PostKoreanQuestion(PostOnlineQuestionReq p){
        log.info("{}", p);
        OnlineKorean onlineKorean=new OnlineKorean();
        log.info("1. {}", onlineKorean);
        onlineKorean.setQuestion(p.getQuestion());
        onlineKorean.setAnswer(p.getAnswer());
        onlineKorean.setCreatedAt(LocalDateTime.now());
        log.info("2. {}", onlineKorean);
        onlineKoreanRepository.save(onlineKorean);
        log.info("3. {}", onlineKorean);
        return 1;
    }
}
