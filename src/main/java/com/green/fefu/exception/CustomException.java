package com.green.fefu.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException {
    private final ErrorCode errorCode;
}
