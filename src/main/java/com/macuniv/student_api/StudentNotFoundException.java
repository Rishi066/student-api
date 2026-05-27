package com.macuniv.student_api;

public class StudentNotFoundException extends RuntimeException
{
    StudentNotFoundException(String message)
    {
        super(message);
    }
}
