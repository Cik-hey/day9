package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_company_list_when_get_given_companies() {
        //Given
        List<Employee> employeeList = Arrays.asList(new Employee(1, "Kate", 17, "female", 4801112),
                                                    new Employee(2, "Aedrian", 20, "male", 480111));
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "Mihoyo", employeeList));

        //When
        when(companyRepository.getCompanyList()).thenReturn(companyList);
        List<Company> returnedCompanyList = companyService.getCompanyList();

        //Then
        assertThat(returnedCompanyList, hasSize(1));
        assertThat(returnedCompanyList, equalTo(companyList));
    }

    @Test
    void should_return_company_when_get_by_id_given_companies() {
        //Given
        final int companyId = 1;
        List<Employee> employeeList = Arrays.asList(new Employee(1, "Kate", 17, "female", 4801112),
                                                    new Employee(2, "Aedrian", 20, "male", 480111));
        List<Company> companyList = new ArrayList<>();
        Company company = new Company(companyId, "Mihoyo", employeeList);

        //When
        when(companyRepository.getCompanyById(companyId)).thenReturn(company);
        Company returnedCompany = companyService.getCompanyById(companyId);

        //Then
        verify(companyRepository).getCompanyById(companyId);
        assertThat(returnedCompany, equalTo(company));
    }

    @Test
    void should_return_company_employees_list_when_get_employees_by_company_id_given_companies() {
        //Given
        final int companyId = 1;
        List<Employee> employeeList = Arrays.asList(new Employee(1, "Kate", 17, "female", 4801112),
                                                    new Employee(2, "Aedrian", 20, "male", 480111));

        //When
        when(companyRepository.getSpecificCompanyEmployees(companyId)).thenReturn(employeeList);
        List<Employee> returnedCompanyEmployeesList = companyService.getSpecificCompanyEmployees(companyId);

        //Then
        verify(companyRepository).getSpecificCompanyEmployees(companyId);
        assertThat(returnedCompanyEmployeesList, hasSize(2));
        assertThat(returnedCompanyEmployeesList, equalTo(employeeList));
    }
}
