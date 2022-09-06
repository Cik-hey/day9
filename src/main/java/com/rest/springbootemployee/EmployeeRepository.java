package com.rest.springbootemployee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    List<Employee> employeeList;

    public EmployeeRepository() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Kate", 17, "female", 480111));
        employeeList.add(new Employee(2, "Aedrian", 17, "male", 480111));
        employeeList.add(new Employee(3, "Someone", 17, "female", 480111));
    }

    public List<Employee> getAll() {
        return employeeList;
    }

    public Employee findById(Integer idNumber) {
        return employeeList.stream().filter(employee -> employee.getIdNumber().equals(idNumber))
                .findFirst()
                .orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> getAllByGender(String gender) {
        return employeeList.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addNewEmployee(Employee newEmployee) {
        Integer newEmployeeId = generateNextId();
        newEmployee.setIdNumber(newEmployeeId);
        employeeList.add(newEmployee);
        return newEmployee;
    }

    private Integer generateNextId() {
        int maxId = employeeList.stream()
                .mapToInt(Employee::getIdNumber)
                .max()
                .orElse(1);
        return ++maxId;
    }

    public Employee updateEmployeeInformation(Integer idNumber, Employee employee) {
        Employee existingEmployee = findById(employee.getIdNumber());
        if (employee.getAge() != null) {
            existingEmployee.setAge(employee.getAge());
        }
        if (employee.getSalary() != null) {
            existingEmployee.setSalary(employee.getSalary());
        }
        return existingEmployee;
    }
}
