//package com.green.fefu.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///*
//SecurityConfiguration에서 어느 URL에 로그인&권한이 필요한지 정의
//첫 번째로 만나는 필터 token이 있는지 확인하고 없으면 생성해주는 역할*/
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter/*1번만 실행*/ {
//    private final JwtTokenProvider jwtTokenProvider; //빈등록+DI
//    @Override/*추상클래스의 구현부가 없는 메소드 구현의 강제성*/
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//                                    throws ServletException/*서블릿 바깥으로 갔을 때*/, IOException/*입출력 오류*/ {
//        String token=jwtTokenProvider.resolveToken/*토큰을 꺼냄*/(request);
//        if(token!=null && jwtTokenProvider.isValidateToken(token))/*null 값이 retrun되진 않았는지, 살아있는지*/{
//            // 안에 값이 들어있으면 True, 비어있으면 False
//            // 만료시간이 경과하지 않았으면 True, 만료시간이 경과하였으면 False
//            // => 통과했다는 건 값이 들어있고 만료 전이라는 것
//            Authentication/*인증*/ auth=jwtTokenProvider.getAuthentication(token);
//
//            SecurityContextHolder.getContext()/*로그인처리 인가처리?*/.setAuthentication(auth);
//        }
//        filterChain.doFilter/*예외 발생, 다음 필터로 넘김*/(request, response);
//    }
//}
