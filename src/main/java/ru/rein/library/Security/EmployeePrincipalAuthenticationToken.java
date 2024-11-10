package ru.rein.library.Security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class EmployeePrincipalAuthenticationToken extends AbstractAuthenticationToken {
    private final EmployeePrincipal principal;

    public EmployeePrincipalAuthenticationToken(EmployeePrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public EmployeePrincipal getPrincipal() {
        return principal;
    }
}
