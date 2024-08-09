package com.green.fefu.online;

import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.entity.*;
import com.green.fefu.exception.CustomException;
import com.green.fefu.online.model.GetEnglishListeningQuestionRes;
import com.green.fefu.online.model.GetEnglishWordsQuestionReq;
import com.green.fefu.online.model.PostOnlineQuestionEnglishListeningReq;
import com.green.fefu.online.model.PostOnlineQuestionEnglishWordReq;
import com.green.fefu.online.repository.OnlineEnglishListeningRepository;
import com.green.fefu.online.repository.OnlineEnglishWordRepository;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import com.green.fefu.student.repository.StudentRepository;
import com.green.fefu.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.green.fefu.exception.JSH.JshErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnlineEnglishServiceImpl {
    // 내가 저장하고 꺼내올  Entity Repository
    private final OnlineEnglishWordRepository wordRepository;
    private final OnlineEnglishListeningRepository listeningRepository;

    // 파일을 다루고, 로그인 된 사용자
    private final AuthenticationFacade authenticationFacade;
    private final CustomFileUtils customFileUtils;
    private final OnlineMapper mapper;

    // 어떠한 사용자인지 분기
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    @Transactional // word DB 업로드
    public int postEnglishWordQuestion(PostOnlineQuestionEnglishWordReq p, MultipartFile pic) {
        log.info("service - p {}", p);
        log.info("service - pic {}", pic);
        OnlineEnglishWord entEnglishWord=new OnlineEnglishWord();
        entEnglishWord.setWord(p.getWord());
        entEnglishWord.setAnswer(p.getAnswer());
        String picName = customFileUtils.makeRandomFileName(pic);
        entEnglishWord.setPic(picName);
        Teacher teacher=teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        entEnglishWord.setTeaId(teacher);
        long grade=mapper.teacherClass(teacher.getTeaId());
        entEnglishWord.setGrade(grade);
        log.info("service-entity {}", entEnglishWord);
        log.info("service-entity.getGrade {}", entEnglishWord.getGrade());
        wordRepository.save(entEnglishWord);
        log.info("service-entity {}", entEnglishWord);


        //사진 처리
        if (pic != null || pic.isEmpty()) {
            try {
                String path = String.format("onlineEngWord/%s", entEnglishWord.getWordPk());
                customFileUtils.makeFolders(path);
                String target = String.format("%s/%s", path, picName);
                customFileUtils.transferTo(pic, target);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException(CAN_T_UPROAD_QUESTION);
            }
        }

        return 1;
    }


    public int PostEnglishListeningQuestion(PostOnlineQuestionEnglishListeningReq p, MultipartFile pic){
        log.info("service - p {}", p);
        log.info("service - pic {}", pic);
        OnlineEnglishListening entEnglishListening=new OnlineEnglishListening();
        entEnglishListening.setQuestion(p.getQuestion());
        entEnglishListening.setAnswer(p.getAnswer());
        String picName=customFileUtils.makeRandomFileName(pic);
        entEnglishListening.setSentence(p.getSentence());
        entEnglishListening.setPic(picName);
        log.info("service-entity {}", entEnglishListening);
        Teacher teacher=teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        entEnglishListening.setTeaId(teacher);
        log.info("service-entity {}", entEnglishListening);
        listeningRepository.save(entEnglishListening);
        log.info("service-entity2 {}", entEnglishListening);


        //사진 처리
        if (pic != null || pic.isEmpty()) {
            try {
                String path = String.format("onlineEngLis/%s",entEnglishListening.getListeningPk());
                customFileUtils.makeFolders(path);
                String target = String.format("%s/%s", path, pic);
                customFileUtils.transferTo(pic, target);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException(CAN_T_UPROAD_QUESTION);
            }
        }
        return 1;
    }

    public List<GetEnglishListeningQuestionRes> getEnglishWords(GetEnglishWordsQuestionReq p){
        MyUser user=authenticationFacade.getLoginUser();

        Long grade = switch (user.getRole()){
            case "ROLE_PARENTS"->{
                if(p.getStudentPk() != null || p.getStudentPk()==0){
                    throw new CustomException(STUDENT_PK_NOT_FOUND_ERROR);
                }
                Parents parents=parentRepository.getReferenceById(user.getUserId());
                yield mapper.parentsClass(parents.getParentsId(), p.getStudentPk());
            }
            case "ROLE_TEACHER"-> {
                Teacher teacher = teacherRepository.getReferenceById(user.getUserId());
                yield mapper.teacherClass(teacher.getTeaId());
            }
            case "ROLE_STUDENT"->{
                Student student=studentRepository.getReferenceById(user.getUserId());
                yield mapper.studentClass(student.getStuId());
            }
            default-> throw new CustomException(CANT_ENTER);
        };

        wordRepository.getAllByGrade(grade);
        return null;
    }
}

