package com.macuniv.student_api;

public class UnauthorizedAccessException extends RuntimeException
{
    public UnauthorizedAccessException(String message)
    {
        super(message);
    }

}
