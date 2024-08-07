package com.green.fefu.online;

import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.common.model.ResultDto;
import com.green.fefu.entity.HaesolOnline;
import com.green.fefu.entity.HaesolOnlineMultiple;
import com.green.fefu.entity.Teacher;
import com.green.fefu.entity.dummy.Subject;
import com.green.fefu.entity.dummy.TypeTag;
import com.green.fefu.online.model.GetKoreanAndMathQuestionReq;
import com.green.fefu.online.model.PostOnlineQuestionReq;
import com.green.fefu.online.repository.HaesolOnlineMultipleRepository;
import com.green.fefu.online.repository.HaesolOnlineRepository;
import com.green.fefu.online.repository.TypeTagRepository;
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
public class HaesolOnlineServiceImpl {
    private final HaesolOnlineRepository HaesolOnlineRepository;
    private final HaesolOnlineMultipleRepository HaesolOnlineMultipleRepository;
    private final TypeTagRepository typeTagRepository;
    private final SubjectRepository subjectRepository;

    private final AuthenticationFacade authenticationFacade;
    private final TeacherRepository teacherRepository;
    private final CustomFileUtils customFileUtils;

    private final OnlineMapper mapper;

    @Transactional
    public int PostKorAMatQuestion(MultipartFile pic, PostOnlineQuestionReq p) {
        log.info("데이터 객체 : {}", p);
        //setter에 들어갈 Entity + fileName제작
        Teacher teacher = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        Subject subject = subjectRepository.getReferenceById(p.getSubjectCode());
        TypeTag typeTag = typeTagRepository.findByTypeNumAndSubject_SubjectId(p.getSubjectCode(), p.getTypeTag());
        String picString = customFileUtils.makeRandomFileName(pic);

        // entity 생성 + 파일 이름
        HaesolOnline haesolOnline = new HaesolOnline();

        haesolOnline.setTeaId(teacher); //선생님 PK->아래에서 학반 정보
        haesolOnline.setClassId(mapper.teacherClass(teacher.getTeaId())); //선생님 정보 토대로 학년 정보
        haesolOnline.setSubjectCode(subject); //과목코드
        haesolOnline.setTypeTag(typeTag); //객관식-주관식
        haesolOnline.setLevel(p.getLevel()); //난이도
        haesolOnline.setQuestion(p.getQuestion()); //문제
        haesolOnline.setContents(p.getContents()); //내용
        haesolOnline.setAnswer(p.getAnswer()); //보기 및 단답형 정답
        haesolOnline.setQueTag(p.getQueTag()); //객관식 주관식 구분
        haesolOnline.setPic(picString); // 사진 저장
        haesolOnline.setCreatedAt(LocalDateTime.now()); //생성일자(상속)
        // 문제 Entity에 저장
        HaesolOnlineRepository.save(haesolOnline);

        // 사진을 폴더에 저장
        if (pic != null || !pic.isEmpty()) {
            try {
                String path = String.format("korean/%s", haesolOnline.getQueId());
                customFileUtils.makeFolders(path);
                String target = String.format("%s/%s", path, picString);
                customFileUtils.transferTo(pic, target);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("시험문제 업로드에 실패했습니다.");
            }
        }

        // 문제 유형이 객관식이라면 보기를 Entity에 저장
        if (p.getQueTag() == 1) {
            List<String> koreanMultipleList = p.getMultiple();
            log.info("multiple req : {}", p.getMultiple());
            int num=1;
            List<HaesolOnlineMultiple> multipleList=new ArrayList<>();
            for(String mul:koreanMultipleList) {
                HaesolOnlineMultiple entKoreanMultiple = new HaesolOnlineMultiple();
                entKoreanMultiple.setHaesolOnline(haesolOnline);
                entKoreanMultiple.setNum(num);
                log.info("num:{} : string : {}", num, mul);
                entKoreanMultiple.setSentence(mul);
                num++;
                multipleList.add(entKoreanMultiple);
            }
            HaesolOnlineMultipleRepository.saveAll(multipleList);
        }

        return 1;
    }

    public ResultDto<List<GetKoreanAndMathQuestionReq>> GetKorAMatQuestion(GetKoreanAndMathQuestionReq p){
        return null;
    }
}