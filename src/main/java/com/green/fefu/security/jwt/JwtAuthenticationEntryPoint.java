package com.green.fefu.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

//잘못된, 만료된, 지원하지 않는 토큰 처리
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //HttpServletResponse.SC_UNAUTHORIZED => 401 에러 ( jwt 할때 쓰는 에러 )
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

    }
}
