package com.trisul.core.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = 1L;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, username);
  }

  public Boolean validateToken(String token) {
    return validateToken(token, getUsernameFromToken(token));
  }

  public Boolean validateToken(String token, String username) {
    final String usernameFromToken = getUsernameFromToken(token);
    return (usernameFromToken.equals(username) && !isTokenExpired(token));
  }

  public String getValidToken(String requestTokenHeader) {
    if (requestTokenHeader != null && requestTokenHeader.startsWith(SeqConstant.BEARER_TOKEN)) {
      return requestTokenHeader.substring(7);
    }
    return null;
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(SeqConstant.SECRET_KEY).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + SeqConstant.TOKEN_VALIDITY * 100))
        .signWith(SignatureAlgorithm.HS512, SeqConstant.SECRET_KEY)
        .compact();
  }
}
