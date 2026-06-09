package com.macuniv.student_api;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO
{
   @Size(min = 3,max = 20, message = "Username should be at least 4 characters but not more than 20")
   private  String username;

   @Size(min = 8,message = "password must be at least 8 characters long")
   private  String password;
}
