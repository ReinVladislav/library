package ru.rein.library.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.rein.library.Services.EmployeeService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomEmployeeDetailService implements UserDetailsService {
    private final EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var employee = employeeService.getEmployeeByLogin(login);
        return EmployeePrincipal.builder()
                .employeeId(employee.getId())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .build();
    }
}
