package com.green.fefu.notice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.fefu.CharEncodingConfiguration;
import com.green.fefu.notice.model.GetNoticeReq;
import com.green.fefu.notice.model.GetNoticeRes;
import com.green.fefu.notice.model.PostNoticeReq;
import com.green.fefu.notice.model.PutNoticeReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class) //문자열 중 한글이 깨지지 않게 하기 위함
@WebMvcTest(NoticeControllerImpl.class) //MOCKMvc를 객체 등록 주입
class NoticeControllerTest {
    @MockBean
    private NoticeService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;



    @Test
    void postNotice() throws Exception{ //Json 통신! (HTTP통신의 기본)
        int result=1;
        PostNoticeReq p=new PostNoticeReq(100, "제목 100", "내용 100", 100);
        String reqJson=om.writeValueAsString(p);

        given(service.postNotice(p)).willReturn(result);

        Map<String, Object> expectedRes= new HashMap();//VO개념 : 사용 방법만 다를 뿐 용도는 같음
        expectedRes.put("statusCode", HttpStatus.OK);
        expectedRes.put("resultMsg", "성공적으로 업로드 되었습니다.");
        expectedRes.put("result", result);

        //제네릭은 레퍼런스 타입으로 줘야함
        //프라모티브 타입 래퍼 타입 = 값만 담는 용도 VS 주소값을 줘서 메소드 등 활용 가능

        String expectedResJson=om.writeValueAsString(expectedRes);
        //왜 JSON으로 바꾸는가? 제대로 보냈는지 통신 하기 위해

        mvc.perform(post("/api/notice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson)
                ).andExpect(status().isOk())
                .andExpect(content().json(expectedResJson))
                .andDo(print());


        verify(service).postNotice(p);

    }


    @Test
    void getNotice() throws Exception{ //쿼리스트링
        List<GetNoticeRes> result=new ArrayList();
        GetNoticeRes res1=new GetNoticeRes();
        res1.setNoticeId(100); res1.setTitle("제목 100"); res1.setContent("내용 100");
        res1.setClassId(100); res1.setTeaId(100);
        GetNoticeRes res2=new GetNoticeRes();
        res2.setNoticeId(200); res2.setTitle("제목 200"); res1.setContent("내용 200");
        res2.setClassId(200); res2.setTeaId(200);
        result.add(res1); result.add(res2);

        GetNoticeReq req1=new GetNoticeReq();
        req1.setClassId(100);

        //쿼리스트링을 짠다
        MultiValueMap<String, String> params=new LinkedMultiValueMap();
        params.add("class_id", String.valueOf(req1.getClassId()));

        //객체를 넣었을 때 리턴할 값 지정
        given(service.getNotice(req1)).willReturn(result);

        //ResultDto
        Map<String, Object> map=new HashMap();
        map.put("statusCode", HttpStatus.OK);
        map.put("resultMsg", "성공적으로 조회되었습니다.");
        map.put("result", result);
        String resultJson=om.writeValueAsString(map);

        //통신 시도
        mvc.perform(get("/api/notice")
                        .params(params)
                ).andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andDo(print());

        verify(service).getNotice(req1);

    }

    @Test
    void putNotice() throws Exception{
        PutNoticeReq req1=new PutNoticeReq();
        req1.setNoticeId(100); req1.setTitle("제목제목"); req1.setContent("내용내용"); req1.setTeaId(100);
        String reqJson=om.writeValueAsString(req1);

    }


    @Test
    void deleteNotice() {
    }
}