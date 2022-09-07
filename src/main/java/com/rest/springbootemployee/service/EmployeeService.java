package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void removeEmployeeInformation(Integer id) {
        employeeRepository.removeEmployeeInformation(id);
    }

    public List<Employee> getEmployeeListByPage(int page, int pageSize) {
        return employeeRepository.getEmployeeListByPage(page, pageSize);
    }
}