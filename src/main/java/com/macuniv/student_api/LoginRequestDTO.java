package com.macuniv.student_api;

import lombok.Data;

@Data
public class LoginRequestDTO
{
    private String username;
    private String password;
}
