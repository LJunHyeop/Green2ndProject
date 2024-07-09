package com.green.fefu.notice;

import com.green.fefu.notice.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    int postNotice(PostNoticeReq p);
    List<GetNoticeRes> getNotice(GetNoticeReq p);
    int putNotice(PutNoticeReq p);
    int deleteNotice(DeleteNoticeReq p);

    //선생님의 담당 반을 찾는 메소드
    int teacherHomeroom(long teaId);

    //TDD를 위한 메소드
    List<GetNoticeRes> getNoticeForTDD();

    List<GetNoticeRes> getNoticeForTDDJustOne(GetOneNoticeForTDD p);
}
