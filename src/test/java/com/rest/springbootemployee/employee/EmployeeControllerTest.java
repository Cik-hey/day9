package com.rest.springbootemployee.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    MockMvc client;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void cleanRepository() {
        employeeRepository.clearData();
    }

    @Test
    void should_return_employee_list_when_get_given_employees() throws Exception {
        //Given
        employeeRepository.addNewEmployee(new Employee(1, "Kate", 17, "female", 480111));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Kate"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(17))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(480111));
    }

    @Test
    void should_return_employee_when_get_by_id_given_employees() throws Exception {
        //Given
        Employee createdEmployee = employeeRepository.addNewEmployee(new Employee(1, "Kate", 17, "female", 480111));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", createdEmployee.getid()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kate"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(17))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(480111));
    }

    @Test
    void should_return_employee_list_when_get_by_gender_given_employees() throws Exception {
        //Given
        employeeRepository.addNewEmployee(new Employee(1, "Kate", 17, "female", 480111));
        employeeRepository.addNewEmployee(new Employee(2, "Aedrian", 17, "male", 480111));
        employeeRepository.addNewEmployee(new Employee(3, "Someone", 19, "male", 90111));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/employees?gender={gender}", "male"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Aedrian", "Someone")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(19, 17)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("male", "male")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(90111, 480111)));
    }

    @Test
    void should_return_employee_list_when_get_by_page_given_employees() throws Exception {
        //Given
        employeeRepository.addNewEmployee(new Employee(1, "Kate", 17, "female", 4801112));
        employeeRepository.addNewEmployee(new Employee(2, "Aedrian", 20, "male", 480111));
        employeeRepository.addNewEmployee(new Employee(3, "Someone", 19, "male", 90111));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/employees?page={page}&pageSize={pageSize}",1,2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Aedrian", "Kate")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(20, 17)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("male", "female")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(4801112, 480111)));
    }
}

