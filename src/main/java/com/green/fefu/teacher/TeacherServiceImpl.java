package com.green.fefu.teacher;


import com.green.fefu.chcommon.Parser;
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
import java.util.regex.Pattern;

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
    //    정규 표현식 체크
    private final String ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,12}$";
    private final Pattern idPattern = Pattern.compile(ID_PATTERN);
    private final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|.*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,20}$";
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private final String NAME_PATTERN = "^[가-힣a-zA-Z\\s-]+$";
    private final Pattern namePattern = Pattern.compile(NAME_PATTERN);
    private final String PHONE_PATTERN = "^010-\\d{4}-\\d{4}$";
    private final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

//=====================================================================================================================

    //    회원가입
    @Transactional
    public Map CreateTeacher(CreateTeacherReq p, Map map) throws Exception {

//         벨리데이션 체크
//        1. 데이터 널체크
        validation.nullCheck(p.getTeacherId());
        validation.nullCheck(p.getPassword());
        validation.nullCheck(p.getName());
        validation.nullCheck(p.getEmail());
        validation.nullCheck(p.getPhone());
        validation.nullCheck(p.getGender());

//        2. 데이터 타입 체크
        if (p.getBirth() != null && !p.getBirth().trim().isEmpty()) {
            validation.typeCheck(p.getBirth(), LocalDate.class, BIRTH_TYPE_ERROR);
        }
        if (!idPattern.matcher(p.getTeacherId()).matches()) {
            throw new RuntimeException(ID_PATTERN_ERROR);
        } else if (!emailPattern.matcher(p.getEmail()).matches()) {
            throw new RuntimeException(EMAIL_PATTERN_ERROR);
        } else if (!namePattern.matcher(p.getName()).matches()) {
            throw new RuntimeException(NAME_PATTERN_ERROR);
        } else if (!passwordPattern.matcher(p.getPassword()).matches()) {
            throw new RuntimeException(PASSWORD_PATTERN_ERROR);
        } else if (!phonePattern.matcher(p.getPhone()).matches()) {
            throw new RuntimeException(PHONE_PATTERN_ERROR);
        }

//        3. 비밀번호 암호화
        String hashpw = passwordEncoder.encode(p.getPassword());
        p.setPassword(hashpw);
//        4. 주소 데이터 합성

//        만들어야 함


//        5. 데이터 길이 체크
        validation.lengthCheck(p.getTeacherId(), TEACHER_ID_MAX_LENGTH);
        validation.lengthCheck(p.getPassword(), TEACHER_PASSWORD_MAX_LENGTH);
        validation.lengthCheck(p.getName(), TEACHER_NAME_MAX_LENGTH);
        validation.lengthCheck(p.getEmail(), TEACHER_EMAIL_MAX_LENGTH);
        validation.lengthCheck(p.getPhone(), TEACHER_PHONE_MAX_LENGTH);
        validation.lengthCheck(p.getGender(), TEACHER_GENDER_MAX_LENGTH);
        if (!p.getFullAddr().isEmpty()) {
            validation.lengthCheck(p.getFullAddr(), TEACHER_ADDRESS_MAX_LENGTH);
        }

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

//=====================================================================================================================

    //    로그인
    public Map LogInTeacher(LogInTeacherReq p, Map map, HttpServletResponse res) throws Exception {

//        데이터 널 체크
        validation.nullCheck(p.getTeacherId());
        validation.nullCheck(p.getPassword());
//        데이터 타입 체크
        if (!idPattern.matcher(p.getTeacherId()).matches()) {
            throw new RuntimeException(ID_PATTERN_ERROR);
        } else if (!passwordPattern.matcher(p.getPassword()).matches()) {
            throw new RuntimeException(PASSWORD_PATTERN_ERROR);
        }
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
        MyUser myUser = MyUser.builder()
                .userId(teacher.getPk())
                .role(teacher.getAuth())
                .build();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

        int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res, "refresh-token");
        cookieUtils.setCookie(res, "refresh-token", refreshToken, refreshTokenMaxAge);


//        담당 학급 받아오기
        String teacherClass = mapper.getCurrentClassesByTeacherId(teacher.getPk());
        String tClass = Parser.classParser(teacherClass);

//        데이터 삽입
        map.put(TEACHER_NAME, teacher.getName());
        map.put(TEACHER_EMAIL, teacher.getEmail());
        map.put(TEACHER_CLASS, tClass);
        map.put(TEACHER_ACCESS_TOKEN, accessToken);

        return map;
    }


//=====================================================================================================================

    //    아이디, 이메일 중복 확인
    public void CheckDuplicate(CheckDuplicateReq p) throws Exception {
//        아이디 형식 확인
        if (p.getTeacherId() != null) {
            if (!idPattern.matcher(p.getTeacherId()).matches()) {
                throw new RuntimeException(ID_PATTERN_ERROR);
            }
        }
//        이메일 형식 확인
        else if (p.getEmail() != null) {
            if (!emailPattern.matcher(p.getEmail()).matches()) {
                throw new RuntimeException(EMAIL_PATTERN_ERROR);
            }
        } else {
            throw new RuntimeException(ESSENTIAL_DATA_NOT_FOUND_ERROR);
        }

//        유저 select (아이디, email)
        TeacherEntity teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .id(p.getTeacherId())
                        .email(p.getEmail())
                        .build()
        );
        if (teacher != null) {
            throw new RuntimeException(DUPLICATE_DATA_ERROR);
        }
    }

//=====================================================================================================================

    //    선생님 아이디 찾기
    public Map FindTeacherId(FindTeacherIdReq p, Map map) throws Exception {
//        널 체크
        validation.nullCheck(p.getName());
        validation.nullCheck(p.getPhone());
//        타입 체크
        if (!namePattern.matcher(p.getName()).matches()) {
            throw new RuntimeException(ID_PATTERN_ERROR);
        } else if (!phonePattern.matcher(p.getPhone()).matches()) {
            throw new RuntimeException(PHONE_PATTERN_ERROR);
        }

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

//=====================================================================================================================

    //    선생님 비밀번호 찾기
    public void FindTeacherPassword(FindTeacherPasswordReq p) throws Exception {
//        널 체크
        validation.nullCheck(p.getTeacherId());
        validation.nullCheck(p.getPhone());
//        타입 체크
        if (!idPattern.matcher(p.getTeacherId()).matches()) {
            throw new RuntimeException(ID_PATTERN_ERROR);
        } else if (!phonePattern.matcher(p.getPhone()).matches()) {
            throw new RuntimeException(PHONE_PATTERN_ERROR);
        }
        TeacherEntity teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .id(p.getTeacherId())
                        .phone(p.getPhone())
                        .build()
        );
        if (teacher == null) {
            throw new RuntimeException(NOT_FOUND_USER_ERROR);
        }

//        휴대폰으로 문자 보내기
//        짜야함 ( 미완성 )
        try {
            SmsSender.sendSms(p.getPhone());
        } catch (Exception e) {
            throw new RuntimeException(SMS_SEND_ERROR);
        }
    }

//=====================================================================================================================

    //    선생님 비밀번호 변경 ( 로그인 전 )
    public void ChangePassWord(ChangePassWordReq p) throws Exception {
        TeacherEntity teacher;

//        널체크
        validation.nullCheck(p.getPassWord());
        validation.nullCheck(p.getTeacherId());
//        유저 select
        teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .id(p.getTeacherId())
                        .build()
        );
        if (teacher == null) {
            throw new RuntimeException(ID_NOT_FOUND_ERROR);
        }

//        타입 체크
        if (!passwordPattern.matcher(p.getPassWord()).matches()) {
            throw new RuntimeException(PASSWORD_PATTERN_ERROR);
        }


//        비밀번호 암호화
        String hashpw = passwordEncoder.encode(p.getPassWord());
        p.setPassWord(hashpw);


//        쿼리 실행
        int result = mapper.ChangePassWord(p);
        if (result != 1) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
    }

//=====================================================================================================================

    //    선생님 내정보 불러오기

    public Map TeacherDetail(Map map) throws Exception {
        TeacherEntity teacher = mapper.GetTeacher(
                EntityArgument.builder()
                        .pk(authenticationFacade.getLogInUserId())
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
}
