package com.green.fefu.parents.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class GetSignaturePicReq {
    private long studentPk;
    private int year ;
    private int semester ;
    private int examSign ;
}