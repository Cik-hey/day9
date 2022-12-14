package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
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
    List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    void clearAll() {
        this.employeeList = Arrays.asList(new Employee(1, "Kate", 17, "female", 4801112),
                new Employee(2, "Aedrian", 20, "male", 480111));
    }

    @Test
    void should_return_company_list_when_get_given_companies() {
        //Given
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

        //When
        when(companyRepository.getSpecificCompanyEmployees(companyId)).thenReturn(employeeList);
        List<Employee> returnedCompanyEmployeesList = companyService.getSpecificCompanyEmployees(companyId);

        //Then
        verify(companyRepository).getSpecificCompanyEmployees(companyId);
        assertThat(returnedCompanyEmployeesList, hasSize(2));
        assertThat(returnedCompanyEmployeesList, equalTo(employeeList));
    }

    @Test
    void should_return_company_list_when_get_by_page_given_companies() {
        //Given
        final int page = 1;
        final int pageSize = 2;
        List<Company> companyList = Arrays.asList(new Company(1, "Mihoyo", employeeList),
                new Company(2, "KLab", employeeList));

        //When
        when(companyRepository.getCompanyListByPage(page, pageSize)).thenReturn(companyList);
        List<Company> returnedCompanyList = companyService.getCompanyListByPage(page, pageSize);

        //Then
        verify(companyRepository).getCompanyListByPage(page, pageSize);
        assertThat(returnedCompanyList, hasSize(2));
        assertThat(returnedCompanyList, equalTo(companyList));
    }

    @Test
    void should_return_new_company_when_post_given_new_company() {
        //Given
        Company newCompany = new Company(1, "Mihoyo", employeeList);
        Company createdCompany = new Company(1, "Mihoyo", employeeList);

        //When
        when(companyRepository.addNewCompany(newCompany)).thenReturn(createdCompany);
        Company returnedCompany = companyService.addNewCompany(newCompany);

        //Then
        verify(companyRepository).addNewCompany(newCompany);
        assertThat(returnedCompany, equalTo(createdCompany));
    }

    @Test
    void should_return_updated_company_when_put_given_new_company_information() {
        //Given
        final int companyId = 1;
        Company newCompany = new Company(companyId, "Mihoyo", employeeList);
        Company updatedCompany = new Company(companyId, "Cerberus", employeeList);

        //When
        when(companyRepository.getCompanyById(companyId)).thenReturn(updatedCompany);
        Company returnedCompany = companyService.updateCompanyInformation(companyId, newCompany);

        //Then
        verify(companyRepository).getCompanyById(companyId);
        assertThat(returnedCompany, equalTo(updatedCompany));
    }

    @Test
    void should_remove_company_when_delete_given_company_id() {
        //Given
        final int companyId = 1;

        //When
        companyService.removeCompany(companyId);

        //Then
        verify(companyRepository).removeCompany(companyId);
    }
}
