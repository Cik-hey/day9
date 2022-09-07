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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
        companyRepository.addNewCompany(new Company(1, "Mihoyo", employeeList));
        String companyJSON = new ObjectMapper().writeValueAsString(employeeList.get(0));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Mihoyo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList", hasSize(2)));
    }
}
