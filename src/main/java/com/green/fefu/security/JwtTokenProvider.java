//package com.green.fefu.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.green.fefu.common.AppProperties;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import io.jsonwebtoken.security.Keys;
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Slf4j
//@Component
//public class JwtTokenProvider { //JWT 토큰을 제공하는 것
//    private final ObjectMapper om; //JSON과 객체를 서로 매칭
//    private final AppProperties appProperties; //JTW의 설정값을 저장
//    private final SecretKey secretKey; //암호화 복호화의 도구
//
//    //생성자
//    public JwtTokenProvider(ObjectMapper om, AppProperties appProperties){
//        this.om=om;
//        this.appProperties = appProperties;
//        this.secretKey=Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties/*JWT 값 설정 객체*/.getJwt()/*내부 클래스*/.getSecret/*String*/()));
//    }
//
//    //AccessToken 생성 //MyUser?
//    public String generateAccessToken(UserDetails userDetails/*부모는 자식객체 담기 가능(PK값과 역할)*/){
//        return /*String*/generateRefreshToken(userDetails/*유저의 정보*/,
//                            appProperties.getJwt().getRefreshTokenExpiry()/*리프레시의 생존시간*/);
//    }
//    private String generateRefreshToken(UserDetails userDetails, long tokenValidMilliSecond)/*위의 메소드에서 호출*/{
//        return Jwts.builder()
//                .issuedAt/*토큰 발급시간*/(new Date(System.currentTimeMillis()))
//                .expiration/*토큰 만료시간*/(new Date(System.currentTimeMillis()/*long*/+tokenValidMilliSecond)/*long*/)
//                .claims/*내용에 담기는 정보*/(createClaims(userDetails)/*아래 메소드*/)
//                .signWith(secretKey, Jwts/*클래스의*/.SIG/*내부 클래스*/.HS512/*멤버필드-Algorithm*/)
//                .compact(); //토큰 생성
//    }
//
//    private Claims createClaims(UserDetails userDetails){
//        try{
//            String json = om.writeValueAsString/*객체->JSON*/(userDetails);
//            return Jwts.claims()/*리턴타입:jwtBuilder(extends MapMutator-interface)*/
//                        .add("signedUser", json/*String*/).build(); //그거를 claims에 추가
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    //------------------------------------------------------------------------------------------------------------------
//
//    /*아래에서 호출*/
//    public Claims/*내용(Payload)에 담김*/ getAllClaims(String token){
//        return Jwts
//                .parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    /*토큰에서 유저 정보 뽑아냄*/
//    private UserDetails getUserDetailsFromToken(String token){
//        try{
//            Claims claims=getAllClaims/*메소드 호출*/(token); //payload에서 Claims를 빼냄
//            String json=(String)claims.get("signedUser"); //claims에서 "signedUser"로 저장된 정보 추출 및 String으로 저장
//            return om.readValue/*String->객체*/(json, MyUserDetails.class);
//        } catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    /*토큰의 인증 정보를 가져옴 - filter에서 세 번째로 호출되는 메소드*/
//    //SpringContextHolder에 저장할 자료를 세팅(나중에 Service단에서 빼서 쓸 값, 로그인 처리, 인가 처리를 위함)
//    public Authentication/*인증*/ getAuthentication(String token) {
//        UserDetails/*자손의 객체 주소값 담음*/ userDetails = getUserDetailsFromToken/*메소드 호출*/(token);
//        return userDetails == null
//                ? null
//                : new UsernamePasswordAuthenticationToken(userDetails,
//                null,
//                userDetails.getAuthorities/*Collection을 return*/());
//
//    }
//    /*토큰이 만료하였는지 확인 - filter에서 두 번째로 호출되는 메소드*/
//    public boolean isValidateToken(String token){
//        try{ //기존: 만료시간이 경과하였으면 True / 경과하지 않았으면 False
//            return !getAllClaims(token)/*토큰 속 Payload 속 Claims에서*/.getExpiration()/*만료시간*/.before(new Date()/*현재시간*/);
//            //!(not): 만료시간이 경과하지 않았으면 True / 만료시간이 경과하였으면 False
//        }catch(Exception e){
//            return false;
//        }
//    }
//
//    /*토큰을 꺼내온다 - filter에서 가장 먼저 호출되는 메소드*/
//    public String resolveToken(HttpServletRequest/*요청*/ req){
//        String jwt=req.getHeader/*Request의 Header 중에서*/((appProperties.getJwt().getHeaderSchemaName())/*"authorization"*/);
//        if(jwt==null){return null;}
//        // 해당 문자열로 시작하면 True, 그렇지 않으면 False
//        if(!jwt.startsWith/*파라미터 문자열로 시작하는지*/(appProperties.getJwt().getTokenType())/*"Bearer"(JWT 토큰을 의미)*/){
//        // 해당 문자열로 시작하지 않으면 True, 시작하면 True
//            return null; //=> 그 문자열로 시작을 안 하면 null
//        }
//        //값이 무사히 들어오고, Bearer로 시작하는 JWT token이다 => JWT token 부분만 리턴
//        return jwt.substring(appProperties.getJwt().getTokenType()/*Bearer*/.length()/*index return*/)
//                                                                                    .trim()/*양쪽 빈칸 제거*/;
//    }
//}
