package com.rest.springbootemployee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping()
    public List<Employee> getEmployeeList() {
        return employeeRepository.getAll();
    }

    @GetMapping("/{idNumber}")
    public Employee getById(@PathVariable Integer idNumber) {
        return employeeRepository.findById(idNumber);
    }
}
