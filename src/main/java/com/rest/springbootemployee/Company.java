package com.rest.springbootemployee;

import java.util.List;

public class Company {
    private Integer idNumber;
    private String name;
    private List<Employee> employeeList;
    public Company(Integer idNumber, String name, List<Employee> employeeList) {
        this.idNumber = idNumber;
        this.name = name;
        this.employeeList = employeeList;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

}
