package com.green.fefu.exception;

import com.green.fefu.common.ResultDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

@Builder //superBuilder
public class MyErrorResponse <T> {
    private HttpStatus statusCode;
    private String resultMsg;
    private T resultData;
    private final List<ValidationError> valids;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError{
        private final String field;
        private final String message;

        public static ValidationError of (final FieldError fieldError){
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
