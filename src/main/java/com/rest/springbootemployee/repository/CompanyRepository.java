package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptionlist.NoCompanyFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    public List<Company> companyList;

    public CompanyRepository() {
        companyList = new ArrayList<>();
//        EmployeeRepository employeeRepository = new EmployeeRepository();
//        companyList.add(new Company(1, "Mihoyo", employeeRepository.getAll()));
//        companyList.add(new Company(2, "KLab", employeeRepository.getAll()));
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public Company getCompanyById(Integer id) {
        return companyList.stream()
                .filter(company -> company.getid().equals(id))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> getSpecificCompanyEmployees(Integer id) {
        Company specificCompany = getCompanyById(id);
        return specificCompany.getEmployeeList();
    }

    public List<Company> getCompanyListByPage(int page, int pageSize) {
        return companyList.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company addNewCompany(Company newCompany) {
        Integer newCompanyId = getNewCompanyId();
        newCompany.setid(newCompanyId);
        companyList.add(newCompany);
        return newCompany;
    }

    private Integer getNewCompanyId() {
        int maxId = companyList.stream()
                .mapToInt(Company::getid)
                .max()
                .orElse(1);

        return maxId + 1;
    }

    public void removeCompany(Integer id) {
        Company companyToBeDeleted = getCompanyById(id);
        companyList.remove(companyToBeDeleted);
    }

    public void clearData() {
        companyList = new ArrayList<>();
    }
}
