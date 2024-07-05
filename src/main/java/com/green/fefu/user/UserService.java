//package com.green.fefu.user;
//
//import com.green.fefu.common.AppProperties;
//import com.green.fefu.common.CookieUtils;
//import com.green.fefu.security.JwtTokenProvider;
//import com.green.fefu.security.MyUserDetails;
//import com.green.fefu.user.model.SignInUser;
//import com.green.fefu.user.model.UserInfo;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.mindrot.jbcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private UserMapper mapper;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final AppProperties appProperties;
//    private final CookieUtils cookieUtils;
//
//    public UserInfo getParentInfo(HttpServletResponse res, SignInUser p) {
//        UserInfo info=mapper.getParentInfo(p);
//        if(info==null){
//            throw new RuntimeException("일치하는 회원을 찾을 수 없습니다.");
//        }else if(!passwordEncoder.matches(p.getUpw(), info.getUpw())){
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//        }
//
//        MyUserDetails myUserDetails=new MyUserDetails();
//
//        String accessToken=jwtTokenProvider.generateAccessToken(myUserDetails);
//        String refreshToken=jwtTokenProvider.generateRefreshToken(myUserDetails);
//
//        int refreshTokenMaxAge=appProperties.getJwt().getRefreshTokenCookieMaxAge();
//        cookieUtils.deleteCookie(res, appProperties.getJwt().getRefreshTokenCookieName());
//        cookieUtils.setCookie(res, appProperties.getJwt().getRefreshTokenCookieName(),refreshToken, refreshTokenMaxAge);
//
//
//        return info;
//    }
//}
