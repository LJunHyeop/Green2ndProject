package com.green.fefu.notice;

import com.green.fefu.notice.model.*;

import java.util.List;

public interface NoticeService {
    int postNotice(PostNoticeReq p);
    List<GetNoticeRes> getNotice(GetNoticeReq p);
    int putNotice(PutNoticeReq p);

    int deleteNotice(DeleteNoticeReq p);
}
