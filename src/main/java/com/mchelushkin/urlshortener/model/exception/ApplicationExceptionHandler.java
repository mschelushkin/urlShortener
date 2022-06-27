package com.mchelushkin.urlshortener.model.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.mchelushkin.urlshortener.model.dto.ErrorResponse;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidUrlException e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

}
