package com.green.fefu.online;

import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.entity.OnlineKorean;
import com.green.fefu.entity.OnlineKoreanMultiple;
import com.green.fefu.entity.OnlineKoreanPics;
import com.green.fefu.entity.Teacher;
import com.green.fefu.online.model.PostOnlineQuestionReq;
import com.green.fefu.online.repository.OnlineKoreanMultipleRepository;
import com.green.fefu.online.repository.OnlineKoreanPicRepository;
import com.green.fefu.online.repository.OnlineKoreanRepository;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineKoreanServiceImpl {
    private final OnlineKoreanRepository onlineKoreanRepository;
    private final OnlineKoreanPicRepository onlineKoreanPicRepository;
    private final OnlineKoreanMultipleRepository onlineKoreanMultipleRepository;

    private final AuthenticationFacade authenticationFacade;
    private final TeacherRepository teacherRepository;
    private final CustomFileUtils customFileUtils;

    private final OnlineMapper mapper;

    @Transactional
    public int PostKoreanQuestion(MultipartFile pic, PostOnlineQuestionReq p) {
        log.info("데이터 객체 : {}", p);
        // 로그인 한 선생님 정보
        Teacher teacher = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());

        // entity 생성
        OnlineKoreanPics entKoreanPic = new OnlineKoreanPics();
        OnlineKorean entOnlineKorean = new OnlineKorean();

        entOnlineKorean.setQuestion(p.getQuestion());
        entOnlineKorean.setContents(p.getContents());
        entOnlineKorean.setAnswer(p.getAnswer().toString());
        entOnlineKorean.setLevel(p.getLevel());
        entOnlineKorean.setTeaId(teacher);
        //선생님 PK를 통해서 반 정보
        entOnlineKorean.setClassId(mapper.teacherClass(teacher.getTeaId()));
        entOnlineKorean.setCreatedAt(LocalDateTime.now());
        entOnlineKorean.setTypeTag(p.getTypeTag());
        // 문제 Entity에 저장
        onlineKoreanRepository.save(entOnlineKorean);


        // 보기를 담기
        List<String> koreanMultipleList = p.getMultiple();
        log.info("multiple req : {}", p.getMultiple());
        int num=1;
        List<OnlineKoreanMultiple> multipleList=new ArrayList<>();
        for(String mul:koreanMultipleList) {
            OnlineKoreanMultiple entKoreanMultiple = new OnlineKoreanMultiple();
            entKoreanMultiple.setOnlineKorean(entOnlineKorean);
            entKoreanMultiple.setNum(num);
            log.info("num:{} : string : {}", num, mul);
            entKoreanMultiple.setSentence(mul);
            num++;
            multipleList.add(entKoreanMultiple);
        }// 보기 Entity에 저장
        onlineKoreanMultipleRepository.saveAll(multipleList);


        if (pic != null || !pic.isEmpty()) {
            try {// 사진 저장 및 랜덤 이름 DB에 저장
                String path = String.format("korean/%s", entOnlineKorean.getQueId());
                customFileUtils.makeFolders(path);
                String fileName = customFileUtils.makeRandomFileName(pic);
                String target = String.format("%s/%s", path, fileName);
                customFileUtils.transferTo(pic, target);
                entKoreanPic.setOnlineKorean(entOnlineKorean);
                entKoreanPic.setPic(fileName);
                entKoreanPic.setOnlineKorean(entOnlineKorean);
                onlineKoreanPicRepository.save(entKoreanPic);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("시험문제 업로드에 실패했습니다.");
            }
        }
        return 1;
    }
}
