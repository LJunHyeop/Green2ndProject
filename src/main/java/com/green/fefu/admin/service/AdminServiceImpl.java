package com.green.fefu.admin.service;


import com.green.fefu.admin.model.dto.*;
import com.green.fefu.admin.model.req.*;
import com.green.fefu.admin.test.AdminService;
import com.green.fefu.chcommon.Parser;
import com.green.fefu.chcommon.Validation;
import com.green.fefu.common.AppProperties;
import com.green.fefu.common.CookieUtils;
import com.green.fefu.security.MyUser;
import com.green.fefu.security.MyUserDetails;
import com.green.fefu.security.jwt.JwtTokenProviderV2;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.green.fefu.admin.model.dataset.AdminMapNamingData.*;
import static com.green.fefu.admin.model.dataset.ExceptionMsgDataSet.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminMapper mapper;
    private final Validation validation;
    private final AppProperties appProperties;
    private final JwtTokenProviderV2 jwtTokenProvider;
    private final CookieUtils cookieUtils;

    //    유저 리스트 가져오기
    public Map findUnAcceptList(int p, Map map) throws Exception {
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
//        해야함
//      클래스 아이디 기준으로 학년 반 나눠야함
        for (GetUserListDto getUserListDto : list) {
            Map dto = new HashMap();
            dto.put(PK, getUserListDto.getPk());
            dto.put(ID, getUserListDto.getId());
            dto.put(NAME, getUserListDto.getName());
            String[] tClass = Parser.classParserArray(getUserListDto.getGrade());
            dto.put(GRADE, tClass[0]); // 학년
            dto.put(CLASS, tClass[1]); // 반
            dto.put(CREATEDAT, getUserListDto.getCreatedAt());
            result.add(dto);
        }
        map.put(LIST, result);
        return map;
    }

    //=====================================================================================================================
//    유저 삭제
    @Transactional
    public void deleteUser(adminUserReq p) throws Exception {
        int result;
//        널체크
        deleteUserNullCheck(p);

        //        부모 리스트 가져오기
        if (p.getP() == 1) {
            result = mapper.delParent(p.getPk());
        }
//        교직원 리스트 가져오기
        else if (p.getP() == 2) {
            result = mapper.delTeacher(p.getPk());
        } else {
            throw new RuntimeException(DIVISION_ERROR);
        }

        if (result != 1) {
            throw new RuntimeException(QUERY_RESULT_ERROR);
        }
    }

    private void deleteUserNullCheck(adminUserReq p) throws Exception {
        validation.nullCheck(p.getP().toString());
        validation.nullCheck(p.getPk().toString());
    }

    //=====================================================================================================================
//    유저 승인
    @Transactional
    public void acceptUser(adminUserReq p) throws Exception {
        int result;
//        널체크
        acceptUserNullCheck(p);
        //        부모 리스트 가져오기
        if (p.getP() == 1) {
            result = mapper.updParent(p.getPk());
            if (result != 1) {
                throw new RuntimeException(QUERY_RESULT_ERROR);
            }
        }
//        교직원 리스트 가져오기
        else if (p.getP() == 2) {
            result = mapper.updTeacher(p.getPk());
            if (result != 1) {
                throw new RuntimeException(QUERY_RESULT_ERROR);
            }
        } else {
            throw new RuntimeException(DIVISION_ERROR);
        }


    }

    private void acceptUserNullCheck(adminUserReq p) throws Exception {
        validation.nullCheck(p.getP().toString());
        validation.nullCheck(p.getPk().toString());
    }



    public Map getAccessToken(Map map, HttpServletRequest req) throws Exception {
        Cookie cookie = cookieUtils.getCookie(req, appProperties.getJwt().getRefreshTokenCookieName());
        // refreshToken 값이 있는 쿠키의 존재 유무
        if (cookie == null) {
            throw new RuntimeException(COOKIE_NOT_FOUND_ERROR);
        }
        String refreshToken = cookie.getValue();
        // refreshToken 만료시간 체크
        if (!jwtTokenProvider.isValidateToken(refreshToken)) {
            throw new RuntimeException(RE_FRESH_TOKEN_TIME_OUT_ERROR);
        }

        Authentication auth = jwtTokenProvider.getAuthentication(refreshToken);
        MyUser myUser = ((MyUserDetails) auth).getMyUser();
        String accessToken = jwtTokenProvider.generateAccessToken(myUser);

        map.put(ADMIN_ACCESS_TOKEN, accessToken);
        return map;
    }
}