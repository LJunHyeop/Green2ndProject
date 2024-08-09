package com.green.fefu.online;

import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.entity.*;
import com.green.fefu.entity.dummy.Subject;
import com.green.fefu.entity.dummy.TypeTag;
import com.green.fefu.exception.CustomException;
import com.green.fefu.online.model.GetKoreanAndMathQuestionReq;
import com.green.fefu.online.model.GetKoreanAndMathQuestionRes;
import com.green.fefu.online.model.PostOnlineQuestionReq;
import com.green.fefu.online.repository.HaesolOnlineMultipleRepository;
import com.green.fefu.online.repository.HaesolOnlineRepository;
import com.green.fefu.online.repository.SubjectRepository;
import com.green.fefu.online.repository.TypeTagRepository;
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

import java.time.LocalDateTime;
import java.util.*;

import static com.green.fefu.exception.JSH.JshErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class HaesolOnlineServiceImpl {
    // 공통된 메소드를 저장(로그인한 유저에 따라 해당하는 학년정보 추출)
    private final CommonMethodStorage methodStorage;

    // 내가 저장하고 꺼내올  Entity Repository
    private final HaesolOnlineRepository haesolOnlineRepository;
    private final HaesolOnlineMultipleRepository haesolOnlineMultipleRepository;
    private final TypeTagRepository typeTagRepository;
    private final SubjectRepository subjectRepository;

    // 어떠한 사용자인지 분기
    private final AuthenticationFacade authenticationFacade;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;

    // 기타 문제
    private final CustomFileUtils customFileUtils;
    private final OnlineMapper mapper;
    private final Integer TOTAL_TEST_QUESTION=20;

    @Transactional
    public int PostKorAMatQuestion(MultipartFile pic, PostOnlineQuestionReq p) {
        log.info("Service 데이터 객체 : {}", p);
        //setter에 들어갈 Entity + fileName제작
        Teacher teacher = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        log.info("teacher entity {}",teacher);
        Subject subject = subjectRepository.getReferenceById(p.getSubjectCode());
        log.info("subject entity {}", subject);
        TypeTag typeTag = typeTagRepository.findByTypeNum(p.getTypeTag());
        log.info("p.getTypeTag {}", p.getTypeTag());
        log.info("p.getSubjectCode {}", p.getSubjectCode());
        log.info("typeTag entity {}", typeTag);
        String picName = customFileUtils.makeRandomFileName(pic);

        // entity 생성 + 파일 이름
        HaesolOnline haesolOnline = new HaesolOnline();

        haesolOnline.setTeaId(teacher); //선생님 PK->아래에서 학반 정보
        haesolOnline.setClassId(mapper.teacherClass(teacher.getTeaId())); //선생님 정보 토대로 학년 정보
        haesolOnline.setSubjectCode(subject); //과목코드
        haesolOnline.setTypeTag(typeTag); //객관식-주관식
        log.info("typeTag is null? {}", haesolOnline.getTypeTag());
        haesolOnline.setLevel(p.getLevel()); //난이도
        haesolOnline.setQuestion(p.getQuestion()); //문제
        haesolOnline.setContents(p.getContents()); //내용
        haesolOnline.setAnswer(p.getAnswer()); //보기 및 단답형 정답
        haesolOnline.setQueTag(p.getQueTag()); //객관식 주관식 구분
        haesolOnline.setPic(picName); // 사진 저장
        haesolOnline.setCreatedAt(LocalDateTime.now()); //생성일자(상속)
        // 문제 Entity에 저장
        log.info("OnlineEntity(haesolOnline) : {}", haesolOnline);
        haesolOnlineRepository.save(haesolOnline);

        // 사진을 폴더에 저장
        if (pic != null || !pic.isEmpty()) {
            try {
                String path = String.format("onlineKorMat/%s", haesolOnline.getQueId());
                customFileUtils.makeFolders(path);
                String target = String.format("%s/%s", path, picName);
                customFileUtils.transferTo(pic, target);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException(CAN_T_UPROAD_QUESTION);
            }
        }

        // 문제 유형이 객관식이라면 보기를 Entity에 저장
        if (p.getQueTag() == 1) {
            List<String> haesolOnlineMultipleList = p.getMultiple();
            log.info("multiple req : {}", p.getMultiple());
            int num=1;
            List<HaesolOnlineMultiple> multipleList=new ArrayList<>();
            for(String mul:haesolOnlineMultipleList) {
                HaesolOnlineMultiple entHaesolOnlineMultiple = new HaesolOnlineMultiple();
                entHaesolOnlineMultiple.setHaesolOnline(haesolOnline);
                entHaesolOnlineMultiple.setNum(num);
                log.info("num:{} : string : {}", num, mul);
                entHaesolOnlineMultiple.setSentence(mul);
                num++;
                multipleList.add(entHaesolOnlineMultiple);
                log.info("OnlineEntity(entHaesolOnlineMultiple) : {}", entHaesolOnlineMultiple);
            }
            haesolOnlineMultipleRepository.saveAll(multipleList);
        }
        return 1;
    }


    public List<GetKoreanAndMathQuestionRes> GetKorAMatQuestion(GetKoreanAndMathQuestionReq p){
        //학부모-> 자녀 학급, 학생->본인, 학급 선생님-> 담당학급 을 추출하는 과정이 필요
        //로그인 한 유저가 누구이며 그에 따른 학년 조회
        long grade=methodStorage.signedUserGrade(p.getStudentPk());

        //TypeTag typeTag = typeTagRepository.findByTypeNumAndSubject_SubjectId(p.getTypeTag(), p.getSubjectCode());

        // 학년과 과목 코드를 넣어서 전체 문제 리스트 조회 ex. 1학년 수학과목
        List<HaesolOnline> listAll=haesolOnlineRepository.findBySubjectCodeAndClassId(p.getSubjectCode(), grade);
        List<GetKoreanAndMathQuestionRes> list=new ArrayList<>(); //20문제만 뽑아 낼 새로운 리스트
        // 문제가 없을 때의 exception 처리
        if(listAll == null || listAll.isEmpty()){
            throw new CustomException(NOT_FOUND_QUESTION);
        }
        log.info("문제는 받아왔음");
        Collections.shuffle(listAll);
//        총 문제 수와 20문제의 비교 후 필요한 만큼 추출(삼항식)
//        for (int i = 0; i < (TOTAL_TEST_QUESTION < listAll.size() ? TOTAL_TEST_QUESTION : listAll.size()); i++) {
//            HaesolOnline a = listAll.get(i);
//            int rand = (int)(Math.random()*listAll.size());
//             랜덤으로 숫자를 섞음
//            listAll.set(i, listAll.get(rand));
//            listAll.set(rand, a);
//        }
        log.info("랜덤으로 문제를 섞음");
        for (int i = 0; i < (TOTAL_TEST_QUESTION < listAll.size() ? TOTAL_TEST_QUESTION : listAll.size()); i++) {
            GetKoreanAndMathQuestionRes a = new GetKoreanAndMathQuestionRes();

            a.setQuestion(listAll.get(i).getQuestion());
            //a.setQueId(listAll.get(i).getQueId());
            a.setLevel(listAll.get(i).getLevel());
            log.info("get res 태그 전: {}",a);
            a.setTypeTag(listAll.get(i).getTypeTag().getTypeNum());
            log.info("get Tag 아마 여기가 2개라서 : {}", a.getTypeTag());
            log.info("get res 태그 후: {}",a);
            a.setQueTag(listAll.get(i).getQueTag());
            a.setContents(listAll.get(i).getContents());
            a.setAnswer(listAll.get(i).getAnswer());
            a.setPic(listAll.get(i).getPic());
            List<HaesolOnlineMultiple> aaa = haesolOnlineMultipleRepository.findSentenceByQueIdOrderByNum(listAll.get(i).getQueId());
            for (HaesolOnlineMultiple item : aaa){
                a.getSentence().add(item.getSentence());
            }
            log.info("리스트 add 직전");
            list.add(a);
        }
        log.info("리턴 전");
        return list;
    }

    //시험 문제를 풀고 그에 따른 점수 및 분석
//    public ???? testMarking() {
//        List<Integer> answerList=new ArrayList<>(); //학생의 응답
//        List<GetKoreanAndMathQuestionRes> testNow=new ArrayList<>();
//        List<Integer> markingList=new ArrayList<>();
//        for(int i=0; i<testNow.size(); i++){
//            if(testNow.get(i).getAnswer()!=answerList.get(i)){
//                markingList.add(a); // 맞은 정답과 틀린 정답을 담을 수 있는 객체
//
//            }
//
//
//        }
//
//        //현재 화면상에 출력되고 있는 시험 문제 리스트
//        //그 리스트의 문제의 정답과 유형
//        //사용자가 체크한 리스트와 비교
//        //다른 부분 유형 분석
//        //ex. 국어 : 독해 11 문법 12 문학 13 -> 유달리 많이 틀린 문제
//        // [독해] 유형을 많이 틀리셨습니다.
//        return null;
//    }
}
