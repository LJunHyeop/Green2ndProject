package com.green.fefu.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder // 생성자 선택적 생성
public class ResultDto<T> {
    @Schema(example = "OK")
    private HttpStatus statusCode;
    @Schema(example = "완료")
    private String resultMsg;
    private T resultData;
}
