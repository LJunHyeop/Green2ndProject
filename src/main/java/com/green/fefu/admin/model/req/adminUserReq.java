package com.green.fefu.admin.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class adminUserReq {
    @Schema(example = "1", description = "1-> 부모List, 2-> 선생List")
    private Integer p;
    @Schema(example = "1", description = "승인/반려 할 사용자 pk값")
    private Long pk;
}
