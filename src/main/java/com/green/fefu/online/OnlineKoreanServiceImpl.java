package com.green.fefu.online;

import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.entity.OnlineKorean;
import com.green.fefu.entity.OnlineKoreanMultiple;
import com.green.fefu.entity.OnlineKoreanPic;
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
import java.util.List;
import java.util.Map;

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

    @Transactional
    public int PostKoreanQuestion(MultipartFile pic, PostOnlineQuestionReq p) {
        log.info("데이터 객체 : {}", p);
        // 로그인 한 선생님 정보
        Teacher teacher = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());

        // entity 생성
        OnlineKorean entOnlineKorean = new OnlineKorean();
        OnlineKoreanPic entKoreanPic = new OnlineKoreanPic();
        OnlineKoreanMultiple entKoreanMultiple = new OnlineKoreanMultiple();

        log.info("1. 아무 것도 없는 상태 {}", entOnlineKorean);
        entOnlineKorean.setQuestion(p.getQuestion());
        entOnlineKorean.setContents(p.getContents());
        entOnlineKorean.setAnswer(p.getAnswer());
        entOnlineKorean.setTeaId(teacher);
        entOnlineKorean.setCreatedAt(LocalDateTime.now());
        log.info("2. {}", entOnlineKorean);
        onlineKoreanRepository.save(entOnlineKorean);

//        // 보기를 담기
//        List<Map<String, String>> koreanMultipleList = p.getMultiple();
//        for(Map<String, String> key:koreanMultipleList){
//            String mp=key.get("additionalProp1");
//            entKoreanMultiple.setNum(mp);
//            entKoreanMultiple.setSentence(mp);
//        }
        onlineKoreanMultipleRepository.save(entKoreanMultiple);

        if (pic == null || pic.isEmpty()) {
            try {
                // 사진 저장 및 랜덤 이름 DB에 저장
                String path = String.format("korean/%s", entOnlineKorean.getQueId());
                customFileUtils.makeFolders(path);
                String fileName = customFileUtils.makeRandomFileName(pic);
                String target = String.format("%s/%s", path, fileName);
                customFileUtils.transferTo(pic, target);
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
