package com.example.jwt_oauth2_demo.service;

import com.example.jwt_oauth2_demo.entity.Token;
import com.example.jwt_oauth2_demo.exceptions.TokenNotFoundException;
import com.example.jwt_oauth2_demo.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final TokenRepository tokenRepository;

    private String jwtSecretKey = "my-very-long-secret-key-for-testing";

    private String jwtSigningKey;
    private long accessTokenExpiration= 2592000000L;
    public JwtService(TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
        jwtSigningKey = Base64.getEncoder().encodeToString(jwtSecretKey.getBytes());
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            jwtSigningKey = Base64.getEncoder().encodeToString(sk.getEncoded());

//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, accessTokenExpiration);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        Token tokenFromDb = tokenRepository.findByToken(token).orElseThrow(() -> new TokenNotFoundException(token));
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return generateToken(extraClaims, userDetails, expiration, jwtSigningKey);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration, String signingKey) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(getSigningKey(jwtSigningKey))
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey(jwtSigningKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
