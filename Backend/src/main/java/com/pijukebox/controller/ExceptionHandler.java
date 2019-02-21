package com.pijukebox.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(String.format("I have the message '%s' for %s", exception.getMessage(), request.getRemoteAddr()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException exception, HttpServletRequest request) {
        return new ResponseEntity<>(String.format("I have the message '%s' for %s", exception.getMessage(), request.getRemoteAddr()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleIOException(NoSuchElementException exception, HttpServletRequest request) {
        return new ResponseEntity<>(String.format("I have the message '%s' for %s", exception.getMessage(), request.getRemoteAddr()), HttpStatus.NO_CONTENT);
    }
}
