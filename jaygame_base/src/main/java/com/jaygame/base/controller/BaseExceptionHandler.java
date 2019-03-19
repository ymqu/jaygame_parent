package com.jaygame.base.controller;

import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e){
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public Result ExpiredJwtExceptionHandler(ExpiredJwtException e){
        return new Result(false, StatusCode.JWTEXPIRED, e.getMessage());
    }
}
