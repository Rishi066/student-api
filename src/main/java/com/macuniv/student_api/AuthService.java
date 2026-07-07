package com.macuniv.student_api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
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
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepo;

    public void registerUser(RegisterRequestDTO registerRequestDTO) throws UserAlreadyExistsException
    {
        if(userRepo.findByUsername(registerRequestDTO.getUsername()).isPresent())
        {
            throw new UserAlreadyExistsException("username is taken");
        }
        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        userRepo.save(mapper.RegisterRequestDTOToUser(registerRequestDTO));
    }

    public AuthResponseDTO loginUser(LoginRequestDTO loginRequestDTO)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword()));
        User user =  (User) authentication.getPrincipal();
        AuthResponseDTO authResponse = new AuthResponseDTO();
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user).getToken());
        authResponse.setAccessToken(jwtService.generateToken(user));
        return authResponse;
    }

    public AuthResponseDTO refreshJwtToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {

        String refreshToken = refreshTokenRequestDTO.getRefreshToken();

        RefreshToken token = refreshTokenService.verifyExpiry(refreshTokenService.findByToken(refreshToken));

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        User user = token.getUser();
        String newJwtToken = jwtService.generateToken(user);

        authResponseDTO.setAccessToken(newJwtToken);
        authResponseDTO.setRefreshToken(refreshTokenRequestDTO.getRefreshToken());
        return authResponseDTO;
    }
}
