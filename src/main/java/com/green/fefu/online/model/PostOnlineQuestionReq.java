package com.green.fefu.online.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    @Schema(example = "(TEST)내가 그의 이름을 불러주었을 때, 그는 나에게로 와서 꽃이 되었다.", description = "내용을 기입하는 부분입니다.")
    private String contents;
    @Schema(example = "[\"(TEST)김춘수\",\"(TEST)윤동주\",\"(TEST)이광수\",\"(TEST)진소월\",\"(TEST)나태주\"]",
                description = "보기 번호 없이 내용만 입력하는 부분입니다.")
    private List<String> multiple=new ArrayList();

    @Schema(example = "[1,5]", description = "정답이 되는 보기 번호가 들어가는 부분입니다.")
    private List<Integer> answer=new ArrayList();

    @Schema(example="3", description="난이도를 표시하는 항목입니다.")
    private int level;

    @JsonIgnore
    private Long teacherPk;

//    @JsonIgnore
//    private Long classId;
}
