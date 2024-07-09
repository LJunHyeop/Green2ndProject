package com.green.fefu.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Setter
@EqualsAndHashCode
public class PostNoticeReq {
    @JsonIgnore
    //@JsonProperty("tea_id")
    private long teaId;

    private String title;
    private String content;

    @JsonIgnore
    //@JsonProperty("class_id")
    private long classId;

    @ConstructorProperties({"tea_id", "title", "content", "class_id"})
    public PostNoticeReq(long teaId, String title, String content, long classId){
        this.teaId=teaId;
        this.title=title;
        this.content=content;
        this.classId=classId;
    }
}
