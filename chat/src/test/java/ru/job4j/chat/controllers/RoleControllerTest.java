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
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.repository.RoleRepository;

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
 * Date: 21.02.2021.
 */
@SpringBootTest(classes = ChatApplication.class)
@AutoConfigureMockMvc
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleRepository rr;
    private Gson gson = new Gson();

    @Test
    @WithMockUser
    void shouldReturnAllRoles() throws Exception {
        this.mockMvc.perform(get("/role/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void shouldReturnJSONFile() throws Exception {
        this.mockMvc.perform(get("/role/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundRole() throws Exception {
        this.mockMvc.perform(get("/role/{id}", 999999999))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldReturnRoleUseId() throws Exception {
        Role role = this.rr.findById(1).get();
        MvcResult result = this.mockMvc.perform(get("/role/{id}", role.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        Assert.assertThat(object.get("name"), is(role.getName()));
        Assert.assertThat(object.get("id"), is(role.getId()));
    }

    @Test
    @WithMockUser
    void shouldUpdateRoleAndGetFromDB() throws Exception {
        Role role = this.rr.findById(1).get();
        role.setName("TestName");
        this.mockMvc.perform(put("/role/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(role)));
        MvcResult result = this.mockMvc.perform(get("/role/{id}", role.getId()))
                .andExpect(status().isOk())
                .andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        Assert.assertThat(object.get("name"), is("TestName"));
        role.setName("ROLE_USER");
        this.mockMvc.perform(put("/role/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(role)));
    }

    @Test
    @WithMockUser
    void shouldCreateRole() throws Exception {
        Role newRole =  Role.of("ROLE_TEST");
        MvcResult result = this.mockMvc.perform(post("/role/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newRole)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newRole.getName())).andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        newRole.setId(this.gson.fromJson(newPersonResult, Role.class).getId());
        Assert.assertThat(newRole.getName(), is(object.get("name")));
        this.mockMvc.perform(delete("/role/{id}", object.getInt("id")));
    }

    @Test
    @WithMockUser
    void shouldDeleteRoleFromDB() throws Exception {
        Role newRole =  Role.of("ROLE_TEST");
        MvcResult result = this.mockMvc.perform(post("/role/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newRole)))
                .andExpect(status().isCreated())
                .andReturn();
        String newRoleResult = result.getResponse().getContentAsString();
        newRole.setId(this.gson.fromJson(newRoleResult, Role.class).getId());
        this.mockMvc.perform(delete("/role/{id}", newRole.getId()));
        Assert.assertFalse(this.rr.findById(newRole.getId()).isPresent());
    }
}