package com.green.fefu.notice;

import com.green.fefu.notice.model.*;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;


@ActiveProfiles("tdd")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace/*testDB를 대체할지*/.NONE/*하지 않음*/)
class NoticeMapperTest {
    @Autowired //DI
    private NoticeMapper mapper;
    private final int DEFAULT_NUM=10;

    @Test
    void postNotice() {
        /*모든 열을 조회한 숫자가 일치하는지*/
        List<GetNoticeRes> all=mapper.getNoticeForTDD(); //10
        assertEquals(DEFAULT_NUM, all.size(),"기존 리스트의 값이 다름");

        /*영향 받은 행의 값이 1인지*/
        PostNoticeReq req1=new PostNoticeReq(100, "제목 100", "내용 100", 100);
        //PostNoticeReq req1=new PostNoticeReq(200, "제목 200", "내용 200", 200); //다른 값으로 확인
        int effect=mapper.postNotice(req1);
        assertEquals(1, effect, "영향 받은 행의 개수가 다름");

        /*기존 데이터에서 1이 추가 되는지*/
        List<GetNoticeRes> plusOne=mapper.getNoticeForTDD();
        assertEquals(DEFAULT_NUM +1, plusOne.size(), "기존데이터에서 1이 추가 되지 않음");

        /*마지막에 실제 그 데이터가 있는지*/
        GetNoticeRes res1=plusOne.get(10); //리스트 0번부터 시작
        PostNoticeReq tmp=new PostNoticeReq(res1.getTeaId(), res1.getTitle(), res1.getContent(), res1.getClassId());
        assertEquals(tmp, req1, "넣은 값과 추가된 값이 다름");

        /*실제 그 데이터가 업데이트 되었는지*/
        GetNoticeReq getReq1=new GetNoticeReq(100);
        getReq1.setClassId(100);
        List<GetNoticeRes> search=mapper.getNotice(getReq1);
    }

    @Test
    void getNotice() {
        /*이상한 번호를 넣었을 때 조회되지 않음*/
        GetNoticeReq req1=new GetNoticeReq(100);
        req1.setClassId(100);
        List<GetNoticeRes> res1=mapper.getNotice(req1);
        assertEquals(0, res1.size(), "조회되는 행이 있음");

        /*특정 값을 넣었을 때 n개 조회*/
        GetNoticeReq req2=new GetNoticeReq(100);
        req2.setClassId(2);
        List<GetNoticeRes> res2=mapper.getNotice(req2);
        assertEquals(2, res2.size(), "조회된 행의 개수가 다름");

        /*전체 조회 = TDD 전용*/
        List<GetNoticeRes> resAll=mapper.getNoticeForTDD();
        assertEquals(DEFAULT_NUM, resAll.size(), "전체 행의 개수가 다름");

        /*특정 값 조회+n번 행이 실제 그 행인지*/
        GetNoticeReq req3=new GetNoticeReq(100);
        req3.setClassId(3);
        List<GetNoticeRes> res3=mapper.getNotice(req3);
        assertEquals(2, res3.size(),"조회된 행의 개수가 다름");

        /*이거보다 좋은 방법이 있을 거 같은데*/
        GetNoticeRes onePick=res3.get(0);
        assertEquals(3, onePick.getNoticeId(), "PK 값이 다름");
        assertEquals(2, onePick.getTeaId(), "선생님의 PK 값이 다름");
        assertEquals("제목 3", onePick.getTitle(), "제목이 다름");
        assertEquals("내용 3", onePick.getContent(), "내용이 다름");
        assertEquals(3, onePick.getClassId(), "반의 PK 값이 다름");
    }

    @Test
    void putNotice() {
        /*영향받은 행의 값이 1인지*/
        PutNoticeReq req1=new PutNoticeReq();
        req1.setNoticeId(7);
        req1.setTitle("제목 변경");
        req1.setContent("내용 변경");
        req1.setTeaId(4);

        int effect=mapper.putNotice(req1);
        assertEquals(1, effect, "영향 받은 행이 1이 아님");

        /*실제 수정된 내용으로 조회했을 때 1개인지*/
        GetOneNoticeForTDD reqOne1=new GetOneNoticeForTDD();
        reqOne1.setTitle("제목 변경");
        reqOne1.setContent("내용 변경");

        List<GetNoticeRes> putOne=mapper.getNoticeForTDDJustOne(reqOne1);
        assertEquals(1, putOne.size(), "같은 내용의 행이 조회됨");

        /*그 조회된 1개가 수정과 내용이 같은지*/
        assertEquals(reqOne1.getTitle(), putOne.get(0).getTitle(), "제목이 수정되지 않음");
        assertEquals(reqOne1.getContent(), putOne.get(0).getContent(), "내용이 수정되지 않음");

        /*행의 수에 변화는 없는지*/
        List<GetNoticeRes> allNotice=mapper.getNoticeForTDD();
        assertEquals(DEFAULT_NUM, allNotice.size(), "행이 추가되거나 삭제되었음");

        /*수정 전 행이 사라졌는지*/
        assertNotEquals("제목 7", reqOne1.getTitle(), "제목이 다름");

        List<GetNoticeRes> resAll=mapper.getNoticeForTDD();
        for(GetNoticeRes res : resAll){
            boolean answer=res.getTitle().equals("제목 7"); //제목 7이라는 행이 있으면 true
            assertNotEquals(answer, true, "제목이 다름2");
        }
    }

    @Test
    void deleteNotice() {
        List<GetNoticeRes> allRes=mapper.getNoticeForTDD();
        assertEquals(DEFAULT_NUM, allRes.size(), "전체 행의 개수가 다름");

        /*삭제 됐는지*/
        DeleteNoticeReq req1 = new DeleteNoticeReq(1,1,1);
        int effect = mapper.deleteNotice(req1);
        assertEquals( 1, effect, "영향 받은 행이 다름");

        /*전체 행의 개수가 달라졌는지*/
        List<GetNoticeRes> allRes2=mapper.getNoticeForTDD();
        assertEquals(DEFAULT_NUM-1, allRes2.size(),"행의 변화가 이상함");

        /*지운 행이 없는지*/
        for(GetNoticeRes res: allRes2){
            boolean answer=(res.getNoticeId()!=req1.getNoticeId());
            assertEquals(answer, true,"지워져야 할 행이 남아있음");
        }
    }
}