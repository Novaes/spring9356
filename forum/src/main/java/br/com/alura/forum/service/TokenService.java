package br.com.alura.forum.service;


import br.com.alura.forum.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${auth.token.expiration}")
    private long expirationTimeInMilliseconds;

    @Value("${auth.token.key}")
    private String passwordKey;

    public String createToken(User user) {

        long now = new Date().getTime();
        Date expirationTime = new Date(now + expirationTimeInMilliseconds );

        String token = Jwts.builder()
                .setExpiration(expirationTime)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, passwordKey)
                .compact();
        return token;
    }
}
