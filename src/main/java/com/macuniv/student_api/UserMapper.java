package com.macuniv.student_api;
import org.springframework.stereotype.Component;

@Component
public class UserMapper
{
    public User RegisterRequestDTOToUser(RegisterRequestDTO registerRequestDTO)
    {
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(registerRequestDTO.getPassword());
        user.setRole("ROLE_STUDENT");
        return user;
    }

    public RegisterRequestDTO UserToRegisterRequestDTO(User user)
    {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setUsername(user.getUsername());
        return registerRequestDTO;
    }
}
