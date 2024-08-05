package com.green.fefu.admin.service;


import com.green.fefu.admin.model.dto.*;
import com.green.fefu.admin.model.req.*;
import com.green.fefu.admin.test.AdminService;
import com.green.fefu.chcommon.Parser;
import com.green.fefu.entity.Class;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import com.green.fefu.exception.CustomException;
import com.green.fefu.parents.ParentRepository;
import com.green.fefu.student.repository.ClassRepository;
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


    //    유저 리스트 가져오기
    public Map findUnAcceptList(int p, Map map) {
        List<GetUserListDto> list;
        List<Map> result = new ArrayList<>();
//        부모 리스트 가져오기
        if (p == 1) {
            list = mapper.getParentList();
            map.put(CODE, "학부모");
        }
//        교직원 리스트 가져오기
        else if (p == 2) {
            list = mapper.getTeacherList();
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
                dto.put(CREATEDAT, getUserListDto.getCreatedAt());
            } else {
                dto.put(GRADE, null); // 학년
                dto.put(CLASS, null); // 반
                dto.put(CREATEDAT, getUserListDto.getCreatedAt());
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

    public List<FindUserListDto> findUserList(FindUserListReq p, List<FindUserListDto> list) {

//        부모
        if (p.getP() == PrarentCode) {
            List<Parents> parentList = parentRepository.findAllByStateIs(p.getCheck());
            if(parentList == null || parentList.isEmpty()) {
                return new ArrayList<>();
            }
            for (Parents parent : parentList) {
                FindUserListDto data = new FindUserListDto();
                data.setState(parent.getState() == 1 ? "활성화" : "비활성화");
                data.setUid(parent.getUid());
                data.setName(parent.getName());
//                자녀 여러명 보여줘야 하는지 프론트 한테 물어봐야함
//                data.setGrade();
//                data.setUclass();
//                data.setDate();
                list.add(data);
            }
        }
//        교직원
        else if (p.getP() == TeacherCode) {
            List<Teacher> teacherList = teacherRepository.findAllByStateIs(p.getCheck());
            if(teacherList == null || teacherList.isEmpty()) {
                return new ArrayList<>();
            }
            for (Teacher teacher : teacherList) {
                Class c = classRepository.getReferenceByTeaId(teacher.getTeaId());
                FindUserListDto data = new FindUserListDto();
                data.setState(teacher.getState() == 1 ? "활성화" : "비활성화");
                data.setUid(teacher.getUid());
                data.setName(teacher.getName());
//                담당 학급 받아오는거 아직 class 엔티티 없어서 못만듦
                String[] gradeClass = Parser.classParserArray(Long.toString(c.getClassId()));
                data.setGrade(gradeClass[0]);
                data.setUclass(gradeClass[1]);
                data.setDate(Date.valueOf(teacher.getCreatedAt().toLocalDate()));
                list.add(data);
            }
        } else {
            throw new CustomException(DIVISION_ERROR);
        }
        return list;
    }

    @Transactional
    public void updateUser(UpdateUserReq p) {
//        부모
        if(p.getP() == PrarentCode){
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
