package com.green.fefu.common.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Builder
@Getter
public class ResultDto<T>{
    private HttpStatusCode statusCode;
    private String resultMsg;
    private T result;
}
