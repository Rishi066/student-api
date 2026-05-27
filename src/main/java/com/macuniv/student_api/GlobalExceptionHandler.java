package com.macuniv.student_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<StudentDTO>> handleStudentNotFoundException(StudentNotFoundException ex)
    {
        ApiResponse<StudentDTO> error = ApiResponse.error(ex.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
