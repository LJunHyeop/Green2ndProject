package com.green.fefu.online.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostOnlineQuestionReq {
    private Long queId;
    private String question;
    private int answer;
}
