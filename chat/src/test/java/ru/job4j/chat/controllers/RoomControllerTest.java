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
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.repository.RoomRepository;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 21.02.2021.
 */
@SpringBootTest(classes = ChatApplication.class)
@AutoConfigureMockMvc
class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoomRepository rr;
    private Gson gson = new Gson();

    @Test
    @WithMockUser
    void shouldReturnAllRooms() throws Exception {
        this.mockMvc.perform(get("/room/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void shouldReturnJSONFile() throws Exception {
        Room newRoom = Room.of("TestName", "TestDescription", null);
        MvcResult result = this.mockMvc.perform(post("/room/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newRoom)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newRoom.getName())).andReturn();
        String newRoomResult = result.getResponse().getContentAsString();
        newRoom.setId(this.gson.fromJson(newRoomResult, Room.class).getId());
        this.mockMvc.perform(get("/room/{id}", newRoom.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(delete("/room/{id}", newRoom.getId()));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundRole() throws Exception {
        this.mockMvc.perform(get("/room/{id}", 999999999))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldReturnRoleUseId() throws Exception {
        Room newRoom = Room.of("TestName", "TestDescription", null);
        MvcResult result = this.mockMvc.perform(post("/room/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newRoom)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newRoom.getName())).andReturn();
        String newRoomResult = result.getResponse().getContentAsString();
        newRoom.setId(this.gson.fromJson(newRoomResult, Room.class).getId());
        result = this.mockMvc.perform(get("/room/{id}", newRoom.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        Assert.assertThat(object.get("name"), is(newRoom.getName()));
        Assert.assertThat(object.get("id"), is(newRoom.getId()));
        this.mockMvc.perform(delete("/room/{id}", newRoom.getId()));
    }

    @Test
    void shouldAddRoomGetAndUpdateRoomAndGetFromDB() throws Exception {
        Room newRoom = Room.of("Room", "TestDescription", null);
        MvcResult result = this.mockMvc.perform(post("/room/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newRoom)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newRoom.getName())).andReturn();
        String newRoomResult = result.getResponse().getContentAsString();
        newRoom.setId(this.gson.fromJson(newRoomResult, Room.class).getId());
        newRoom.setName("TestName");
        this.mockMvc.perform(put("/room/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newRoom)));
        result = this.mockMvc.perform(get("/room/{id}", newRoom.getId()))
                .andExpect(status().isOk())
                .andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        Assert.assertThat(object.get("name"), is("TestName"));
        this.mockMvc.perform(delete("/room/{id}", newRoom.getId()));
    }

    @Test
    void shouldCreateRole() throws Exception {
        Room newRoom = Room.of("Room", "TestDescription", null);
        MvcResult result = this.mockMvc.perform(post("/room/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newRoom)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newRoom.getName())).andReturn();
        String newPersonResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(newPersonResult);
        newRoom.setId(this.gson.fromJson(newPersonResult, Room.class).getId());
        Assert.assertThat(newRoom.getName(), is(object.get("name")));
        this.mockMvc.perform(delete("/room/{id}", object.getInt("id")));
    }

    @Test
    void shouldDeleteRoleFromDB() throws Exception {
        Room newRoom = Room.of("Room", "TestDescription", null);
        MvcResult result = this.mockMvc.perform(post("/room/").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(newRoom)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newRoom.getName())).andReturn();
        String newRoleResult = result.getResponse().getContentAsString();
        newRoom.setId(this.gson.fromJson(newRoleResult, Room.class).getId());
        this.mockMvc.perform(delete("/room/{id}", newRoom.getId()));
        Assert.assertFalse(this.rr.findById(newRoom.getId()).isPresent());
    }
}