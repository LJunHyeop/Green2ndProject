package com.green.fefu.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
@EqualsAndHashCode
public class DeleteNoticeReq {
    @Schema(name="notice_id")
    private long noticeId;
    //@JsonIgnore
    @Schema(name="tea_id")
    private long teaId;
    @Schema(name="class_id")
    private long classId;

    @ConstructorProperties({"notice_id", "tea_id", "class_id"})
    public DeleteNoticeReq(long noticeId, long teaId, long classId){
        this.noticeId=noticeId;
        this.teaId=teaId;
        this.classId=classId;
    }
}
