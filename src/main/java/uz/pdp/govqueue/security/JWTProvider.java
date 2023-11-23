package uz.pdp.govqueue.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.pdp.govqueue.model.User;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTProvider {

    @Value("${app.jwt.key}")
    private String jwtKey;


    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(User user) {
//        jwtKey = new String(Base64.getEncoder().encode(jwtKey.getBytes(StandardCharsets.UTF_8)));
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .setSubject(user.getId().toString())
                .claim("age", 25)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .compact();
    }

    public String getSubjectFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
