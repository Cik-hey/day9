package com.rest.springbootemployee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getComanyListByPage(@RequestParam int page,@RequestParam int pageSize) {
        return companyRepository.getCompanyListByPage(page, pageSize);
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.addNewCompany(company);
    }

    @PutMapping("/{idNumber}")
    public Company updateCompany(@PathVariable Integer idNumber, @RequestBody Company company) {
        return companyRepository.updateCompanyInformation(idNumber, company);
    }
}
