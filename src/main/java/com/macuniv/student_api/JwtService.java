package com.macuniv.student_api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService
{
    @Value("${security.jwt.secret-key}")
    private String secret_key;

    @Value("${security.jwt.expiration}")
    private long expiration;

    public String generateToken(UserDetails userDetails)
    {
        HashMap<String,String> claims = new HashMap<>();
        claims.put("username",userDetails.getUsername());
        claims.put("role",userDetails.getAuthorities().toString());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public SecretKey getSignInKey()
    {
        return Keys.hmacShaKeyFor(secret_key.getBytes());
    }

    public String extractUsername(String token)
    {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        return ((extractUsername(token).equals(userDetails.getUsername())) && (!isTokenExpired(token)));
    }

    public boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public Claims extractAllClaims(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    public Date extractExpiration(String token)
    {
        return extractAllClaims(token).getExpiration();
    }

}
