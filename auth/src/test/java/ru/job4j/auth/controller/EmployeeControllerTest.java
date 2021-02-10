package ru.job4j.auth.controller;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 09.02.2021.
 */

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonRepository pr;
    private Gson gson = new Gson();


    @Ignore
    void shouldReturnAllEmployees() throws Exception {
        this.mockMvc.perform(get("/employee/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Ignore
    void shouldUpdatePersonAndGetFromDB() throws Exception {
        Person person = this.pr.findById(2).get();
        person.setLogin("ban2");
        String json = this.gson.toJson(person);
        this.mockMvc.perform(put("/employee/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
        MvcResult result = this.mockMvc.perform(get("/person/{id}", person.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(json)).andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        person = (this.gson.fromJson(newPersonResult, Person.class));
        Assert.assertThat(person.getLogin(), is("ban2"));
    }


    @Ignore
    void shouldCreatePersonAndGetFromDB() throws Exception {
        Person newPerson = Person.of(0, "TestName", "TestPassword");
        MvcResult result = this.mockMvc.perform(post("/employee/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newPerson)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login").value(newPerson.getLogin()))
                .andExpect(jsonPath("$.password").value(newPerson.getPassword())).andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
          newPerson.setId(this.gson.fromJson(newPersonResult, Person.class).getId());
        Assert.assertThat(newPerson, is(this.pr.findById(newPerson.getId()).get()));
        this.mockMvc.perform(delete("/person/{id}", newPerson.getId()));
    }

    @Ignore
    void shouldDeletePersonFromDB() throws Exception {
        Person newPerson = Person.of(0, "TestName", "TestPassword");
        MvcResult result = this.mockMvc.perform(post("/employee/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newPerson)))
                .andExpect(status().isCreated())
                .andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        newPerson.setId(this.gson.fromJson(newPersonResult, Person.class).getId());
        this.mockMvc.perform(delete("/employee/{id}", newPerson.getId()));
        Assert.assertFalse(this.pr.findById(newPerson.getId()).isPresent());
    }

}