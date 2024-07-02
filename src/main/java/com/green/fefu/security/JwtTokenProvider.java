package com.green.fefu.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.fefu.common.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider { //JWT 토큰을 제공하는 것
    private final ObjectMapper om; //JSON과 객체를 서로 매칭
    private final AppProperties appProperties; //JTW의 설정값을 저장
    private final SecretKey secretKey; //암호화의 도구

    //생성자
    public JwtTokenProvider(ObjectMapper om, AppProperties appProperties){
        this.om=om;
        this.appProperties = appProperties;
        this.secretKey=Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties/*이 객체 안*/.getJwt()/*내부 클래스*/.getSecret/*String*/()));
    }

    //AccessToken 생성
    public String generateAccessToken(UserDetails userDetails/*부모는 자식객체 담기 가능(PK값과 역할)*/){
        return /*String*/generateToken(userDetails, appProperties.getJwt().getRefreshTokenExpiry());//리프레시가 살아있는지 확인
    }
    private String generateToken(UserDetails userDetails, long tokenValidMilliSecond){
        return Jwts.builder()
                .issuedAt/*토큰 발급시간*/(new Date(System.currentTimeMillis()))
                .expiration/*토큰 만료시간*/(new Date(System.currentTimeMillis()/*long*/+tokenValidMilliSecond)/*long*/)
                .claims/*내용에 담기는 정보*/(createClaims(userDetails)/*아래 메소드*/)
                .signWith(secretKey, Jwts/*클래스의*/.SIG/*내부 클래스*/.HS512/*멤버필드-Algorithm*/)
                .compact();
    }

    private Claims createClaims(UserDetails userDetails){
        try{
            String json = om.writeValueAsString(userDetails); //객체를 json으로
            return Jwts.claims().add("signedUser", json/*String*/).build(); //그거를 claims에 추가
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Claims/*내용(Payload)에 담김*/ getAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //토큰에서 유저 정보
    private UserDetails getUserDetailsFromToken(String token){
        try{
            Claims claims=getAllClaims(token); //뭐 어떻게 빼내고
            String json=(String)claims.get("signedUser"); //claims에서 "signedUser"로 저장된 정보를 저장(48줄)
            return om.readValue/*String->객체*/(json, MyUserDetails.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //SpringContextHolder에 저장할 자료를 세팅(나중에 Service단에서 빼서 쓸 값, 로그인 처리, 인가 처리를 위함)
    public Authentication/*인증*/ getAuthentication(String token) {
        UserDetails/*자손의 객체 주소값 담음*/ userDetails = getUserDetailsFromToken(token);
        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());

    }

    public boolean isValidateToken(String token){
        try{ //기존: 만료시간이 경과하였으면 True / 경과하지 않았으면 False
            return !getAllClaims(token)/*토큰 속 Payload 속 Claims에서*/.getExpiration()/*만료시간*/.before(new Date()/*현재시간*/);
            //!(not): 만료시간이 경과하지 않았으면 True / 만료시간이 경과하였으면 False
        }catch(Exception e){
            return false;
        } //filter에서 호출 중
    }
    public String resolveToken(HttpServletRequest req){
        String jwt=req.getHeader/*Request의 Header를 저장*/((appProperties.getJwt().getHeaderSchemaName())/*이런 이름의?*/);
        if(jwt==null){return null;}
        if(!jwt.startsWith(appProperties.getJwt().getTokenType())){
            //토큰에 저장된 문자열이 Bearer(jwt토큰이라는 의미)로 시작하면 True, 아니면 False
            return null;
        }
        return jwt.substring(appProperties.getJwt().getTokenType().length()).trim();
    }
}
