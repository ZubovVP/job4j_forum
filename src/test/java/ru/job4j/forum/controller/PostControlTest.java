package ru.job4j.forum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import org.junit.After;
import org.junit.Before;

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

    @Before
    public void start() {
        System.out.println("START");
    }

    @After
    public void finish() {
        System.out.println("Finish");
    }

    @Test
    @WithMockUser
    void shouldReturnDefaultFormForUpdatePost() throws Exception {
        creatUserAndPost();
        this.mockMvc.perform(get("/update/{id}", this.post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
        deletePostAndUser();
    }

    @Test
    @WithMockUser
    void shouldReturnDefaultPostMessage() throws Exception {
        creatUserAndPost();
        this.mockMvc.perform(get("/post/{id}", this.post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post"));
        deletePostAndUser();
    }

    private void creatUserAndPost() {
        this.user = User.of("name", "password", Authority.of(1, ""));
        this.user = this.usr.save(this.user);
        this.post = Post.of(0, "name", "decs", Calendar.getInstance(), user);
        this.post = this.pfr.save(this.post);
    }

    private void deletePostAndUser() {
        this.pfr.delete(this.post);
        this.usr.delete(this.user);
    }
}