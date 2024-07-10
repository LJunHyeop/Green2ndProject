package com.green.fefu.score;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.fefu.CharEncodingConfiguration;
import com.green.fefu.score.model.InsScoreReq;
import com.green.fefu.security.MyUser;
import com.green.fefu.security.MyUserDetails;
import com.green.fefu.security.jwt.JwtAuthenticationFilter;
import com.green.fefu.security.jwt.JwtTokenProviderV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@Import({CharEncodingConfiguration.class})
@WebMvcTest({ScoreControllerImpl.class})
class ScoreControllerTest {
    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @MockBean private ScoreServiceImpl service;
    @Autowired
    private WebApplicationContext wac;
    @MockBean
    private JwtTokenProviderV2 tokenProvider;
    @MockBean
    private JwtAuthenticationFilter filter;
    private final String BASE_URL = "/api/Score";

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @WithMockUser(username = "testUser", roles = {"USER"})
    @Test
    void postScore() throws Exception {
        Authentication auth = mock(Authentication.class) ;
        MyUserDetails myUserDetails = mock(MyUserDetails.class) ;
        InsScoreReq p = new InsScoreReq();
        p.setSemester(1);
        p.setYear(1);
        p.setExam(1);
        p.setName("김철수철수");
        p.setMark(1);
        p.setStuId(1);
        String token = "accessToken";
        String reqJson = om.writeValueAsString(p);
        given(service.postScore(p)).willReturn(1L);

        MyUser myUser = new MyUser() ;
        myUser.setUserId(p.getScoreId());

        given(tokenProvider.getAuthentication(token)).willReturn(auth);
        given(auth.getPrincipal()).willReturn(myUserDetails);
        given(myUserDetails.getMyUser()).willReturn(myUser);

        Map<String, Object> expected = new HashMap<>();
        String expectedResJson = om.writeValueAsString(expected);

        mvc.perform(
                        post(BASE_URL)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(reqJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson))
                .andDo(print());

        verify(service).postScore(p);
    }

}
