package com.pijukebox.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception exception, HttpServletRequest request) {
        return String.format("I have the message '%s' for %s", exception.getMessage(), request.getRemoteAddr());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    public String handleIOException(IOException exception, HttpServletRequest request) {
        return String.format("I have the message '%s' for %s", exception.getMessage(), request.getRemoteAddr());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchElementException.class)
    public String handleIOException(NoSuchElementException exception, HttpServletRequest request) {
        return String.format("I have the message '%s' for %s", exception.getMessage(), request.getRemoteAddr());
    }
}
