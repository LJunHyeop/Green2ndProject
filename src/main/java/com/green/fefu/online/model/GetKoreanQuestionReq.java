package com.green.fefu.online.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetKoreanQuestionReq {
    private Long studentPk;
    private String subject;
}
