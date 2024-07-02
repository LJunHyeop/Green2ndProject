package com.green.fefu.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties(prefix = "app") //yaml
public class AppProperties {
    private final Jwt jwt=new Jwt();
    //JWT 토큰
    @Getter
    @Setter
    public static class Jwt{
        //대소문자 구분 X
        private String secret;
        private String headerSchemaName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private long refreshTokenCookieMaxAge;

        public void setRefreshTokenExpiry(long refreshTokenExpiry){
            this.refreshTokenExpiry=refreshTokenExpiry;
            this.refreshTokenCookieMaxAge=(int)(refreshTokenExpiry*0.001);
        }
    }
}
