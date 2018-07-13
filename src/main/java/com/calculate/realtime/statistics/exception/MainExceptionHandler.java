package com.calculate.realtime.statistics.exception;

import com.calculate.realtime.statistics.model.ErrorDesc;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MainExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDesc handleException(ApplicationException exception) {
        return new ErrorDesc(exception.getErrorCode(), exception.getErrorMessage());
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDesc handleException(Exception exception) {
        return new ErrorDesc(ErrorCode.UNEXPECTED_ERROR.getCode(),
                ErrorCode.UNEXPECTED_ERROR.getMessage());
    }
}