package ru.job4j.forum.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.PostServiceForRepository;
import ru.job4j.forum.service.UserServiceForRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Calendar;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 02.01.2021.
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class PostControlTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostServiceForRepository pfr;
    @Autowired
    private UserServiceForRepository usr;
    private User user;
    private Post post;


    @BeforeEach
    public void start() {
        this.user = User.of(0, "name", "password", Authority.of(1, ""));
        this.user = usr.save(this.user);
        this.post = Post.of(0, "name", "decs", Calendar.getInstance(), this.user);
        this.post = pfr.save(this.post);
    }

    @AfterEach
    public void finish() {
        this.pfr.delete(this.post);
        this.usr.delete(this.user);
    }

    @Test
    @WithMockUser
    void shouldReturnDefaultFormForUpdatePost() throws Exception {
        this.mockMvc.perform(get("/update/{id}", this.post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser
    void shouldReturnDefaultPostMessage() throws Exception {
        this.mockMvc.perform(get("/post/{id}", this.post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post"));
    }
}