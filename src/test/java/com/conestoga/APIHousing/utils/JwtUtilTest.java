package com.conestoga.APIHousing.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        // Create a consistent SecretKey for testing
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        jwtUtil = new JwtUtil();
        jwtUtil.secretKey = Arrays.toString(secretKey.getEncoded());
        jwtUtil.expiration = 3600; // 1 hour
    }

    @Test
    void testGenerateAndExtractToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateValidToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void testValidateExpiredToken() {
        // Create an expired token
        SecretKey expiredKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 1000L); // 1 second ago
        String expiredToken = Jwts.builder()
                .setSubject("expiredUser")
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(expiredKey, SignatureAlgorithm.HS256)
                .compact();

        assertFalse(jwtUtil.validateToken(expiredToken));
    }

    @Test
    void testValidateInvalidToken() {
        // An invalid token with a different secret key
        SecretKey invalidKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String invalidToken = Jwts.builder()
                .setSubject("testuser")
                .signWith(invalidKey, SignatureAlgorithm.HS256)
                .compact();

        assertFalse(jwtUtil.validateToken(invalidToken));
    }
}

