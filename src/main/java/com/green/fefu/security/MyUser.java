package com.green.fefu.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


// 현재 필요없는 어노테이션
// Json에 더 많은 속성이 있는데 MyUser에 없는 멤버필드는 무시하고 객체생성 하는 것
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
// JSON > Object 할때 기본 생성자 필요
@NoArgsConstructor
@Builder
public class MyUser {
    // 로그인한 사용자의 PK값
    private long userId;

    // 사용자 권한
    private String role;
}
