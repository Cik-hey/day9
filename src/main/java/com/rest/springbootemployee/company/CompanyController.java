package com.rest.springbootemployee.company;

import com.rest.springbootemployee.employee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getCompanyList() {
        return companyRepository.getCompanyList();
    }

    @GetMapping("/{id}")
    public Company getSpecificCompany(@PathVariable Integer id) {
        return companyRepository.getCompanyById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable Integer id) {
        return companyRepository.getSpecificCompanyEmployees(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getComanyListByPage(@RequestParam int page, @RequestParam int pageSize) {
        return companyRepository.getCompanyListByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.addNewCompany(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) {
        return companyRepository.updateCompanyInformation(id, company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCompany(@PathVariable Integer id) {
        companyRepository.removeCompany(id);
    }
}
