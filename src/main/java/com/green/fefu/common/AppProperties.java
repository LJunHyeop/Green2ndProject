//package com.green.fefu.common;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//
//@Getter
//@ConfigurationProperties(prefix = "app")
//public class AppProperties {
//    private final Jwt jwt=new Jwt();
//
//    @Getter
//    @Setter
//    public static class Jwt{
//        private String secret;
//        private String headerSchemaName;
//        private String tokenType;
//        private long accessTokenExpiry;
//        private long refreshTokenExpiry;
//        private long refreshTokenCookieMaxAge;
//
//        public void setRefreshTokenExpiry(long refreshTokenExpiry){
//            this.refreshTokenExpiry=refreshTokenExpiry;
//            this.refreshTokenCookieMaxAge=(int)(refreshTokenExpiry*0.001);
//        }
//    }
//}
