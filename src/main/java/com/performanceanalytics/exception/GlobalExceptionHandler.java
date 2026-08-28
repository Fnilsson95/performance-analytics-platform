package com.performanceanalytics.exception;


import com.performanceanalytics.exercise.DuplicateExerciseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice applies globally, for every controller in the
// app. Any exception thrown (and not caught) inside a controller method
// gets routed here first, checked against the @ExceptionHandler methods
// below, before falling through to Springs default error response.
@RestControllerAdvice
public class GlobalExceptionHandler {


    // Whenever a DuplicateExerciseException comes up from any controller this method
    // runs instead of Spring's default 500 fallback.
    @ExceptionHandler(DuplicateExerciseException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateExercise(DuplicateExerciseException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
