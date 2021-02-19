package ru.job4j.chat.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.job4j.chat.ChatApplication;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.repository.PersonRepository;


import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 19.02.2021.
 */
@SpringBootTest(classes = ChatApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonRepository pr;
    private Gson gson = new Gson();

    @Test
    @WithMockUser
    void shouldReturnAllPersons() throws Exception {
        this.mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void shouldReturnJSONFile() throws Exception {
        this.mockMvc.perform(get("/person/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundPerson() throws Exception {
        this.mockMvc.perform(get("/person/{id}", 999999999))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldReturnPersonUseId() throws Exception {
        Person person = this.pr.findById(1).get();
        MvcResult result = this.mockMvc.perform(get("/person/{id}", person.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        Assert.assertThat(object.get("login"), is(person.getLogin()));
        Assert.assertThat(object.get("id"), is(person.getId()));
    }

    @Test
    void shouldUpdatePersonAndGetFromDB() throws Exception {
        Person person = this.pr.findById(1).get();
        person.setLogin("ban2");
        String json = this.gson.toJson(person);
        this.mockMvc.perform(put("/person/").contentType(MediaType.APPLICATION_JSON).content(json));
        MvcResult result = this.mockMvc.perform(get("/person/{id}", person.getId()))
                .andExpect(status().isOk())
                .andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        Assert.assertThat(object.get("login"), is("ban2"));
    }

    @Test
    void shouldCreatePerson() throws Exception {
        Person newPerson = Person.of("TestLogin", "TestName", "TestSurname", "123", true, Role.of(1, "ROLE_USER"), new HashSet<>());
        MvcResult result = this.mockMvc.perform(post("/person/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newPerson)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login").value(newPerson.getLogin()))
                .andExpect(jsonPath("$.password").value(newPerson.getPassword())).andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        Assert.assertThat(newPerson.getLogin(), is(object.get("login")));
        Assert.assertThat(newPerson.getName(), is(object.get("name")));
        this.mockMvc.perform(delete("/person/{id}", object.getInt("id")));
    }

    @Test
    void shouldDeletePersonFromDB() throws Exception {
        Person newPerson = Person.of("TestLogin", "TestName", "TestSurname", "123", true, Role.of(1, "ROLE_USER"), new HashSet<>());
        MvcResult result = this.mockMvc.perform(post("/person/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newPerson)))
                .andExpect(status().isCreated())
                .andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        newPerson.setId(this.gson.fromJson(newPersonResult, Person.class).getId());
        this.mockMvc.perform(delete("/person/{id}", newPerson.getId()));
        Assert.assertFalse(this.pr.findById(newPerson.getId()).isPresent());
    }
}