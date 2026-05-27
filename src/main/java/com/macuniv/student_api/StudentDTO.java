package com.macuniv.student_api;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentDTO
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
}
