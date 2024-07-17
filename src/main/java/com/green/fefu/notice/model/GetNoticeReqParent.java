package com.green.fefu.notice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GetNoticeReqParent extends GetNoticeReq{
    private long studentPk;
}
