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
}
