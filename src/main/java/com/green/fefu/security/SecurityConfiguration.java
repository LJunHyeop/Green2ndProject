package com.green.fefu.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity.sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS/*상태 없음*/)) //jwt를 할 것이기 때문에 필요 X
                .httpBasic(http->http.disable())
                .formLogin(form->form.disable())
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/user/sign-up", "/api/user/sign-in",
                                "/swagger","/swagger-ui/**", "/v3/api-docs/**",
                                "/", "/index.html",
                                "/css/**","/js/**","/static/**",
                                "/sign-up", "sign-in", "/profile/*", "/feed")
                        .permitAll()
                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
