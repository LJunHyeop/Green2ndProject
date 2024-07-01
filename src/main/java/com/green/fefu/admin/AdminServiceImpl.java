package com.green.fefu.admin;


import com.green.fefu.admin.model.dto.GetUserListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.green.fefu.admin.model.dataset.AdminMapNamingData.*;
import static com.green.fefu.admin.model.dataset.ExceptionMsgDataSet.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl {
    private final AdminMapper mapper;

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
            throw new RuntimeException(DIVISION_ERROR);
        }

        for (GetUserListDto getUserListDto : list) {
            Map dto = new HashMap();
            dto.put(PK, getUserListDto.getPk());
            dto.put(ID, getUserListDto.getId());
            dto.put(NAME, getUserListDto.getName());
            dto.put(GRADE, getUserListDto.getGrade());
            dto.put(CLASS, getUserListDto.getGrade());
            dto.put(CREATEDAT, getUserListDto.getCreatedAt());
            result.add(dto);
        }
        map.put(LIST, result);
        return map;
    }

    public Map deleteUser(int p, Map map) {
        //        부모 리스트 가져오기
        if (p == 1) {

        }
//        교직원 리스트 가져오기
        else if (p == 2) {

        } else {
            throw new RuntimeException(DIVISION_ERROR);
        }
        return map;
    }

    public Map acceptUser(int p, Map map) {
        //        부모 리스트 가져오기
        if (p == 1) {

        }
//        교직원 리스트 가져오기
        else if (p == 2) {

        } else {
            throw new RuntimeException(DIVISION_ERROR);
        }
        return map;
    }
}
