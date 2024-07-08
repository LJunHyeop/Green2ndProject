package com.green.fefu.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PutNoticeReq {
    //@JsonIgnore
    private long noticeId;
    //@JsonIgnore
    private long teaId;

    private String title;
    private String content;

}
