package com.green.fefu.parents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.fefu.CharEncodingConfiguration;
import com.green.fefu.parents.model.PostParentsUserReq;
import com.green.fefu.security.SecurityConfiguration;
import com.green.fefu.security.jwt.JwtTokenProviderV2;
import com.green.fefu.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.green.fefu.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.green.fefu.sms.SmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ParentsUserControllerImpl.class})
@Import({CharEncodingConfiguration.class, SecurityConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)  // Spring Security 필터 비활성화
@WebAppConfiguration
class ParentsUserControllerTest {
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @MockBean private ParentsUserServiceImpl service;
    @MockBean private JwtTokenProviderV2 tokenProvider;
    @MockBean private SmsService smsService;
    @MockBean private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    @MockBean private UserDetailsService userDetailsService;
    @MockBean private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @MockBean private com.green.fefu.security.oauth2.OAuth2AuthenticationRequestBasedOnCookieRepository oAuth2AuthenticationRequestBasedOnCookieRepository;
    @MockBean private com.green.fefu.security.oauth2.MyOAuth2UserService myOAuth2UserService;
    private final String BASE_URL = "/api/user/parents";

    @Test
    @DisplayName("회원가입 TDD")
    void testPostParents() throws Exception {
        // Given
        PostParentsUserReq req = new PostParentsUserReq();
        // 설정 필요한 필드들 추가
        given(service.postParentsUser(any(PostParentsUserReq.class))).willReturn(1);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/parents/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"field1\":\"value1\",\"field2\":\"value2\"}")) // JSON 요청 바디
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void getParentsUser() {
    }

    @Test
    void patchParentsUser() {
    }

    @Test
    void getFindId() {
    }

    @Test
    void patchPassword() {
    }

    @Test
    void signInPost() {
    }

    @Test
    void getAccessToken() {
    }
}