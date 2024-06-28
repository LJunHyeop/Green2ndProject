package com.green.fefu.teacher;


import com.green.fefu.teacher.model.req.CreateTeacherReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.green.fefu.teacher.model.dataset.TeacherAllData.*;
import static com.green.fefu.teacher.model.dataset.TeacherDBMaxLength.*;

import com.green.fefu.chcommon.Validation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl {
    private final TeacherMapper mapper;
    private final Validation validation;
    private final PasswordEncoder passwordEncoder;
    //    정규 표현식 체크
    private final String ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,12}$";
    private final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|.*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,20}$";
    private final Pattern idPattern = Pattern.compile(ID_PATTERN);
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private final String NAME_PATTERN = "^[가-힣a-zA-Z\\s-]+$";
    private final Pattern namePattern = Pattern.compile(NAME_PATTERN);

    public Map<String, String> CreateTeacher(CreateTeacherReq p) throws Exception {
        Map<String, String> map = new HashMap<>();

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
            validation.typeCheck(p.getBirth(), LocalDateTime.class, "생년월일 입력 형식 에러");
        }
        if (!idPattern.matcher(p.getTeacherId()).matches()) {
            throw new RuntimeException("아이디 형식을 맞춰주세요");
        } else if (!emailPattern.matcher(p.getEmail()).matches()) {
            throw new RuntimeException("이메일 형식을 맞춰주세요");
        } else if (!namePattern.matcher(p.getName()).matches()) {
            throw new RuntimeException("이름 형식을 맞춰주세요");
        } else if (!passwordPattern.matcher(p.getPassword()).matches()) {
            throw new RuntimeException("비밀번호 형식을 맞춰주세요");
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
            throw new RuntimeException("쿼리 에러");
        }

//        정상 결과 시
        map.put(TEACHER_PK, p.getTeacherPk());

        return map;
    }
}
