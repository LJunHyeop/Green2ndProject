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

    //TDD를 위한 메소드
    List<GetNoticeRes> getNoticeForTDD();
}
