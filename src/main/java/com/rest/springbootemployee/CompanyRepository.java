package com.rest.springbootemployee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    public List<Company> companyList;

    public CompanyRepository() {
        companyList = new ArrayList<>();
        EmployeeRepository employeeRepository = new EmployeeRepository();
        companyList.add(new Company(1, "Mihoyo", employeeRepository.employeeList));
        companyList.add(new Company(2, "KLab", employeeRepository.employeeList));
    }

}
