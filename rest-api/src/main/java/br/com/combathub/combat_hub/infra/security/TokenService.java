package br.com.combathub.combat_hub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String secrete;

    public String generateToken(String user) {
        var algorithm = Algorithm.HMAC256(secrete);
        return JWT.create()
                .withIssuer("Combat Hub")
                .withSubject(user)
                .withExpiresAt(calculateExpirationTime())
                .sign(algorithm);
    }

    public Instant calculateExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String jwtToken) {
        var algorithm = Algorithm.HMAC256(secrete);
        return JWT.require(algorithm)
                .withIssuer("Combat Hub")
                .build()
                .verify(jwtToken)
                .getSubject();
    }

}
