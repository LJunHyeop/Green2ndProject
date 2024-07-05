package com.green.fefu.notice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
