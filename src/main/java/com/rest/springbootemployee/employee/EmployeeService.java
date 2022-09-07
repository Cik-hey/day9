package com.rest.springbootemployee.employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id);
        if (employee.getAge() != null) {
            existingEmployee.setAge(employee.getAge());
        }
        if (employee.getSalary() != null) {
            existingEmployee.setSalary(employee.getSalary());
        }
        return existingEmployee;
    }

    public Employee findById(Integer id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllByGender(String gender) {
        return employeeRepository.getAllByGender(gender);
    }

    public Employee addNewEmployee(Employee newEmployee) {
        return employeeRepository.addNewEmployee(newEmployee);
    }
}
