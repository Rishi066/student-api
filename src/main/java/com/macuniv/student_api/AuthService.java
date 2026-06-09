package com.macuniv.student_api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final UserRepository userRepo;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public User registerUser(RegisterRequestDTO registerRequestDTO) throws UserAlreadyExistsException
    {
        if(userRepo.findByUsername(registerRequestDTO.getUsername()).isPresent())
        {
            throw new UserAlreadyExistsException("username is taken");
        }
        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        return userRepo.save(mapper.RegisterRequestDTOToUser(registerRequestDTO));
    }

    public User loginUser(LoginRequestDTO loginRequestDTO)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword()));
        return (User) authentication.getPrincipal();
    }

}
