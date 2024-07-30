package com.green.fefu.parents.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SocialUserEntity {
    private long socialUserPk;

    @Schema(description = "로컬 사용자 ID")
    private long userId;

    @Schema(description = "소셜 로그인 제공자")
    private String provider;

    @Schema(description = "소셜 로그인 ID")
    private String socialId;

    @Schema(description = "소셜 로그인 이메일")
    private String socialEmail;

    @Schema(description = "소셜 로그인 이름")
    private String socialName;
}
