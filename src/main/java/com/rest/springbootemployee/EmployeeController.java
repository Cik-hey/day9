package com.rest.springbootemployee;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping(params = {"gender"})
    public List<Employee> getAllByGender(@RequestParam String gender) {
        return employeeRepository.getAllByGender(gender);
    }
}
