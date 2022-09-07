package com.rest.springbootemployee.employee;

import com.rest.springbootemployee.exceptionlist.NoEmployeeFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private final List<Employee> employeeList;

    public EmployeeRepository() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Kate", 17, "female", 480111));
        employeeList.add(new Employee(2, "Aedrian", 17, "male", 480111));
        employeeList.add(new Employee(3, "Someone", 17, "female", 480111));
    }

    public List<Employee> getAll() {
        return employeeList;
    }

    public Employee findById(Integer id) {
        return employeeList.stream()
                .filter(employee -> employee.getid().equals(id))
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
        newEmployee.setid(newEmployeeId);
        employeeList.add(newEmployee);
        return newEmployee;
    }

    private Integer generateNextId() {
        int maxId = employeeList.stream()
                .mapToInt(Employee::getid)
                .max()
                .orElse(1);
        return ++maxId;
    }

    public Employee updateEmployeeInformation(Integer id, Employee employee) {
        Employee existingEmployee = findById(employee.getid());
        if (employee.getAge() != null) {
            existingEmployee.setAge(employee.getAge());
        }
        if (employee.getSalary() != null) {
            existingEmployee.setSalary(employee.getSalary());
        }
        return existingEmployee;
    }

    public void removeEmployeeInformation(Integer id) {
        Employee employeeToBeRemoved = findById(id);
        employeeList.remove(employeeToBeRemoved);
    }

    public List<Employee> getEmployeeListByPage(int page, int pageSize) {
        return employeeList.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
