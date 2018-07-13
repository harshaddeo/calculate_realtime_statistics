package com.calculate.realtime.statistics.exception;


public enum ErrorCode {

    VALIDATION_EMPTY_REQUEST_BODY(1001, "Empty request body"),

    VALIDATION_MISSING_TIMESTAMP(1002, "Missing timestamp field"),

    VALIDATION_MISSING_AMOUNT(1003, "Missing amount field"),

    UNEXPECTED_ERROR(9999, "Internal Server Error");

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

}