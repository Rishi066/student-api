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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequestDTO registerRequestDTO)
    {
        authService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(null,"User Created Successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        AuthResponseDTO authResponse = authService.loginUser(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(authResponse,"Login Successfully"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refresh(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(authService.refreshJwtToken(refreshTokenRequestDTO),"JWT refreshed"));
    }

}
