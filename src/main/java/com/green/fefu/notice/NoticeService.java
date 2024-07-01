package com.green.fefu.notice;

import com.green.fefu.notice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper mapper;

    int postNotice(PostNoticeReq p){
        return mapper.postNotice(p);
    }
    List<GetNoticeRes> getNotice(GetNoticeReq p){
        return mapper.getNotice(p);
    }
    int putNotice(PutNoticeReq p){
        return mapper.putNotice(p);
    }

    int deleteNotice(DeleteNoticeReq p){
        return mapper.deleteNotice(p);
    }
}
