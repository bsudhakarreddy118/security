package com.example.security.utils;


import com.example.security.entity.LoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public final static String SECRET_KEY = "this is my custom Secret key for authnetication"; // Replace with your secret key

    public static String generateToken(LoginRequest userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key,SignatureAlgorithm.HS256)// Token valid for 10 hours
                .compact();
    }
    public static boolean validateToken(String token) {
        try {
            Date expirationDate = getClaims(token).getExpiration();
            Date now = new Date();
            if (expirationDate.before(now)) {
                return false;
            }
            return true; // Token is valid
        } catch (Exception e) {
            // Token validation failed
            return false;
        }
    }
    public static String extractUsername(String token)
    {
        try {
            Claims claims = getClaims(token);
            return claims.getSubject(); // Extracted username
        } catch (Exception e) {
            // Token validation failed or subject claim is missing
            return null;
        }
    }
    private static Claims getClaims(String token)
    {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Claims claims = claimsJws.getBody();
        return claims;
    }
}