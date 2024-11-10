package ru.rein.library.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rein.library.Models.Employee;
import ru.rein.library.Repositories.EmployeeRepository;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee getEmployeeByLogin(String login){
        return employeeRepository.findByLogin(login).get();
    }
}
