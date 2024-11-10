package ru.rein.library.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;

    public String issue(Request request) {
        var now = Instant.now();

        return JWT.create()
                .withSubject(String.valueOf(request.getEmployeeId()))
                .withIssuedAt(now)
                .withExpiresAt(now.plus(properties.getTokenDuration()))
                .withClaim("login", request.getLogin())
                .withClaim("au", request.getRoles())
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }

    public String issueRefresh(Request request) {
        var now = Instant.now();

        return JWT.create()
                .withSubject(String.valueOf(request.getEmployeeId()))
                .withIssuedAt(now)
                .withExpiresAt(now.plus(properties.getRefreshTokenDuration()))
                .withClaim("login", request.getLogin())
                .withClaim("au", request.getRoles())
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(properties.getSecretKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptExpiresAt(0)
                    .build();

            verifier.verify(refreshToken);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }


    public DecodedJWT getClaimsFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(properties.getSecretKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptExpiresAt(0)
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new IllegalArgumentException("Invalid or expired token", ex);
        }
    }

    @Getter
    @Builder
    public static class Request {
        private final Long employeeId;
        private final String login;
        private final List<String> roles;
    }
}
