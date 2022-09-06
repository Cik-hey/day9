package com.rest.springbootemployee;

import org.springframework.http.HttpStatus;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee newEmployee) {
        return employeeRepository.addNewEmployee(newEmployee);
    }

    @PutMapping("/{idNumber}")
    public Employee updateEmployee(@PathVariable Integer idNumber, @RequestBody Employee employee) {
        return employeeRepository.updateEmployeeInformation(idNumber, employee);
    }

    @DeleteMapping("/{idNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer idNumber) {
        employeeRepository.removeEmployeeInformation(idNumber);
    }
}
