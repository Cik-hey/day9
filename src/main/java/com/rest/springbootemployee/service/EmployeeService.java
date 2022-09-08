package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptionlist.NoEmployeeFoundException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private JpaEmployeeRepository jpaEmployeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository, JpaEmployeeRepository jpaEmployeeRepository) {
        this.employeeRepository = employeeRepository;
        this.jpaEmployeeRepository = jpaEmployeeRepository;
    }

    public List<Employee> getAll() {
        return jpaEmployeeRepository.findAll();
//        return employeeRepository.getAll();
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        Employee existingEmployee = jpaEmployeeRepository.findById(id)
                .orElseThrow(NoEmployeeFoundException::new);
        if (employee.getAge() != null) {
            existingEmployee.setAge(employee.getAge());
        }
        if (employee.getSalary() != null) {
            existingEmployee.setSalary(employee.getSalary());
        }
        jpaEmployeeRepository.save(existingEmployee);
        return existingEmployee;
    }

    public Employee findById(Integer id) {
        return jpaEmployeeRepository.findById(id)
                .orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> getAllByGender(String gender) {
        return jpaEmployeeRepository.findByGender(gender);
    }

    public Employee addNewEmployee(Employee newEmployee) {
        return jpaEmployeeRepository.save(newEmployee);
    }

    public void removeEmployeeInformation(Integer id) {
        jpaEmployeeRepository.delete(jpaEmployeeRepository.findById(id)
                .orElseThrow(NoEmployeeFoundException::new));
    }

    public List<Employee> getEmployeeListByPage(int page, int pageSize) {
        return employeeRepository.getEmployeeListByPage(page, pageSize);
    }
}
