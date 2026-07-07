package com.macuniv.student_api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService
{
    private final RefreshTokenRepository refreshTokenRepo;

    public RefreshToken createRefreshToken(User user)
    {
       String uuidString = UUID.randomUUID().toString();
       RefreshToken refreshToken = new RefreshToken();
       refreshToken.setToken(uuidString);
       refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + 86400000*7));
       refreshToken.setUser(user);
       refreshTokenRepo.findByUser(user).ifPresent(refreshTokenRepo::delete);
       refreshTokenRepo.save(refreshToken);
       return refreshToken;
    }

    public RefreshToken verifyExpiry(RefreshToken token)
    {
        if(token.getExpiryDate().before(new Date(System.currentTimeMillis())))
        {
            refreshTokenRepo.delete(token);
            throw new RefreshTokenInvalidException("Refresh Token is expired (or) invalid");
        }
        return token;
    }

    public RefreshToken findByToken(String token)
    {
        return (refreshTokenRepo.findByToken(token).orElseThrow(() -> new RefreshTokenInvalidException("Refresh Token is invalid")));
    }
}
