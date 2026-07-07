package com.macuniv.student_api;

import lombok.Data;


@Data
public class AuthResponseDTO
{
    private String refreshToken;
    private String accessToken;
}
