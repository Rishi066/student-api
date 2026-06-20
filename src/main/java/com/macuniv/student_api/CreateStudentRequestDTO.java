package com.macuniv.student_api;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateStudentRequestDTO
{
    @NotBlank
    @Size(min = 4, max = 20, message = "Name should be between 4 to 20 characters long")
    private String name;

    @Min(value = 5, message = "Minimum age should be 5")
    @Max(value = 25, message = "Maximum age should be 25")
    private int age;

    @Email(message = "Enter a valid e-mail")
    @NotBlank
    private String email;

    @Size(min = 3,max = 20, message = "Username should be at least 4 characters but not more than 20")
    private String username;

    @Size(min = 8,message = "password must be at least 8 characters long")
    private String password;

    @Size(min = 2, max = 20, message = "Branch should be between 4 to 20 characters long")
    private String branch;
}
