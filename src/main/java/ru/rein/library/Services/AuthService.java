package ru.rein.library.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.rein.library.DTO.LoginResponse;
import ru.rein.library.DTO.RefreshResponse;
import ru.rein.library.Security.EmployeePrincipal;
import ru.rein.library.Security.JwtIssuer;

@Service
public class AuthService {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(JwtIssuer jwtIssuer, AuthenticationManager authenticationManager) {
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse attemptLogin(String login, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (EmployeePrincipal) authentication.getPrincipal();

        var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                .employeeId(principal.getEmployeeId())
                .login(principal.getLogin())
                .build());
        var refreshToken = jwtIssuer.issueRefresh(JwtIssuer.Request.builder()
                .employeeId(principal.getEmployeeId())
                .login(principal.getLogin())
                .build());

        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshResponse refreshAccessToken(String refreshToken) {

        if (!jwtIssuer.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        var claims = jwtIssuer.getClaimsFromToken(refreshToken);
        var employeeId = Long.parseLong(claims.getSubject());
        var login = claims.getClaim("login").asString();

        // Генерируем новый Access Token и Refresh Token
        String newAccessToken = jwtIssuer.issue(JwtIssuer.Request.builder()
                .employeeId(employeeId)
                .login(login)
                .build());

        return new RefreshResponse(newAccessToken);
    }


}
