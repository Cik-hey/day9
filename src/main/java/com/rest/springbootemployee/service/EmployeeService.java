package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptionlist.NoEmployeeFoundException;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final JpaEmployeeRepository jpaEmployeeRepository;
    public EmployeeService( JpaEmployeeRepository jpaEmployeeRepository) {
        this.jpaEmployeeRepository = jpaEmployeeRepository;
    }

    public List<Employee> getAll() {
        return jpaEmployeeRepository.findAll();
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
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return jpaEmployeeRepository.findAll(pageRequest).toList();
    }
}
