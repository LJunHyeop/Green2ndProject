package com.green.fefu.online.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class PostOnlineQuestionReq {
    @JsonIgnore
    private Long queId;

    @Schema(example = "(TEST)다음 시의 저자로 옳은 사람을 고르시오.", description = "문항을 입력하는 부분입니다.")
    private String question;
    @Schema(example = "(TEST)내가 그의 이름을 불러주었을 때, 그는 나에게로 와서 꽃이 되었다.", description = "보기 번호가 들어가는 부분입니다.")
    private String contents;

    private List<String> multiple;

    @Schema(example = "1", description = "보기 번호가 들어가는 부분입니다.")
    private int answer;

    @JsonIgnore
    private Long teacherPk;
}
