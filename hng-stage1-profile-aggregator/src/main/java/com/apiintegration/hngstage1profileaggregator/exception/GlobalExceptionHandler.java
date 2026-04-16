package com.apiintegration.hngstage1profileaggregator.exception;

import com.apiintegration.hngstage1profileaggregator.dtos.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiRequestLimitError.class)
    public ResponseEntity<ErrorResponse> handleApiRequestLimitError(ApiRequestLimitError e){
        return ResponseEntity.status(429).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e){
        String message = e.getConstraintViolations()
                    .iterator()
                    .next()
                    .getMessage();

            if ("String is only allowed".equals(message)) {
                throw new RuntimeException("Pattern is invalid");
            }
        return ResponseEntity.status(422).body(new ErrorResponse(e.getMessage()));
    }
    @ExceptionHandler(ApiResponseException.class)
    public ResponseEntity<ErrorResponse> handleApiResponseException(ApiResponseException e){
        return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(GenderNullException.class)
    public ResponseEntity<ErrorResponse> handleGenderNullException(GenderNullException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage()));
    }
}
