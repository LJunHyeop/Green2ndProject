package com.green.fefu.notice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNoticeReq {
    //@Schema(name="class_id")
    private long classId;
}
