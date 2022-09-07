package com.rest.springbootemployee.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    MockMvc client;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @BeforeEach
    void cleanRepository() {
        employeeRepository.clearData();
    }

    @Test
    void should_return_employee_list_when_get_given_employees() throws Exception {
        //Given
        employeeService.addNewEmployee(new Employee(1, "Kate", 17, "female", 480111));

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
        Employee createdEmployee = employeeService.addNewEmployee(new Employee(1, "Kate", 17, "female", 480111));

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
        employeeService.addNewEmployee(new Employee(1, "Kate", 17, "female", 480111));
        employeeService.addNewEmployee(new Employee(2, "Aedrian", 17, "male", 480111));
        employeeService.addNewEmployee(new Employee(3, "Someone", 19, "male", 90111));

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
        employeeService.addNewEmployee(new Employee(1, "Kate", 17, "female", 4801112));
        employeeService.addNewEmployee(new Employee(2, "Aedrian", 20, "male", 480111));
        employeeService.addNewEmployee(new Employee(3, "Someone", 19, "male", 90111));

        //When and Then
        client.perform(MockMvcRequestBuilders.get("/employees?page={page}&pageSize={pageSize}",1,2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Aedrian", "Kate")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(20, 17)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("male", "female")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(4801112, 480111)));
    }

    @Test
    void should_return_new_employee_when_post_given_new_employee() throws Exception {
        //Given
        Employee newEmployee = new Employee(1, "Kate", 17, "female", 4801112);
        String newEmployeeJSON = new ObjectMapper().writeValueAsString(newEmployee);

        //When
        client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployeeJSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kate"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(17))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(4801112));

        //Then
        List<Employee> employeeList = employeeService.getAll();
        final Employee newEmployeeTest = employeeList.get(0);
        assertThat(newEmployeeTest.getName(), equalTo("Kate"));
        assertThat(newEmployeeTest.getAge(), equalTo(17));
        assertThat(newEmployeeTest.getGender(), equalTo("female"));
        assertThat(newEmployeeTest.getSalary(), equalTo(4801112));
    }

    @Test
    void should_return_updated_employee_when_put_given_new_employee() throws Exception {
        //Given
        Employee newEmployee = employeeService.addNewEmployee(new Employee(2, "Aedrian", 20, "male", 480111));
        Employee updatedEmployee = new Employee(2, "Kate", 21, "female", 4801112);

        String updatedEmployeeJson = new ObjectMapper().writeValueAsString(updatedEmployee);


        //When
        client.perform(MockMvcRequestBuilders.put("/employees/{id}", newEmployee.getid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Aedrian"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(4801112));

        //Then
        List<Employee> employeeList = employeeService.getAll();
        final Employee newEmployeeTest = employeeList.get(0);
        assertThat(newEmployeeTest.getName(), equalTo("Aedrian"));
        assertThat(newEmployeeTest.getAge(), equalTo(21));
        assertThat(newEmployeeTest.getGender(), equalTo("male"));
        assertThat(newEmployeeTest.getSalary(), equalTo(4801112));
    }

    @Test
    void should_remove_employee_when_delete_given_employee_id() throws Exception {
        //Given
        employeeService.addNewEmployee(new Employee(1, "Kate", 17, "female", 4801112));

        //When
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}", 2))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Then
        assertThat(employeeService.getAll(), empty());
    }
}

