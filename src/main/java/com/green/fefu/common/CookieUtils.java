package com.green.fefu.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtils {

    private final ObjectMapper om;

//    public <T> T getData(T type, HttpServletRequest req, String name) {
//        Cookie cookie = getCookie(req, name);
//        String json = cookie.getValue();
//        return (T) om.readValue(json, type.getClass());
//    }

    public Cookie getCookie(HttpServletRequest req, String name) {
        // req에서 모든 쿠키 정보를 받는다.
        Cookie[] cookies = req.getCookies();
        
        //쿠키 정보가 있고 쿠키가 하나이상 있다면
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                
                //찾고자 하는 key가 있는지 확인 후 있으면 리턴
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public void setCookie(HttpServletResponse res, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        // Root URL ( 우리 백엔드 모든 요청에 해당하게 셋팅 )
        cookie.setPath("/");
        // 보안 쿠키
        cookie.setHttpOnly(true);
        // 만료 시간
        cookie.setMaxAge(maxAge);
        res.addCookie(cookie);
    }

    public void deleteCookie(HttpServletResponse res, String name) {
        setCookie(res, name, null, 0);
    }
}
