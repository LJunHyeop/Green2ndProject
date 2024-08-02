package com.green.fefu.online.model;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class PostOnlineQuestionReq {
    @JsonIgnore
    private Long queId;

    private String question;
    private int answer;
}
