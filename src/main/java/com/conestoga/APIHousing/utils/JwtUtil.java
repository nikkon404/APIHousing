package com.conestoga.APIHousing.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  @Value("${jwt.secret}")
  String secretKey;

  @Value("${jwt.expiration}")
  int expiration;

  public String generateToken(String username) {

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration * 1000L);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  // public boolean validateToken(String token) {
  //     SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

  //     try {
  //         Claims claims = Jwts.parserBuilder()
  //                 .setSigningKey(key)
  //                 .build()
  //                 .parseClaimsJws(token)
  //                 .getBody();

  //         return !claims.getExpiration().before(new Date());
  //     } catch (Exception ex) {
  //         // Exception indicates token is not valid
  //         return false;
  //     }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String extractUsername(String token) {

    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    return claims.getSubject();
  }
}
