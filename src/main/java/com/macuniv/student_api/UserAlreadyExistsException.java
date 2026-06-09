package com.macuniv.student_api;

public class UserAlreadyExistsException extends RuntimeException
{
    UserAlreadyExistsException(String message)
    {
        super(message);
    }
}
