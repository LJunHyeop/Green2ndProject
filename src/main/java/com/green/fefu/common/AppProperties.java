package com.green.fefu.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties(prefix = "app") //yaml의 값을 매칭해줌
public class AppProperties {
    private final Jwt jwt=new Jwt();
    //JWT 토큰
    @Getter
    @Setter
    public static class Jwt{
        //대소문자 구분 X
        private String secret;
        private String headerSchemaName; //인증 토큰을 서버로 보낼 때 쓰는 헤더의 이름을 yaml에 저장
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private int refreshTokenCookieMaxAge;
        private String refreshTokenCookieName;

        public void setRefreshTokenExpiry(long refreshTokenExpiry){
            this.refreshTokenExpiry=refreshTokenExpiry;
            this.refreshTokenCookieMaxAge=(int)(refreshTokenExpiry*0.001);
        }
    }
}
