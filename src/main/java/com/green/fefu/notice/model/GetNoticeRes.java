package com.green.fefu.notice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GetNoticeRes {
    private long noticeId;
    private long teaId;
    private long classId;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private int state;
}
