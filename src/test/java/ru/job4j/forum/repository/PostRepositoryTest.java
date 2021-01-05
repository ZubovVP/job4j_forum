package ru.job4j.forum.repository;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;

import java.util.Calendar;

import static org.hamcrest.core.Is.is;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 05.01.2021.
 */
@SpringBootTest(classes = Main.class)
class PostRepositoryTest {
    @Autowired
    private PostRepository pfr;
    @Autowired
    private UserRepository usr;
    private User user;
    private Post post;

    @BeforeEach
    void start() {
        this.user = User.of("name", "password", Authority.of(1, ""));
        this.post = Post.of(0, "name", "decs", Calendar.getInstance(), this.user);
        this.usr.save(this.user);

    }

    @AfterEach
    void finish() {
        this.pfr.delete(this.post);
        usr.delete(this.user);
    }

    @Test
    void testAddPostInDB() {
        this.pfr.save(this.post);
        Post result = this.pfr.findById(this.post.getId()).get();
        Assert.assertThat(result.getId(), is(this.post.getId()));
    }

    @Test
    void testUpdatePostInDB() {
        this.pfr.save(this.post);
        this.post.setName("name2");
        this.pfr.save(this.post);
        Post result = this.pfr.findById(this.post.getId()).get();
        Assert.assertThat(result.getName(), is(this.post.getName()));
    }

    @Test
    void testDeleteUserInDB() {
        this.pfr.save(this.post);
        this.pfr.delete(this.post);
        Assert.assertFalse(this.pfr.findById(this.post.getId()).isPresent());
    }

    @Test
    void testFindByIdUserInDB() {
        this.pfr.save(this.post);
        Post result = this.pfr.findById(this.post.getId()).get();
        Assert.assertThat(result.getName(), is(this.post.getName()));
    }
}