package com.green.fefu.notice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;


@Getter
@Setter
@EqualsAndHashCode
public class GetNoticeReq {
    //@Schema(name="class_id")
    //@JsonProperty("class_id")
    private long classId;

    public GetNoticeReq(@BindParam("class_id")long classId) {
        this.classId = classId;
    }
}