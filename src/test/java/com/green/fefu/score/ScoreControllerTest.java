package com.green.fefu.score;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.fefu.CharEncodingConfiguration;
import com.green.fefu.score.model.InsScoreReq;
import com.green.fefu.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private JwtAuthenticationFilter filter;
    private final String BASE_URL = "/api/score";

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @WithMockUser(username = "testUser", roles = {"USER"})
    @Test
    void postScore() throws Exception {

        InsScoreReq p = new InsScoreReq();
        p.setSemester(1);
        p.setYear(1);
        p.setExam(1);
        p.setName("김철수철수");
        p.setMark(1);
        p.setStuId(1);

        String reqJson = om.writeValueAsString(p);
        given(service.postScore(p)).willReturn(1L);

        Map<String, Object> expected = new HashMap<>();
        expected.put("statusCode", HttpStatus.OK);
        expected.put("resultMsg", "등록성공");
        expected.put("resultData", 1L);

        String expectedResJson = om.writeValueAsString(expected);

        mvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(reqJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson))
                .andDo(print());

        verify(service).postScore(p);
    }
}
