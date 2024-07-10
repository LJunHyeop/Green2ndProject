package com.green.fefu.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostNoticeReq {
    @JsonProperty("tea_id")
    //@JsonIgnore
    private long teaId;

    private String title;
    private String content;


    //@JsonProperty("class_id")
    @JsonIgnore
    private long classId;


}
