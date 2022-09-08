package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Mock
    JpaEmployeeRepository jpaEmployeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_get_all_given_employees() {
        //given
        Employee newEmployee = new Employee(2, "Aedrian", 20, "male", 480111);
        List<Employee> employees = new ArrayList<>();
        employees.add(newEmployee);

        //when
        when(jpaEmployeeRepository.findAll()).thenReturn(employees);

        List<Employee> employeeList = employeeService.getAll();

        //then
        assertThat(employeeList, hasSize(1));
        assertThat(employeeList.get(0), equalTo(newEmployee));
    }

    @Test
    void should_return_updated_employee_when_put_given_new_employee_information() {
        //Given
        final int employeeId = 1;
        Employee originalEmployee = new Employee(employeeId, "Kate", 17, "female", 4801112);
        Employee newEmployeeInfo = new Employee(employeeId, "Katey", 18, "male", 4812345);

        //When
        when(employeeRepository.findById(employeeId)).thenReturn(originalEmployee);
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, newEmployeeInfo);

        //Then
        verify(employeeRepository).findById(employeeId);
        assertThat(updatedEmployee.getName(), equalTo("Kate"));
        assertThat(updatedEmployee.getAge(), equalTo(18));
        assertThat(updatedEmployee.getGender(), equalTo("female"));
        assertThat(updatedEmployee.getSalary(), equalTo(4812345));
    }

    @Test
    void should_return_employee_when_get_by_id_given_employees() {
        //Given
        final int employeeId = 1;
        Employee employee = new Employee(employeeId, "Kate", 17, "female", 4801112);

        //When
        when(employeeRepository.findById(employeeId)).thenReturn(employee);
        Employee returnedEmployee = employeeService.findById(employeeId);

        //Then
        verify(employeeRepository).findById(employeeId);
        assertThat(returnedEmployee, equalTo(returnedEmployee));
    }

    @Test
    void should_return_employee_list_when_get_by_gender_given_employees() {
        //Given
        final String employeesGender = "female";
        List<Employee> employeeList = Arrays.asList(new Employee(1, "Kate", 17, employeesGender, 4801112),
                new Employee(3, "Taylor", 19, employeesGender, 90111));
        //When
        when(employeeRepository.getAllByGender(employeesGender)).thenReturn(employeeList);
        List<Employee> returnedEmployeeList = employeeService.getAllByGender(employeesGender);

        //Then
        verify(employeeRepository).getAllByGender(employeesGender);
        assertThat(returnedEmployeeList, hasSize(2));
        assertThat(returnedEmployeeList, equalTo(employeeList));
    }

    @Test
    void should_return_new_employee_when_post_given_new_employee() {
        //Given
        Employee newEmployee = new Employee(1, "Kate", 17, "female", 4801112);
        Employee createdEmployee = new Employee(1, "Kate", 17, "female", 4801112);

        //When
        when(employeeRepository.addNewEmployee(newEmployee)).thenReturn(createdEmployee);
        Employee returnedEmployee = employeeService.addNewEmployee(newEmployee);

        //Then
        verify(employeeRepository).addNewEmployee(newEmployee);
        assertThat(returnedEmployee, equalTo(createdEmployee));
    }

    @Test
    void should_remove_employee_when_delete_given_employee_id() {
        //Given
        int employeeNumber = 1;

        //When
        employeeService.removeEmployeeInformation(employeeNumber);

        //Then
        verify(employeeRepository).removeEmployeeInformation(employeeNumber);
    }

    @Test
    void should_return_employee_list_when_get_by_page_given_employees() {
        //Given
        final int page = 1;
        final int pageSize = 2;
        List<Employee> employeeList = Arrays.asList(new Employee(1, "Kate", 17, "female", 4801112),
                new Employee(2, "Aedrian", 20, "male", 480111));

        //When
        when(employeeRepository.getEmployeeListByPage(page, pageSize)).thenReturn(employeeList);
        List<Employee> returnedEmployeeList = employeeService.getEmployeeListByPage(page, pageSize);

        //Then
        verify(employeeRepository).getEmployeeListByPage(page, pageSize);
        assertThat(returnedEmployeeList, hasSize(2));
        assertThat(returnedEmployeeList, equalTo(employeeList));
    }
}
