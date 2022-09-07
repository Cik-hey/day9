package com.rest.springbootemployee.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_get_all_given_employees() {
        //given
        Employee newEmployee = employeeRepository.addNewEmployee(new Employee(2, "Aedrian", 20, "male", 480111));
        List<Employee> employees = new ArrayList<>();
        employees.add(newEmployee);

        //when
        when(employeeRepository.getAll()).thenReturn(employees);

        List<Employee> employeeList = employeeService.getAll();

        //then
        assertThat(employeeList, hasSize(1));
        assertThat(employeeList.get(0), equalTo(newEmployee));
    }
}
