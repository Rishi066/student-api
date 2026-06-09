package com.macuniv.student_api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;
    private final UserMapper mapper;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterRequestDTO>> register(@RequestBody RegisterRequestDTO registerRequestDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(mapper.UserToRegisterRequestDTO(authService.registerUser(registerRequestDTO)),"User Created Successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        User user = authService.loginUser(loginRequestDTO);
        String JwtToken = jwtService.generateToken(user);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(JwtToken,"Login Successfully"));
    }


}
