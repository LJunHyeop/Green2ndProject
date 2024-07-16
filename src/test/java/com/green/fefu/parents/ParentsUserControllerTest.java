package com.green.fefu.parents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.fefu.CharEncodingConfiguration;
import com.green.fefu.common.AppProperties;
import com.green.fefu.parents.model.PostParentsUserReq;
import com.green.fefu.security.SecurityConfiguration;
import com.green.fefu.security.jwt.JwtTokenProviderV2;
import com.green.fefu.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.green.fefu.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.green.fefu.sms.SmsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ParentsUserControllerImpl.class})
@Import({CharEncodingConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)  // Spring Security 필터 비활성화
@WebAppConfiguration
class ParentsUserControllerTest {
    @Autowired private ObjectMapper om;
    @Autowired private MockMvc mockMvc;
    @MockBean private ParentsUserServiceImpl service;
    @MockBean private JwtTokenProviderV2 tokenProvider;
    @MockBean private SmsService smsService;

    private final String BASE_URL = "/api/user/parents";

    @Test
    @DisplayName("회원가입 TDD")
    void testPostParents() throws Exception {
        // Given
        PostParentsUserReq req = new PostParentsUserReq() ;
        req.setParentsId(1) ;
        req.setStudentPk(1) ;
        req.setNm("길동") ;
        req.setEmail("rffrfr@gmail.com") ;
        req.setConnet("부") ;
        req.setPhone("010-1235-4567") ;
        req.setUid("parent745") ;
        req.setUpw("Test1234!@#$");

        // 설정 필요한 필드들 추가
        given(service.postParentsUser(any(PostParentsUserReq.class))).willReturn(1) ;
        String json = om.writeValueAsString(req) ;

        Map<String, Object> expectedMap = new HashMap();
        expectedMap.put("statusCode", HttpStatus.OK);
        expectedMap.put("resultMsg", HttpStatus.OK.toString());
        expectedMap.put("resultData", 1);
        String expectedJson = om.writeValueAsString(expectedMap);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/parents/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson)) // JSON 요청 바디
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
        verify(service).postParentsUser(req) ;
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