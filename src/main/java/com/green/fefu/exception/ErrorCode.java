package com.green.fefu.exception;

public interface ErrorCode {
    String name();
    String getHttpStatus();
    String getMessage();

}
