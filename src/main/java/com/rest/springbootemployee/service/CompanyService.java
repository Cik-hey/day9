package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptionlist.NoCompanyFoundException;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompanyList() {
        return companyRepository.getCompanyList();
    }

    public Company getCompanyById(Integer id) {
        return companyRepository.getCompanyById(id);
    }

    public List<Employee> getSpecificCompanyEmployees(Integer id) {
        return companyRepository.getSpecificCompanyEmployees(id);
    }

    public List<Company> getCompanyListByPage(int page, int pageSize) {
        return companyRepository.getCompanyListByPage(page, pageSize);
    }

    public Company addNewCompany(Company newCompany) {
        return companyRepository.addNewCompany(newCompany);
    }

    public Company updateCompanyInformation(Integer id, Company company) {
        Company existingCompany = companyRepository.getCompanyById(id);
        if (company.getName() != null) {
            existingCompany.setName(company.getName());
        }
        return existingCompany;
    }

    public void removeCompany(Integer id) {
        companyRepository.removeCompany(id);
    }
}
