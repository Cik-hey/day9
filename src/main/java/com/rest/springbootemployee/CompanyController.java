package com.rest.springbootemployee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    public CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getCompanyList() {
        return companyRepository.getCompanyList();
    }

    @GetMapping("/{idNumber}")
    public Company getSpecificCompany(@PathVariable Integer idNumber) {
        return companyRepository.getCompanyById(idNumber);
    }

    @GetMapping("/{idNumber}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable Integer idNumber) {
        return companyRepository.getSpecificCompanyEmployees(idNumber);
    }
}
