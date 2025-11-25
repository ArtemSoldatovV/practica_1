package org.example.UserService.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.UserService.User;

import java.util.Date;

public class ProductionToxins {
    private final String jwtSecret = "strong_password";
    private final long jwtExpirationMs = 20 * 60 * 60 * 1000;//сколько будет жить токен. 20 часов
    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getFullName())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
