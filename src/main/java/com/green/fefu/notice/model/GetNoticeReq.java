package com.green.fefu.notice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GetNoticeReq {
    //@Schema(name="class_id")
    private long classId;
}
