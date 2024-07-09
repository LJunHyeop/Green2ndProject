package com.green.fefu.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private long classId;

    public GetNoticeReq(@BindParam("class_id")long classId) {
        this.classId = classId;
    }
}
