package com.system_mastery.ascension.security;

import com.system_mastery.ascension.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationinMs;

    public String generateToken(User user){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationinMs);
        String token = Jwts.builder().setSubject(user.getUsername()).claim("role",user.getRole().name())
                .setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        return token;
    }
    public long getExpirationInMs() {
        return jwtExpirationinMs;
    }

    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token); // if parsing fails, it's invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
