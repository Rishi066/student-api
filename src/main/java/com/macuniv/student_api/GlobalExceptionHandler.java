package com.macuniv.student_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<StudentDTO>> handleStudentNotFoundException(StudentNotFoundException ex)
    {
        ApiResponse<StudentDTO> error = ApiResponse.error(Collections.singletonList(ex.getMessage()));
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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<RegisterRequestDTO>> handleUserAlreadyExistsException(UserAlreadyExistsException ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(List.of(ex.getMessage())));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<LoginRequestDTO>> handleBadCredentialsException(BadCredentialsException ex)
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(List.of(ex.getMessage())));
    }


}
