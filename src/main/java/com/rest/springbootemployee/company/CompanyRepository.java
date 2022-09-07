package com.rest.springbootemployee.company;

import com.rest.springbootemployee.employee.Employee;
import com.rest.springbootemployee.employee.EmployeeRepository;
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
        EmployeeRepository employeeRepository = new EmployeeRepository();
        companyList.add(new Company(1, "Mihoyo", employeeRepository.getEmployeeList()));
        companyList.add(new Company(2, "KLab", employeeRepository.getEmployeeList()));
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

    public Company updateCompanyInformation(Integer id, Company company) {
        Company existingCompany = getCompanyById(id);
        if (company.getName() != null) {
            existingCompany.setName(company.getName());
        }
        return company;
    }

    public void removeCompany(Integer id) {
        Company companyToBeDeleted = getCompanyById(id);
        companyList.remove(companyToBeDeleted);
    }
}
