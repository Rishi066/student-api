package com.macuniv.student_api;

public class RefreshTokenInvalidException extends RuntimeException
{
    RefreshTokenInvalidException(String message)
    {
        super(message);
    }
}
