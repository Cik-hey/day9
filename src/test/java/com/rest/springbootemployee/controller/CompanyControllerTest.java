package com.rest.springbootemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    MockMvc client;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyService companyService;
    List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    void clearAll() {
        companyRepository.clearData();
        this.employeeList = Arrays.asList(new Employee(1, "Kate", 17, "female", 4801112),
                new Employee(2, "Aedrian", 20, "male", 480111));
    }

    @Test
    void should_return_company_list_when_get_given_companies() throws Exception {
        //Given
        companyService.addNewCompany(new Company(1, "Mihoyo", employeeList));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Mihoyo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList", hasSize(2)));
    }

    @Test
    void should_return_company_when_get_by_id_given_companies() throws Exception {
        //Given
        companyService.addNewCompany(new Company(1, "Mihoyo", employeeList));
        Company returnedCompany = companyService.addNewCompany(new Company(2, "KLab", employeeList));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}", returnedCompany.getid()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("KLab"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList", hasSize(2)));
    }

    @Test
    void should_return_company_employees_list_when_get_employees_by_company_id_given_companies() throws Exception {
        //Given
        companyService.addNewCompany(new Company(1, "Mihoyo", null));
        Company returnedCompany = companyService.addNewCompany(new Company(2, "KLab", employeeList));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", returnedCompany.getid()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Kate", "Aedrian")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(17, 20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("female", "male")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(480111, 4801112)));
    }

    @Test
    void should_return_company_list_when_get_by_page_given_companies() throws Exception {
        //Given
        companyService.addNewCompany(new Company(1, "Mihoyo", employeeList));
        companyService.addNewCompany(new Company(2, "KLab", employeeList));
        companyService.addNewCompany(new Company(2, "Cerberus", employeeList));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/companies?page={page}&pageSize={pageSize}", 1, 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Mihoyo", "KLab")));
    }

    @Test
    void should_return_new_company_when_post_given_new_company() throws Exception {
        //Given
        Company newCompany = companyService.addNewCompany(new Company(1, "Mihoyo", employeeList));
        String newCompanyJSON = new ObjectMapper().writeValueAsString(newCompany);

        //When and Then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCompanyJSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mihoyo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList", hasSize(2)));
    }

    @Test
    void should_return_updated_company_when_put_given_new_company_information() throws Exception {
        //Given
        Company newCompany = companyService.addNewCompany(new Company(1, "Mihoyo", employeeList));
        Company updatedCompany = new Company(1, "Cerberus", employeeList);
        String updatedCompanyJSON = new ObjectMapper().writeValueAsString(updatedCompany);

        //When and Then
        client.perform(MockMvcRequestBuilders.put("/companies/{id}", newCompany.getid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompanyJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Cerberus"));
    }

    @Test
    void should_remove_company_when_delete_given_company_id() throws Exception {
        //Given
        Company companyToBeDeleted = companyService.addNewCompany(new Company(1, "Mihoyo", employeeList));

        //When and Then
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}", companyToBeDeleted.getid()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(companyService.getCompanyList(), empty());
    }
}
