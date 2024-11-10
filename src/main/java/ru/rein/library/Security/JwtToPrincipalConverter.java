package ru.rein.library.Security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Component
public class JwtToPrincipalConverter {
    public EmployeePrincipal convert(DecodedJWT jwt) {
        var authorityList = getClaimOrEmptyList(jwt, "au").stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return EmployeePrincipal.builder()
                .employeeId((long) Integer.parseInt(jwt.getSubject()))
                .login( jwt.getClaim("login").asString() )
                .authorities( authorityList )
                .build();
    }



    private List<String> getClaimOrEmptyList(DecodedJWT jwt, String claim) {
        if (jwt.getClaim(claim).isNull()) return List.of();
        return jwt.getClaim(claim).asList(String.class);
    }
}
