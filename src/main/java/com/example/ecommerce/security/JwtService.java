package com.example.ecommerce.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms:86400000}") long expirationMs) {

        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException(
                    "jwt.secret must be at least 32 bytes");
        }

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

// OLD CODE - kept for reference
// @Service
// public class JwtService {
//
//     private static final String SECRET =
//             "apni_bohot_hi_lambee_aur_secure_secret_key_jo_32_bytes_se_badi_ho";
//
//     private final SecretKey key =
//             Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//
//     public String generateToken(String email) {
//         return Jwts.builder()
//                 .subject(email)
//                 .issuedAt(new Date())
//                 .expiration(new Date(System.currentTimeMillis() + 86400000))
//                 .signWith(key)
//                 .compact();
//     }
//
//     public String extractEmail(String token) {
//         return Jwts.parser()
//                 .verifyWith(key)
//                 .build()
//                 .parseSignedClaims(token)
//                 .getPayload()
//                 .getSubject();
//     }
//
//     public boolean isValid(String token) {
//         try {
//             Jwts.parser()
//                     .verifyWith(key)
//                     .build()
//                     .parseSignedClaims(token);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }
// }
