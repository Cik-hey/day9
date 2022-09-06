package com.rest.springbootemployee;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    public List<Company> companyList;

    public CompanyRepository() {
        companyList = new ArrayList<>();
        EmployeeRepository employeeRepository = new EmployeeRepository();
        companyList.add(new Company(1, "Mihoyo", employeeRepository.employeeList));
        companyList.add(new Company(2, "KLab", employeeRepository.employeeList));
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public Company getCompanyById(Integer idNumber) {
        return companyList.stream()
                .filter(company->company.getIdNumber().equals(idNumber))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> getSpecificCompanyEmployees(Integer idNumber) {
        Company specificCompany = getCompanyById(idNumber);
        return specificCompany.getEmployeeList();
    }

    public List<Company> getCompanyListByPage(int page, int pageSize) {
        return companyList.stream()
                .skip((long) (page - 1) * pageSize )
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company addNewCompany(Company newCompany) {
        Integer newCompanyId = getNewCompanyId();
        newCompany.setIdNumber(newCompanyId);
        companyList.add(newCompany);
        return newCompany;
    }

    private Integer getNewCompanyId() {
        int maxIdNumber = companyList.stream()
                .mapToInt(Company::getIdNumber)
                .max()
                .orElse(1);

        return maxIdNumber + 1;
    }
}
