package com.green.fefu.teacher;


import com.green.fefu.chcommon.Parser;
import com.green.fefu.chcommon.PatternCheck;
import com.green.fefu.chcommon.SmsSender;
import com.green.fefu.common.AppProperties;
import com.green.fefu.common.CookieUtils;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import com.green.fefu.security.jwt.JwtTokenProviderV2;
import com.green.fefu.teacher.model.dto.EntityArgument;
import com.green.fefu.teacher.model.dto.TeacherEntity;
import com.green.fefu.teacher.model.req.*;
import com.green.fefu.chcommon.Validation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.green.fefu.teacher.model.dataset.TeacherMapNamingData.*;
import static com.green.fefu.teacher.model.dataset.TeacherDBMaxLength.*;
import static com.green.fefu.teacher.model.dataset.ExceptionMsgDataSet.*;


import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl {
    private final TeacherMapper mapper;
    private final Validation validation;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;
    private final JwtTokenProviderV2 jwtTokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookieUtils;
    private final PatternCheck patternCheck;


//=====================================================================================================================

    //    회원가입
    @Transactional
    public Map CreateTeacher(CreateTeacherReq p, Map map) throws Exception {
//         벨리데이션 체크
//        1. 데이터 널체크
        createTeacherNullCheck(p);
//        2. 데이터 타입 체크
        createTeacherTypeCheck(p);
//        3. 비밀번호 암호화
        String hashpw = passwordEncoder.encode(p.getPassword());
        p.setPassword(hashpw);
//        4. 주소 데이터 합성
//        만들어야 함
//        5. 데이터 길이 체크
        createTeacherLengthCheck(p);
//        5. 쿼리 실행
        int result = mapper.CreateTeacher(p);
//        6. 쿼리 결과 체크
        if (result == 0) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
//        정상 결과 시
        map.put(TEACHER_PK, p.getTeacherPk());
        return map;
    }

    private void createTeacherNullCheck(CreateTeacherReq p) throws Exception {
        validation.nullCheck(p.getTeacherId());
        validation.nullCheck(p.getPassword());
        validation.nullCheck(p.getName());
        validation.nullCheck(p.getEmail());
        validation.nullCheck(p.getPhone());
        validation.nullCheck(p.getGender());
    }

    private void createTeacherTypeCheck(CreateTeacherReq p) throws Exception {
        if (p.getBirth() != null && !p.getBirth().trim().isEmpty()) {
            validation.typeCheck(p.getBirth(), LocalDate.class, BIRTH_TYPE_ERROR);
        }
        patternCheck.idCheck(p.getTeacherId());
        patternCheck.emailCheck(p.getEmail());
        patternCheck.nameCheck(p.getName());
        patternCheck.passwordCheck(p.getPassword());
        patternCheck.phoneCheck(p.getPhone());
    }

    private void createTeacherLengthCheck(CreateTeacherReq p) throws Exception {
        validation.lengthCheck(p.getTeacherId(), TEACHER_ID_MAX_LENGTH);
        validation.lengthCheck(p.getPassword(), TEACHER_PASSWORD_MAX_LENGTH);
        validation.lengthCheck(p.getName(), TEACHER_NAME_MAX_LENGTH);
        validation.lengthCheck(p.getEmail(), TEACHER_EMAIL_MAX_LENGTH);
        validation.lengthCheck(p.getPhone(), TEACHER_PHONE_MAX_LENGTH);
        validation.lengthCheck(p.getGender(), TEACHER_GENDER_MAX_LENGTH);
        if (p.getFullAddr() != null) {
            validation.lengthCheck(p.getFullAddr(), TEACHER_ADDRESS_MAX_LENGTH);
        }
    }

//=====================================================================================================================

    //    로그인
    public Map LogInTeacher(LogInTeacherReq p, Map map, HttpServletResponse res) throws Exception {

//        데이터 널 체크
        logInTeacherNullCheck(p);
//        데이터 타입 체크
        logInTeacherTypeCheck(p);
//        유저 select
        TeacherEntity teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .id(p.getTeacherId())
                        .build()
        );

//        아이디로 확인 안됐을때
        if (teacher == null) {
            throw new RuntimeException(ID_NOT_FOUND_ERROR);
        }
//        비밀번호 매치 체크
//        암호화된 비밀번호가 다를때
        else if (!passwordEncoder.matches(p.getPassword(), teacher.getPassword())) {
            throw new RuntimeException(PASSWORD_NO_MATCH_ERROR);
        }

//        JWT 토큰 발급


        String accessToken = createToken(teacher, res);


//        담당 학급 받아오기
        String teacherClass = mapper.getCurrentClassesByTeacherId(teacher.getPk());
        String tClass = null;
//        담당 학급이 있을 때 ( 방금 회원가입하면 담당학급이 없음 )
        if (teacherClass != null) {
            tClass = Parser.classParser(teacherClass);
        }

//        데이터 삽입
        map.put(TEACHER_NAME, teacher.getName());
        map.put(TEACHER_EMAIL, teacher.getEmail());
        map.put(TEACHER_CLASS, tClass);
        map.put(TEACHER_ACCESS_TOKEN, accessToken);

        return map;
    }

    private void logInTeacherNullCheck(LogInTeacherReq p) throws Exception {
        validation.nullCheck(p.getTeacherId());
        validation.nullCheck(p.getPassword());
    }

    private void logInTeacherTypeCheck(LogInTeacherReq p) throws Exception {
        patternCheck.idCheck(p.getTeacherId());
        patternCheck.passwordCheck(p.getPassword());
    }

    private String createToken(TeacherEntity teacher, HttpServletResponse res) throws Exception {
        MyUser myUser = MyUser.builder()
                .userId(teacher.getPk())
                .role(teacher.getAuth().trim())
                .build();
        String accessToken = jwtTokenProvider.generateAccessToken(myUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

//        refreshToken은 보안 쿠키를 이용해서 처리
        int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res, "refresh-token");
        cookieUtils.setCookie(res, "refresh-token", refreshToken, refreshTokenMaxAge);
        return accessToken;
    }


//=====================================================================================================================

    //    아이디, 이메일 중복 확인
    public void CheckDuplicate(CheckDuplicateReq p) throws Exception {

        dataCheck(p);

//        유저 select (아이디, email)
        TeacherEntity teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .id(p.getId())
                        .email(p.getEmail())
                        .build()
        );
        if (teacher != null) {
            throw new RuntimeException(DUPLICATE_DATA_ERROR);
        }
    }

    private void dataCheck(CheckDuplicateReq p) {
        if (p.getId() != null && p.getEmail() != null) {
            throw new RuntimeException(MULTIPLE_DATA_ERROR);
        }

//        아이디 형식 확인
        if (p.getId() != null) {
            patternCheck.idCheck(p.getId());
        }
//        이메일 형식 확인
        else if (p.getEmail() != null) {
            patternCheck.emailCheck(p.getEmail());
        } else {
            throw new RuntimeException(ESSENTIAL_DATA_NOT_FOUND_ERROR);
        }
    }

//=====================================================================================================================

    //    선생님 아이디 찾기
    public Map FindTeacherId(FindTeacherIdReq p, Map map) throws Exception {
//        널 체크
        FindTeacherIdNullCheck(p);
//        타입 체크
        FindTeacherIdTypeCheck(p);

        TeacherEntity teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .name(p.getName())
                        .phone(p.getPhone())
                        .build()
        );
        if (teacher == null) {
            throw new RuntimeException(NOT_FOUND_USER_ERROR);
        }
        map.put(TEACHER_ID, teacher.getId());
        return map;
    }

    private void FindTeacherIdNullCheck(FindTeacherIdReq p) throws Exception {
        validation.nullCheck(p.getName());
        validation.nullCheck(p.getPhone());
    }

    private void FindTeacherIdTypeCheck(FindTeacherIdReq p) throws Exception {
        patternCheck.nameCheck(p.getName());
        patternCheck.phoneCheck(p.getPhone());
    }

//=====================================================================================================================

    //    선생님 비밀번호 찾기
    public void FindTeacherPassword(FindTeacherPasswordReq p, Map map) throws Exception {
//        널 체크
        FindTeacherPasswordNullCheck(p);
//        타입 체크
        FindTeacherPasswordTypeCheck(p);
        TeacherEntity teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .id(p.getId())
                        .phone(p.getPhone())
                        .build()
        );
        if (teacher == null) {
            throw new RuntimeException(NOT_FOUND_USER_ERROR);
        }

//        랜덤 코드 6자리 생성
        String code = SmsSender.makeRandomCode();
//        휴대폰으로 문자 보내기
//        짜야함 ( 미완성 )
        try {
            SmsSender.sendSms(p.getPhone(), code);
        } catch (Exception e) {
            throw new RuntimeException(SMS_SEND_ERROR);
        }


        map.put(TEACHER_RANDOM_CODE, code);
    }

    private void FindTeacherPasswordNullCheck(FindTeacherPasswordReq p) throws Exception {
        validation.nullCheck(p.getId());
        validation.nullCheck(p.getPhone());
    }

    private void FindTeacherPasswordTypeCheck(FindTeacherPasswordReq p) throws Exception {
        patternCheck.idCheck(p.getId());
        patternCheck.phoneCheck(p.getPhone());
    }

//=====================================================================================================================

    //    선생님 비밀번호 변경 ( 로그인 전 )
    @Transactional
    public void ChangePassWord(ChangePassWordReq p) throws Exception {

//        널체크
        ChangePassWordNullCheck(p);

//        로그인 햇을때는 TeacherId 값이 null 일 수 있기 때문이다.
//        validation.nullCheck(p.getTeacherId());

//        유저 select
        TeacherEntity teacher = getTEntity(p);

//        타입 체크
        ChangePassWordTypeCheck(p);


//        비밀번호 암호화
        String hashpw = passwordEncoder.encode(p.getPassWord());
        p.setPassWord(hashpw);


        p.setPk(teacher.getPk());
//        쿼리 실행
        int result = mapper.ChangePassWord(p);
        if (result != 1) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
    }

    private void ChangePassWordNullCheck(ChangePassWordReq p) throws Exception {
        validation.nullCheck(p.getPassWord());
    }

    private TeacherEntity getTEntity(ChangePassWordReq p) throws Exception {
        TeacherEntity teacher;
        if (authenticationFacade.getLoginUserId() == 0) {
            validation.nullCheck(p.getTeacherId());
            teacher = mapper.GetTeacher(
                    EntityArgument.builder()
                            .id(p.getTeacherId())
                            .build()
            );
        } else {
            teacher = mapper.GetTeacher(
                    EntityArgument.builder()
                            .pk(authenticationFacade.getLoginUserId())
                            .build()
            );
        }
        if (teacher == null) {
            throw new RuntimeException(ID_NOT_FOUND_ERROR);
        }
        return teacher;
    }

    private void ChangePassWordTypeCheck(ChangePassWordReq p) throws Exception {
        patternCheck.passwordCheck(p.getPassWord());
    }

//=====================================================================================================================

    //    선생님 내정보 불러오기
    public Map TeacherDetail(Map map) throws Exception {
        TeacherEntity teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .pk(authenticationFacade.getLoginUserId())
                        .build()
        );

        String teacherClass = mapper.getCurrentClassesByTeacherId(teacher.getPk());
        String tClass = Parser.classParser(teacherClass);

        map.put(TEACHER_ID, teacher.getId());
        map.put(TEACHER_NAME, teacher.getName());
        map.put(TEACHER_PHONE, teacher.getPhone());
        map.put(TEACHER_EMAIL, teacher.getEmail());
        map.put(TEACHER_GENDER, teacher.getGender());
        map.put(TEACHER_CLASS, tClass);
        map.put(TEACHER_BIRTH, teacher.getBirth());

//        주소 자를껀지 물어보고 잘라야 하면 잘라서 보내주기
//        (현재 우편번호 # 주소 ) 임
        map.put(TEACHER_ADDR, teacher.getAddr());

        return map;
    }

//=====================================================================================================================

    //    선생님 정보 변경
    @Transactional
    public void ChangeTeacher(ChangeTeacherReq p) throws Exception {
        p.setPk(authenticationFacade.getLoginUserId());

//        타입 체크 and 데이터 길이 체크
        ChangeTeacherErrorCheck(p);

//        주소값 합성
//        만들어야함

//        쿼리 실행
        int result = mapper.ChangeTeacher(p);
        if (result != 1) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
    }

    private void ChangeTeacherErrorCheck(ChangeTeacherReq p) throws Exception {
        if (p.getName() != null) {
            patternCheck.nameCheck(p.getName());
            validation.lengthCheck(p.getName(), TEACHER_NAME_MAX_LENGTH);
        }
        if (p.getPhone() != null) {
            patternCheck.phoneCheck(p.getPhone());
            validation.lengthCheck(p.getPhone(), TEACHER_PHONE_MAX_LENGTH);
        }
        if (p.getEmail() != null) {
            patternCheck.emailCheck(p.getEmail());
            validation.lengthCheck(p.getEmail(), TEACHER_EMAIL_MAX_LENGTH);
        }
    }
}
