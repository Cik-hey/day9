package com.rest.springbootemployee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
}
