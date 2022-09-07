package com.rest.springbootemployee.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> getEmployeeList() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employeeService.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getAllByGender(@RequestParam String gender) {
        return employeeService.getAllByGender(gender);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee newEmployee) {
        return employeeService.addNewEmployee(newEmployee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.removeEmployeeInformation(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeeByPage(@RequestParam int page, @RequestParam int pageSize) {
        return employeeService.getEmployeeListByPage(page, pageSize);
    }
}
