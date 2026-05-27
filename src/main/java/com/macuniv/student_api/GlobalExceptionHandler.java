package com.macuniv.student_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<StudentDTO>> handleStudentNotFoundException(StudentNotFoundException ex)
    {
        ApiResponse<StudentDTO> error = ApiResponse.error(Arrays.asList(ex.getMessage()));
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<StudentDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        List<String> errors  = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.add("Error Field: " + e.getField() + ", " + e.getDefaultMessage()));

        ApiResponse<StudentDTO> error = ApiResponse.error(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
