package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 09.02.2021.
 */

@SpringBootTest(classes = AuthApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {
    private Person prs = Person.of(1, "NamePerson", "123");
    @Mock
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeController empController;

    @Test
    void shouldReturnAllEmployees() {
        Employee empl = Employee.of(1, "EmpName", "EmpSurname", 123, new Timestamp(System.currentTimeMillis()), this.prs);
        List<Person> persons = new ArrayList<>();
        persons.add(this.prs);
        Mockito
                .when(this.restTemplate.exchange("http://localhost:8080/person/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                        }))
                .thenReturn((new ResponseEntity(persons, HttpStatus.OK)));
        List<Employee> employees = this.empController.findAll();
        Assert.assertEquals(employees.get(0).getPerson(), List.of(empl).get(0).getPerson());
    }

    @Test
    void createPersonAndGetThisPerson() {
        Mockito
                .when(this.restTemplate.postForObject("http://localhost:8080/person/", this.prs, Person.class))
                .thenReturn(this.prs);
        Person personCreated = this.empController.create(this.prs).getBody();
        Assert.assertEquals(personCreated, this.prs);
    }

    @Test
    void updatePersonShouldStatusOk() {
        Mockito.doNothing().when(this.restTemplate).put("http://localhost:8080/person/", this.prs);
        Assert.assertThat(this.empController.update(this.prs).getStatusCode().toString(), is("200 OK"));
    }

    @Test
    void deletePersonShouldStatusOk() {
        Mockito.doNothing().when(this.restTemplate).delete("http://localhost:8080/person/{id}", prs.getId());
        Assert.assertThat(this.empController.update(this.prs).getStatusCode().toString(), is("200 OK"));
    }
}