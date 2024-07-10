package com.green.fefu.notice;

import com.green.fefu.notice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
    private final NoticeMapper mapper;


    public int postNotice(PostNoticeReq p){
        return mapper.postNotice(p);
    }
    public List<GetNoticeRes> getNotice(GetNoticeReq p){
        return mapper.getNotice(p);
    }
    public int putNotice(PutNoticeReq p){
        return mapper.putNotice(p);
    }

    public int deleteNotice(DeleteNoticeReq p){
        return mapper.deleteNotice(p);
    }
}
