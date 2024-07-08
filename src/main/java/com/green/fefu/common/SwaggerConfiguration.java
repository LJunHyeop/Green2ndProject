package com.green.fefu.common;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@OpenAPIDefinition(
        info = @Info(
                title = "2차 프로젝트",
                description = "모르겟다 ; ",
                version = "v0.0.1"

        )
)

public class SwaggerConfiguration {
}
