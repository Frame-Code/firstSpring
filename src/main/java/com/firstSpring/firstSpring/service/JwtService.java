package com.firstSpring.firstSpring.service;

import com.firstSpring.firstSpring.model.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Artist-Code
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-token}")
    private long refreshExpiration;
    
    public String generateToken(final UserEntity user) {
        return buildToken(user, jwtExpiration);
    }
    
    public String generateRefreshToken(final UserEntity user) {
        return buildToken(user, refreshExpiration);
    }
    
    private String buildToken(final UserEntity user, final long expiration) {
        return Jwts.builder()
                .claims(Map.of("name", user.getName())) //Informacion adicional de la clave: opcional
                .subject(user.getEmail()) //UNIQUE value to identify the user in the token
                .issuedAt(new Date(System.currentTimeMillis())) //When was created the token
                .expiration(new Date(System.currentTimeMillis() + expiration)) //When was expirated the token
                .signWith(getSignInKey()) //Generar clave privada a traves de la secret key (.properties dont share!)
                .compact();
        
    }
    
    //Genera clave secreta usando la secret key (.properties)
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
