package com.green.fefu.admin.service;


import com.green.fefu.admin.model.dto.*;
import com.green.fefu.admin.model.req.*;
import com.green.fefu.admin.test.AdminService;
import com.green.fefu.chcommon.Parser;
import com.green.fefu.entity.Class;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Student;
import com.green.fefu.entity.Teacher;
import com.green.fefu.exception.CustomException;
//import com.green.fefu.parents.ParentRepository;
import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.student.repository.ClassRepository;
//import com.green.fefu.parents.repository.ParentRepository;
import com.green.fefu.student.repository.StudentClassRepository;
import com.green.fefu.student.repository.StudentRepository;
import com.green.fefu.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.green.fefu.admin.model.dataset.AdminMapNamingData.*;
import static com.green.fefu.exception.bch.BchErrorCode.*;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminMapper mapper;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final ClassRepository classRepository;
    private final int PrarentCode = 1;
    private final int TeacherCode = 2;
    private final StudentClassRepository studentClassRepository;
    private final StudentRepository studentRepository;


    //    유저 리스트 가져오기
    public Map findUnAcceptList(FindUnAcceptListReq p, Map map) {
        List<GetUserListDto> list;
        List<Map> result = new ArrayList<>();
//        부모 리스트 가져오기
        if (p.getP() == 1) {
            list = mapper.getParentList(p.getSearchWord());
            map.put(CODE, "학부모");
        }
//        교직원 리스트 가져오기
        else if (p.getP() == 2) {
            list = mapper.getTeacherList(p.getSearchWord());
            map.put(CODE, "교직원");
        } else {
            throw new CustomException(DIVISION_ERROR);
        }


//        해야함
//      클래스 아이디 기준으로 학년 반 나눠야함
        for (GetUserListDto getUserListDto : list) {
            Map dto = new HashMap();
            dto.put(PK, getUserListDto.getPk());
            dto.put(ID, getUserListDto.getId());
            dto.put(NAME, getUserListDto.getName());

            String grade = getUserListDto.getGrade();
            if (grade != null && !grade.isEmpty()) {
                String[] tClass = Parser.classParserArray(getUserListDto.getGrade());
                dto.put(GRADE, tClass[0]); // 학년
                dto.put(CLASS, tClass[1]); // 반
                dto.put(CREATED_AT, getUserListDto.getCreatedAt());
            } else {
                dto.put(GRADE, null); // 학년
                dto.put(CLASS, null); // 반
                dto.put(CREATED_AT, getUserListDto.getCreatedAt());
            }
            result.add(dto);
        }
        map.put(LIST, result);
        return map;
    }

    //=====================================================================================================================
//    유저 삭제
    @Transactional
    public void deleteUser(adminUserReq p) {
//        int result;
//        널체크
//        deleteUserNullCheck(p);


        //        부모 리스트 가져오기
        if (p.getP() == 1) {
//            result = mapper.delParent(p.getPk());
            Parents parent = parentRepository.getReferenceById(p.getPk());
            parentRepository.delete(parent);
        }
//        교직원 리스트 가져오기
        else if (p.getP() == 2) {
//            result = mapper.delTeacher(p.getPk());
            Teacher teacher = teacherRepository.getReferenceById(p.getPk());
            teacherRepository.delete(teacher);
        } else {
            throw new CustomException(DIVISION_ERROR);
        }
    }

//    private void deleteUserNullCheck(adminUserReq p){
//        validation.nullCheck(p.getP().toString());
//        validation.nullCheck(p.getPk().toString());
//    }

    //=====================================================================================================================
//    유저 승인
    @Transactional
    public void acceptUser(adminUserReq p) {
//        int result;
//        널체크
//        acceptUserNullCheck(p);
        //        부모 리스트 가져오기
        if (p.getP() == 1) {
//            result = mapper.updParent(p.getPk());

//            if (result != 1) {
//                throw new CustomException(QUERY_RESULT_ERROR);
//            }
            Parents parent = parentRepository.getReferenceById(p.getPk());
            parent.setAccept(1);
            parentRepository.save(parent);
        }
//        교직원 리스트 가져오기
        else if (p.getP() == 2) {
//            result = mapper.updTeacher(p.getPk());
//            if (result != 1) {
//                throw new CustomException(QUERY_RESULT_ERROR);
//            }
            Teacher teacher = teacherRepository.getReferenceById(p.getPk());
            teacher.setAccept(1);
            teacherRepository.save(teacher);
        } else {
            throw new CustomException(DIVISION_ERROR);
        }


    }

//    private void acceptUserNullCheck(adminUserReq p){
//        validation.nullCheck(p.getP().toString());
//        validation.nullCheck(p.getPk().toString());
//    }

    public List<Map> findUserList(FindUserListReq p, List list) {


//  부모
        if (p.getP() == PrarentCode) {
            List<Parents> parentList;
            if (p.getSearchWord() != null) {
                parentList = parentRepository.findByNameContainingAndStateIsAndAcceptIs(p.getSearchWord(), p.getCheck(), 1);
            } else {
                parentList = parentRepository.findAllByStateIsAndAcceptIs(p.getCheck(), 1);
            }
            if (parentList == null || parentList.isEmpty()) {
                return new ArrayList<>();
            }
            for (Parents parent : parentList) {
                Map map = new HashMap();  // 새 map 객체 생성
                map.put(STATE, parent.getState() == 1 ? "활성화" : "비활성화");
                map.put(ID, parent.getUid());
                map.put(NAME, parent.getName());
                map.put(PK, parent.getParentsId());
//                    입학 ( 부모기준 현재까지 입학했던 자녀 수 -> 자녀기준 부모 PK 매칭 및 카운트 )
                Long totalChild = studentRepository.countByParent(parent);
                map.put(TOTAL_CHILD, totalChild);
//                    재학 ( 현재 재학중인 자녀 수 ( STATE 1인 애들 )
                Long inSchoolChild = studentRepository.countByParentAndStateIs(parent, 1);
                map.put(IN_SCHOOL_CHILD, inSchoolChild);
//                    가입일
                map.put(CREATED_AT, parent.getCreatedAt());
//                    자녀 리스트
                List<Student> studentList = studentRepository.findStudentsByParentOrderByGradeAsc(parent);
                if (studentList != null && !studentList.isEmpty()) {
                    List<Map> result = new ArrayList<>();
                    for (Student student : studentList) {
                        Map data = new HashMap();
                        data.put(STUDENT_ID, student.getUid());
                        data.put(STUDENT_NAME, student.getName());
                        data.put(STUDENT_PK, student.getStuId());
                        data.put(STUDENT_STATE, switch (student.getState()) {
                                    case 1 -> "재학중";
                                    case 2 -> "전학";
                                    case 3 -> "졸업";
                                    case 4 -> "퇴학";
                                    default -> "정해진 값 없음";
                                });
                        data.put(STUDENT_CODE, student.getRandCode());
                        String[] gradeClass = Parser.classParserArray(Long.toString(student.getGrade()));
                        data.put(STUDENT_GRADE, gradeClass[0]);
                        data.put(STUDENT_CLASS, gradeClass[1]);
//                        신청, 학적 변동
                        result.add(data);
                    }
                    map.put(STUDENT_LIST, result);
                }
                list.add(map);
            }
        }
//  교직원
        else if (p.getP() == TeacherCode) {
            List<Teacher> teacherList;
            if (p.getSearchWord() != null) {
                teacherList = teacherRepository.findByNameContainingAndStateIsAndAcceptIs(p.getSearchWord(), p.getCheck(), 1);
            } else {
                teacherList = teacherRepository.findAllByStateIsAndAcceptIs(p.getCheck(), 1);
            }
            if (teacherList == null || teacherList.isEmpty()) {
                return new ArrayList<>();
            }
            for (Teacher teacher : teacherList) {
                Map map = new HashMap();  // 새 map 객체 생성
                Class c = classRepository.findByTeaId(teacher.getTeaId());
                map.put(STATE, teacher.getState() == 1 ? "활성화" : "비활성화");
                map.put(ID, teacher.getUid());
                map.put(NAME, teacher.getName());
                map.put(PK, teacher.getTeaId());
                if (c != null) {
                    String[] gradeClass = Parser.classParserArray(Long.toString(c.getClassId()));
                    map.put(GRADE, gradeClass[0]);
                    map.put(CLASS, gradeClass[1]);
                    Long studentClass = studentClassRepository.countByClassId(c);
                    map.put(TOTAL_STUDENT, studentClass);
                }
                map.put(CREATED_AT, Date.valueOf(teacher.getCreatedAt().toLocalDate()));
                list.add(map);
            }
        } else {
            throw new CustomException(DIVISION_ERROR);
        }
        return list;
    }


    @Transactional
    public void updateUser(UpdateUserReq p) {
//        부모
        if (p.getP() == PrarentCode) {
            Parents parent = parentRepository.getReferenceById(p.getPk());
            parent.setState(2);
            parentRepository.save(parent);
        }
//        교직원
        else if (p.getP() == TeacherCode) {
            Teacher teacher = teacherRepository.getReferenceById(p.getPk());
            teacher.setState(2);
            teacherRepository.save(teacher);
        } else {
            throw new CustomException(DIVISION_ERROR);
        }
    }
}
