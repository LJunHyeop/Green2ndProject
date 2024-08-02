package com.green.fefu.admin.model.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.green.fefu.admin.model.dataset.ExceptionMsgDataSet.*;


@Getter
@Setter
@ToString
public class UpdateUserReq {
    @Min(value = 1, message = DIVISION_CODE_ERROR)
    @Max(value = 2, message = DIVISION_CODE_ERROR)
    private int p;
    @Positive(message = PK_DATA_ERROR)
    private Long pk;
}
