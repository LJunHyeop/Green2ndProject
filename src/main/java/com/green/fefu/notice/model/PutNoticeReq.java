package com.green.fefu.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutNoticeReq {
    //@JsonIgnore
    private long noticeId;
    //@JsonIgnore
    private long teaId;

    private String title;
    private String content;

}
