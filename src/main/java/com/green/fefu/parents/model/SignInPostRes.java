package com.green.fefu.parents.model;

import com.green.fefu.entity.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInPostRes {
    @Schema(example = "1", description = "pk")
    private long parentsId;
    @Schema(example = "홍길동", description = "이름")
    private String nm;

    @Schema(description = "자녀 List")
    private List<StudentRes> studentList ;

    private String accessToken;
}
