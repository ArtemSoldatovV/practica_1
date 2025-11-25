package org.example.RatingService.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class ChekToxin {

    private static final String jwtSecret = "strong_password";

    public static boolean validateJwtToken(String authToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                return false;
            }

            String subject = claims.getSubject();

            return true; // Токен валиден

        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
